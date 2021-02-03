package com.nexters.giftarchiving.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentWriteBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.extension.toast
import com.nexters.giftarchiving.ui.data.write.WriteMenu
import com.nexters.giftarchiving.viewmodel.WriteViewModel
import com.xiaopo.flying.sticker.DrawableSticker
import com.xiaopo.flying.sticker.Sticker
import org.koin.android.viewmodel.ext.android.viewModel
import java.time.LocalDate


internal class WriteFragment : BaseFragment<WriteViewModel, FragmentWriteBinding>() {
    override val layoutId = R.layout.fragment_write
    override val viewModel: WriteViewModel by viewModel()
    override val navArgs by navArgs<WriteFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Bitmap>("image")
            ?.observe(viewLifecycleOwner, Observer { viewModel.editedImage.value = it })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(binding.stickerView) {
            stickers = viewModel.stickerList
            isConstrained = true
            configDefaultIcons()
        }

        observe(viewModel.showMenuType) { showSelectedMenu(it) }
        observe(viewModel.hideMenuType) { hideSelectedMenu(it) }
        observe(viewModel.changeDate) { changeDate() }
        observe(viewModel.loadGallery) { checkPermissionAndAccessGallery() }
        observe(viewModel.isSaved) { binding.stickerView.removeStickerHandler() }
        observe(viewModel.addSticker) {
            val drawable =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_launcher_foreground, null)
            binding.stickerView.addSticker(DrawableSticker(drawable), Sticker.Position.CENTER)
        }
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
                val source = ImageDecoder.createSource(requireActivity().contentResolver, it)
                val bitmap = ImageDecoder.decodeBitmap(source)
                viewModel.setNewImage(it, bitmap)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_READ_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    accessGallery()
                } else {
                    toast("갤러리에 접근할 수 없습니다")
                }
                return
            }
        }
    }

    private fun checkPermission(requestCode: Int, doAccess: () -> Unit) {
        val permission = when (requestCode) {
            REQUEST_CODE_READ_EXTERNAL_STORAGE -> Manifest.permission.READ_EXTERNAL_STORAGE
            else -> null
        }

        permission?.let {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    it
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(permission), requestCode)
            } else {
                doAccess()
            }
        }
    }

    private fun showSelectedMenu(menuType: WriteMenu) {
        when (menuType) {
            WriteMenu.INFORMATION_CATEGORY, WriteMenu.INFORMATION_PURPOSE, WriteMenu.INFORMATION_EMOTION -> binding.informationLayout
            WriteMenu.FRAME -> binding.informationLayout
            WriteMenu.STICKER -> binding.informationLayout
            WriteMenu.BACKGROUND_COLOR -> binding.informationLayout
            WriteMenu.DATE -> {
                loadDate()
                binding.menuDateLayout
            }
        }.visibility = View.VISIBLE
    }

    private fun hideSelectedMenu(menuType: WriteMenu) {
        when (menuType) {
            WriteMenu.INFORMATION_CATEGORY, WriteMenu.INFORMATION_PURPOSE, WriteMenu.INFORMATION_EMOTION -> binding.informationLayout
            WriteMenu.FRAME -> binding.informationLayout
            WriteMenu.STICKER -> binding.informationLayout
            WriteMenu.BACKGROUND_COLOR -> binding.informationLayout
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
            viewModel.date.value = LocalDate.of(year, month + 1, dayOfMonth)
        }
    }

    private fun checkPermissionAndAccessGallery() {
        checkPermission(REQUEST_CODE_READ_EXTERNAL_STORAGE) {
            accessGallery()
        }
    }

    private fun accessGallery() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    companion object {
        private const val REQUEST_CODE_READ_EXTERNAL_STORAGE = 100
    }
}