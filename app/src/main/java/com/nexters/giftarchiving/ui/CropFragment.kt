package com.nexters.giftarchiving.ui

import android.graphics.Bitmap
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentCropBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.util.BackDirections
import com.nexters.giftarchiving.viewmodel.CropViewModel
import com.theartofdev.edmodo.cropper.CropImageView
import org.koin.android.viewmodel.ext.android.viewModel

internal class CropFragment : BaseFragment<CropViewModel, FragmentCropBinding>(),
    CropImageView.OnCropImageCompleteListener {
    override val layoutId = R.layout.fragment_crop
    override val viewModel: CropViewModel by viewModel()
    override val navArgs by navArgs<CropFragmentArgs>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setCropImageView()

        observe(viewModel.crop) { cropImage() }
    }

    override fun onCropImageComplete(view: CropImageView?, result: CropImageView.CropResult?) {
        handleCropResult(result)
    }

    private fun handleCropResult(result: CropImageView.CropResult?) {
        result?.let {
            sendArgToBackStack("image", it.bitmap)
            viewModel.navDirections.value = BackDirections(R.id.writeFragment)
        }
    }

    private fun setCropImageView() {
        with(binding.cropIv) {
            setImageBitmap(navArgs.bitmap)
            setOnCropImageCompleteListener(this@CropFragment)
        }
    }

    private fun cropImage() {
        binding.cropIv.getCroppedImageAsync()
    }
}