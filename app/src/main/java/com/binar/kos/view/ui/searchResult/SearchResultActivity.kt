package com.binar.kos.view.ui.searchResult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.kos.R
import com.binar.kos.databinding.ActivitySearchBinding
import com.binar.kos.databinding.ActivitySearchResultBinding
import com.binar.kos.view.ui.filter.FilterActivity
import com.binar.kos.view.ui.search.SearchActivity
import com.binar.kos.view.ui.selectUser.SelectUserActivity

class SearchResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchText = intent.getStringExtra(SearchActivity.SEARCH_TEXT)
        binding.etSearch.setText(searchText!!)

        binding.btnFilter.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            startActivity(intent)
        }
    }
}