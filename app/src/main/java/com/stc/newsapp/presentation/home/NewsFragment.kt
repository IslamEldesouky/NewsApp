package com.stc.newsapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
    lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        adapter = NewsAdapter(this)
        initRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            newsViewModel.isLoading.observe(viewLifecycleOwner){
                if (it)
                    binding.progressBar.visibility = View.VISIBLE
                else
                    binding.progressBar.visibility = View.GONE
            }
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                newsViewModel.items.observe(viewLifecycleOwner) {
                    lifecycleScope.launch {
                        it.collect {
                            if (it != null) {
                                adapter.submitData(it)
                            }else {
                                Toast.makeText(this@NewsFragment.context,"No data found", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.newsRv.layoutManager = LinearLayoutManager(this@NewsFragment.context)
        binding.newsRv.adapter = adapter
    }

    override fun itemSelected(item: NewsResponse?) {
        val bundle = bundleOf("title" to item?.title, "image" to item?.image)
        findNavController().navigate(R.id.action_newsFragment_to_newsDetailsFragment, bundle)
    }
}