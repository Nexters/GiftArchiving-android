package com.nexters.giftarchiving.ui

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.nexters.giftarchiving.R

class Guide1Fragment : Fragment() {
    private lateinit var guideAnimation: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guide1, container, false)
        val imageView = view.findViewById<ImageView>(R.id.guide1_image).apply {
            setBackgroundResource(R.drawable.guide_animation)
            guideAnimation = background as AnimationDrawable
        }
        guideAnimation.start()
        return view
    }

    companion object {

    }
}