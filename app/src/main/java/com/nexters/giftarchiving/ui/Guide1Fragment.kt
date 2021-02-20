package com.nexters.giftarchiving.ui

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.nexters.giftarchiving.R

class Guide1Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guide1, container, false)
        view.findViewById<ImageView>(R.id.guide1_image).apply {
            (ContextCompat.getDrawable(
                requireContext(),
                R.drawable.guide_animation
            ) as? AnimationDrawable)?.let {
                setImageDrawable(it)
                it.start()
            }
        }
        return view
    }
}