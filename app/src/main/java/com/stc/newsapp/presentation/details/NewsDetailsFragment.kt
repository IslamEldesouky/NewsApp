package com.stc.newsapp.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.stc.newsapp.R
import com.stc.newsapp.databinding.FragmentNewsDetailsBinding

class NewsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        val title = arguments?.getString("title")
        val image = arguments?.getString("image")
        Glide.with(binding.root.context).load(image)
            .placeholder(R.drawable.placeholder_empty).into(binding.ivImage)
        binding.titleText.text = title
    }
}