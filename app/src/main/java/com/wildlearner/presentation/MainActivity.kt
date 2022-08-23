package com.wildlearner.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulrybitskyi.persistentsearchview.utils.VoiceRecognitionDelegate
import com.wildlearner.core.model.Results
import com.wildlearner.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var resultsAdapter: SearchResultsAdapter? = null
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchViewModel = getViewModel()
        setupSearchBar()
        subscribeToViewModel()
    }

    private fun setupSearchBar() {
        with(binding.persistentSearchView) {
            setOnLeftBtnClickListener {

            }
            setOnClearInputBtnClickListener {
                binding.searchList.adapter = null
                binding.emptyMessage.visibility = View.VISIBLE
            }
            setVoiceRecognitionDelegate(VoiceRecognitionDelegate(this@MainActivity))
            setOnSearchConfirmedListener { searchView, query ->
                binding.searchList.adapter = null
                searchViewModel.search(query)
                searchView.collapse()
            }
            setSuggestionsDisabled(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        resultsAdapter = null
    }

    private fun getAdapter(results: MutableList<Results>): SearchResultsAdapter {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.searchList.layoutManager = layoutManager
        resultsAdapter = SearchResultsAdapter(results)
        return resultsAdapter as SearchResultsAdapter
    }

    private fun subscribeToViewModel() {
        searchViewModel.searchResults.observe(this, Observer {
            binding.searchList.adapter = getAdapter(it.results)
            binding.emptyMessage.visibility = View.GONE
        })

        searchViewModel.loading.observe(this, Observer {
            binding.progress.visibility = if (it) View.VISIBLE else View.GONE
            binding.emptyMessage.visibility = View.GONE
        })

        searchViewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

}