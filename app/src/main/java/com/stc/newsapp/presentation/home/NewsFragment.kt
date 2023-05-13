package com.stc.newsapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stc.newsapp.R
import com.stc.newsapp.databinding.FragmentNewsBinding
import com.stc.newsapp.domain.entity.NewsResponse
import com.stc.newsapp.presentation.home.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment(), NewsAdapter.ItemSelected {

    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var binding: FragmentNewsBinding
    private lateinit var adapter: NewsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        adapter = NewsAdapter(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel.getNewsResponse()
        lifecycleScope.launch {
            newsViewModel.newsResponse.collect {
                if (it != null) {
                    initView(it.newsResponse)
                }
            }
        }
    }

    fun initView(list: List<NewsResponse>) {
        binding.progressBar.visibility = View.GONE
        adapter.submitList(list)
        val recyclerView: RecyclerView = binding.newsRv
        recyclerView.layoutManager = LinearLayoutManager(this@NewsFragment.context)
        recyclerView.adapter = adapter
    }

    override fun itemSelected(item: NewsResponse?) {
        val bundle = bundleOf("title" to item?.title, "image" to item?.image)
        findNavController().navigate(R.id.action_newsFragment_to_newsDetailsFragment, bundle)
    }
}