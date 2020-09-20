package com.rahil.newspoc.ui.news

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahil.newspoc.R
import dagger.android.AndroidInjection
import com.rahil.newspoc.presentation.ViewModelFactory
import com.rahil.newspoc.presentation.news.NewsViewModel
import com.rahil.newspoc.presentation.data.ResourceState
import com.rahil.newspoc.presentation.model.NewsView
import com.rahil.newspoc.ui.mapper.NewsMapper
import com.rahil.newspoc.presentation.utility.NewsConstant
import com.rahil.newspoc.ui.widget.empty.EmptyListener
import com.rahil.newspoc.ui.widget.error.ErrorListener
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    @Inject
    lateinit var newsAdapter: NewsAdapter
    @Inject
    lateinit var mapper: NewsMapper
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        AndroidInjection.inject(this)

        newsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewsViewModel::class.java)

        setupBrowseRecycler()
        setupViewListeners()
        spinnerClickListener()
    }

    private fun spinnerClickListener() {
        newsSpinnerList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                fetchNewsSpinnerItem(position)
            }

        }

    }

    private fun fetchNewsSpinnerItem(position: Int) {
        when (position) {
            0 -> newsViewModel.fetchNews(NewsConstant.BBC)
            1 -> newsViewModel.fetchNews(NewsConstant.CNN)
            2 -> newsViewModel.fetchNews(NewsConstant.NYTIMES)
        }
    }

    override fun onStart() {
        super.onStart()
        newsViewModel.getNews().observe(this, Observer {
            if (it != null) this.handleDataState(it.status, it.data, it.message)
        })
    }

    private fun setupBrowseRecycler() {
        recyclerNews.layoutManager = LinearLayoutManager(this)
        recyclerNews.adapter = newsAdapter
    }

    private fun handleDataState(resourceState: ResourceState, data: List<NewsView>?,
                                message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> setupScreenForLoadingState()
            ResourceState.SUCCESS -> setupScreenForSuccess(data)
            ResourceState.ERROR -> setupScreenForError(message)
        }
    }

    private fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        recyclerNews.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: List<NewsView>?) {
        view_error.visibility = View.GONE
        progress.visibility = View.GONE
        if (data != null && data.isNotEmpty()) {
            updateListView(data)
            recyclerNews.visibility = View.VISIBLE
        } else {
            view_empty.visibility = View.VISIBLE
        }
    }

    private fun updateListView(newsList: List<NewsView>) {
        newsAdapter.newsList = newsList.map { mapper.mapToViewModel(it) }
        newsAdapter.notifyDataSetChanged()
    }

    private fun setupScreenForError(message: String?) {
        progress.visibility = View.GONE
        recyclerNews.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.VISIBLE
    }

    private fun setupViewListeners() {
        view_empty.emptyListener = emptyListener
        view_error.errorListener = errorListener
    }

    private val emptyListener = object : EmptyListener {
        override fun onCheckAgainClicked() {
            fetchNewsUsingSpinnerSelection()
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            fetchNewsUsingSpinnerSelection()
        }
    }

    private fun fetchNewsUsingSpinnerSelection() {
        when (newsSpinnerList.selectedItemPosition) {
            0 -> newsViewModel.fetchNews(NewsConstant.BBC)
            1 -> newsViewModel.fetchNews(NewsConstant.CNN)
            2 -> newsViewModel.fetchNews(NewsConstant.NYTIMES)
        }
    }

}