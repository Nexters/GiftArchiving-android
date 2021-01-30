package com.nexters.giftarchiving.ui

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentCropBinding
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
        with(binding.cropIv) {
            setImageUriAsync(Uri.parse(navArgs.imageUri))
            setOnCropImageCompleteListener(this@CropFragment)
        }
    }

    override fun onCropImageComplete(view: CropImageView?, result: CropImageView.CropResult?) {
        handleCropResult(result)
    }

    private fun handleCropResult(result: CropImageView.CropResult?) {
        var bitmap: Bitmap? = null
        result?.let { bitmap = it.bitmap }
        with(findNavController()) {
            previousBackStackEntry?.savedStateHandle?.getLiveData<Bitmap>("image")?.value = bitmap
            viewModel.navDirections.value = BackDirections(R.id.writeFragment)
        }
    }
}