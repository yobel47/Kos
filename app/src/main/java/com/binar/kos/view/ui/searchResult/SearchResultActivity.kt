package com.binar.kos.view.ui.searchResult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.R
import com.binar.kos.data.local.entity.Filter
import com.binar.kos.data.remote.response.searchResponse.SearchResponse
import com.binar.kos.databinding.ActivitySearchBinding
import com.binar.kos.databinding.ActivitySearchResultBinding
import com.binar.kos.utils.GridSpacingItemDecoration
import com.binar.kos.utils.Status
import com.binar.kos.view.adapter.FilterGeneralAdapter
import com.binar.kos.view.adapter.FilterRoomAdapter
import com.binar.kos.view.adapter.SearchResultAdapter
import com.binar.kos.view.ui.filter.FilterActivity
import com.binar.kos.view.ui.home.HomeActivity
import com.binar.kos.view.ui.search.SearchActivity
import com.binar.kos.view.ui.selectUser.SelectUserActivity
import com.binar.kos.viewmodel.LoginViewModel
import com.binar.kos.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class SearchResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchResultBinding
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var searchResultAdapter: SearchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchText = intent.getStringExtra(SearchActivity.SEARCH_TEXT)
        binding.etSearch.setText(searchText)

        binding.btnFilter.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            startActivity(intent)
        }

        getData(searchText!!.toLowerCase(Locale.ROOT))
        setAdapter()
        onBack()
    }

    private fun onBack(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun getData(searchText: String){
        searchViewModel.searchRoom(searchText).observe(this@SearchResultActivity) { result ->
            when (result.status) {
                Status.LOADING -> {
                    binding.pb.isVisible = true
                    binding.rvSearchResult.isVisible = false
                }
                Status.SUCCESS -> {
                    binding.pb.isVisible = false
                    if(result.data!!.isEmpty()){
                        binding.tvEmptyRv.isVisible = true
                    }else{
                        binding.rvSearchResult.isVisible = true
                        searchResultAdapter.submitList(result.data)
                    }
                }
                Status.ERROR -> {
                    binding.pb.isVisible = false
                    binding.tvEmptyRv.isVisible = true
                    binding.tvEmptyRv.text = result.message
                }
            }
        }
    }

    private fun setAdapter(){
        binding.rvSearchResult.apply {
            searchResultAdapter = SearchResultAdapter(
                clickListener = {
                    onClick(it)
                }
            )
            layoutManager = LinearLayoutManager(context)
            adapter = searchResultAdapter
        }
    }

    private fun onClick(searchResponse: SearchResponse){
        Toast.makeText(this, searchResponse.title, Toast.LENGTH_SHORT).show()
    }
}