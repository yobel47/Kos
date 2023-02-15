package com.binar.kos.view.ui.search

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binar.kos.data.local.entity.PopularArea
import com.binar.kos.databinding.ActivitySearchBinding
import com.binar.kos.utils.GridSpacingItemDecoration
import com.binar.kos.view.adapter.PopularAreaAdapter
import com.binar.kos.view.ui.searchResult.SearchResultActivity


class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private lateinit var popularAreaAdapter: PopularAreaAdapter
     private lateinit var popularAreaList : List<PopularArea>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)

        setContentView(binding.root)
        @Suppress("DEPRECATION")
        Handler().postDelayed({
            binding.etSearch.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.etSearch, InputMethodManager.SHOW_IMPLICIT)
        }, 540)
        loadList()
        onSearch()

        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 5, GridLayoutManager.HORIZONTAL, false)
        binding.rvSearch.layoutManager = layoutManager
        popularAreaAdapter = PopularAreaAdapter(popularAreaList) { popularArea -> onClick(popularArea) }
        binding.rvSearch.addItemDecoration(GridSpacingItemDecoration(3,10, 1))
        binding.rvSearch.adapter = popularAreaAdapter


        binding.rvSearch
    }



    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
        binding.etSearch.requestFocus()

        //your code
    }

    private fun onSearch(){
        binding.etSearch.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val intent = Intent(this, SearchResultActivity::class.java)
                intent.putExtra(SEARCH_TEXT,binding.etSearch.text.toString())
                startActivity(intent)
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun onClick(popularArea: PopularArea){
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