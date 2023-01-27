package com.binar.kos.view.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.R
import com.binar.kos.data.local.entity.Filter
import com.binar.kos.data.local.entity.PopularArea
import com.binar.kos.databinding.ActivityLoginBinding
import com.binar.kos.databinding.ActivitySearchBinding
import com.binar.kos.utils.GridSpacingItemDecoration
import com.binar.kos.view.adapter.FilterGeneralAdapter
import com.binar.kos.view.adapter.FilterRoomAdapter
import com.binar.kos.view.adapter.PopularAreaAdapter
import com.binar.kos.view.ui.searchResult.SearchResultActivity
import com.binar.kos.view.ui.selectUser.SelectUserActivity

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private lateinit var popularAreaAdapter: PopularAreaAdapter
     private lateinit var popularAreaList : List<PopularArea>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadList()

        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 5, GridLayoutManager.HORIZONTAL, false)
        binding.rvSearch.layoutManager = layoutManager
        popularAreaAdapter = PopularAreaAdapter(popularAreaList) { popularArea -> onClick(popularArea) }
        binding.rvSearch.addItemDecoration(GridSpacingItemDecoration(3,10, 1))
        binding.rvSearch.adapter = popularAreaAdapter
    }

    private fun onClick(popularArea: PopularArea){
        Toast.makeText(this, popularArea.text, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, SearchResultActivity::class.java)
        intent.putExtra(SEARCH_TEXT,popularArea.text)
        startActivity(intent)
    }

    private fun loadList() {
        popularAreaList = listOf(
            PopularArea("Jakarta"),
            PopularArea("Yogyakarta"),
            PopularArea("Bandung"),
            PopularArea("Surabaya"),
            PopularArea("Malang"),
            PopularArea("Depok"),
            PopularArea("BSD"),
            PopularArea("Jatinangor"),
            PopularArea("Karawaci"),
        )
    }

    companion object {
        const val SEARCH_TEXT = "search_text"
    }
}