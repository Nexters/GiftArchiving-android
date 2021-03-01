package com.nexters.giftarchiving.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseConfirmDialogListener
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentWriteBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.extension.toast
import com.nexters.giftarchiving.ui.data.write.WriteMenu
import com.nexters.giftarchiving.ui.data.write.WriteSticker
import com.nexters.giftarchiving.ui.viewpager.adapter.MenuSlidePagerAdapter
import com.nexters.giftarchiving.ui.viewpager.adapter.StickerSlidePagerAdapter
import com.nexters.giftarchiving.viewmodel.WriteViewModel
import com.xiaopo.flying.sticker.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.time.LocalDate

internal class WriteFragment : BaseFragment<WriteViewModel, FragmentWriteBinding>() {
    override val layoutId = R.layout.fragment_write
    override val viewModel: WriteViewModel by viewModel()
    override val navArgs by navArgs<WriteFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receiveArgFromOtherView<Bitmap>("image") { viewModel.setNewImage(it) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setStickerView()
        setStickerMenuViewPager()
        setContentEditTextSize()
        setBackPressedDispatcher()

        observe(viewModel.showMenuType) { showSelectedMenu(it) }
        observe(viewModel.hideMenuType) { hideSelectedMenu(it) }
        observe(viewModel.changeDate) { changeDate() }
        observe(viewModel.loadGallery) { if (checkReadPermission()) accessGallery() }
        observe(viewModel.isBack) { showExitDialog() }
        observe(viewModel.isSaved) { saveGift() }
        observe(viewModel.addSticker) { addSticker(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        with(viewModel.stickerList) {
            clear()
            addAll(binding.stickerView.stickers)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            data?.data?.let {
                viewModel.navDirections.value =
                    WriteFragmentDirections.actionWriteFragmentToCropFragment(it)
            }
        }
    }

    private fun checkReadPermission(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            toast(NOTICE_DO_NOT_LOAD_GALLERY)
            requestPermissions(permissions, REQUEST_CODE_READ_EXTERNAL_STORAGE)
            false
        } else {
            true
        }
    }

    private fun setStickerView() {
        with(binding.stickerView) {
            stickers = viewModel.stickerList
            isConstrained = true

            val deleteIcon = BitmapStickerIcon(
                ContextCompat.getDrawable(context, R.drawable.ic_icon_cancel_sticker),
                BitmapStickerIcon.RIGHT_TOP
            ).apply { iconEvent = DeleteIconEvent() }

            val zoomIcon = BitmapStickerIcon(
                ContextCompat.getDrawable(context, R.drawable.ic_icon_scale),
                BitmapStickerIcon.RIGHT_BOTOM
            ).apply { iconEvent = ZoomIconEvent() }

            val flipIcon = BitmapStickerIcon(
                ContextCompat.getDrawable(
                    context,
                    com.xiaopo.flying.sticker.R.drawable.sticker_ic_flip_white_18dp
                ),
                BitmapStickerIcon.LEFT_TOP
            ).apply { iconEvent = FlipHorizontallyEvent() }

            binding.stickerView.icons = listOf(deleteIcon, zoomIcon, flipIcon)
        }
    }

    private fun setContentEditTextSize() {
        binding.contentEt.let {
            it.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (it.height != 0) {
                        it.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        it.height = it.height
                    }
                }
            })
        }
    }

    private fun setBackPressedDispatcher() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            with(viewModel) {
                if (currentMenuType.value != null) hideCurrentMenu()
                else showExitDialog()
            }
        }
    }

    private fun showSelectedMenu(menuType: WriteMenu) {
        hideSoftKeypad()
        when (menuType) {
            WriteMenu.INFORMATION_CATEGORY, WriteMenu.INFORMATION_PURPOSE, WriteMenu.INFORMATION_EMOTION -> {
                setInformationMenuViewPager(menuType)
                binding.menuInformationLayout
            }
            WriteMenu.FRAME -> binding.menuFrameLayout
            WriteMenu.STICKER -> binding.menuStickerLayout
            WriteMenu.BACKGROUND_COLOR -> binding.menuBackgroundColorLayout
            WriteMenu.DATE -> {
                loadDate()
                binding.menuDateLayout
            }
        }.visibility = View.VISIBLE
    }

    private fun hideSelectedMenu(menuType: WriteMenu) {
        when (menuType) {
            WriteMenu.INFORMATION_CATEGORY, WriteMenu.INFORMATION_PURPOSE, WriteMenu.INFORMATION_EMOTION -> binding.menuInformationLayout
            WriteMenu.FRAME -> binding.menuFrameLayout
            WriteMenu.STICKER -> binding.menuStickerLayout
            WriteMenu.BACKGROUND_COLOR -> binding.menuBackgroundColorLayout
            WriteMenu.DATE -> binding.menuDateLayout
        }.visibility = View.GONE
    }

    private fun loadDate() {
        viewModel.date.value?.run {
            val y = year
            val m = monthValue - 1
            val d = dayOfMonth
            binding.datePicker.updateDate(y, m, d)
        }
    }

    private fun changeDate() {
        with(binding.datePicker) {
            val y = year
            val m = month + 1
            val d = dayOfMonth
            viewModel.date.value = LocalDate.of(y, m, d)
        }
    }

    private fun setInformationMenuViewPager(menuType: WriteMenu) {
        with(binding.informationMenuViewpager) {
            adapter = MenuSlidePagerAdapter(
                requireActivity(),
                viewModel,
                menuType,
                viewModel.isReceiveGift
            )
            TabLayoutMediator(binding.informationMenuTabLayout, this) { tab, pos ->

            }.attach()
        }
    }

    private fun setStickerMenuViewPager() {
        val stickerType = WriteSticker.values()
        with(binding.menuStickerViewpager) {
            isUserInputEnabled = false
            resetStickerMenuViewPager()
        }
        with(binding.menuStickerTabLayout) {
            TabLayoutMediator(this, binding.menuStickerViewpager) { tab, pos ->
                tab.text = WriteSticker.valueOf(stickerType[pos].name).menuTitle
            }.attach()
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                    if (tab?.position == 1) {
                        resetStickerMenuViewPager()
                        binding.menuStickerViewpager.currentItem = tab.position
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab?.position == 1) {
                        resetStickerMenuViewPager()
                    }
                }
            })
        }
    }

    private fun resetStickerMenuViewPager() {
        binding.menuStickerViewpager.adapter =
            StickerSlidePagerAdapter(requireActivity(), viewModel)
    }

    private fun accessGallery() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    private fun addSticker(stickerResId: Int) {
        val drawable = ContextCompat.getDrawable(requireContext(), stickerResId)
        binding.stickerView.addSticker(DrawableSticker(drawable), Sticker.Position.CENTER)
    }

    private fun saveGift() {
        if (viewModel.isEditMode) {
            sendArgToBackStack("isEdit", true)
            viewModel.editGiftProperties()
        } else {
            sendArgToBackStack("needReload", true)
            binding.stickerView.removeStickerHandler()
            val noBgBitmap = binding.stickerView.createBitmap()
            with(binding.shareIv) {
                val cacheDir = requireContext().cacheDir
                setImageBitmap(noBgBitmap)
                viewTreeObserver.addOnGlobalLayoutListener(object :
                    ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        if (drawable.toString() != "") {
                            viewTreeObserver.removeOnGlobalLayoutListener(this)
                            val bgBitmap = viewModel.convertLayoutToBitmap(binding.shareLayout)
                            viewModel.save(cacheDir, noBgBitmap, bgBitmap)
                        }
                    }
                })
            }
        }
    }

    private fun showExitDialog() {
        val listener = object : BaseConfirmDialogListener() {
            override fun onConfirm() {
                super.onConfirm()
                with(viewModel) {
                    if (isEditMode) sendArgToBackStack("isEdit", false)
                    onBackExit()
                }
            }
        }
        ConfirmBottomSheet(
            title = EXIT_DIALOG_TITLE,
            subTitle = EXIT_DIALOG_SUB_TITLE,
            listener = listener
        ).show(parentFragmentManager, EXIT_DIALOG_TAG)
    }

    private fun hideSoftKeypad() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    companion object {
        private const val REQUEST_CODE_READ_EXTERNAL_STORAGE = 100

        private const val EXIT_DIALOG_TAG = "exit dialog"
        private const val EXIT_DIALOG_TITLE = "저장하지 않고 나가시겠습니까?"
        private const val EXIT_DIALOG_SUB_TITLE = "작성중이던 내용이 사라집니다."
        private const val NOTICE_DO_NOT_LOAD_GALLERY = "이미지를 로드하려면 권한이 필요합니다."
    }
}