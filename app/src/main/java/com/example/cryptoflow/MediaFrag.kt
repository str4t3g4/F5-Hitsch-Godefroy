package com.example.cryptoflow

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoflow.placeholder.News
import androidx.lifecycle.Observer

class MediaFrag : Fragment() { // Fragemnt that display crypto's latest news
    private val viewModel: MainViewModel by viewModels()
    private var recyclerView: RecyclerView? = null
    private var news : News = News(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view : View = inflater.inflate(R.layout.fragment_media_, container, false)

        //Another recyclerview to handle displaying
        recyclerView = view.findViewById(R.id.recyclerview_news)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = NewsListAdapter(news) { url ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url as String?)
            startActivity(intent)
        }

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.newsAPI()
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar_media) // progress bar at loading


        val newsObserver = Observer<News> { newNews ->
            news.articles.clear()
            news.articles.addAll(newNews.articles)
            recyclerView?.adapter?.notifyDataSetChanged()
            if(news.articles.size > 0) {
                progressBar.visibility = View.GONE
            }
        }
        viewModel.news.observe(viewLifecycleOwner,newsObserver)
    }

}