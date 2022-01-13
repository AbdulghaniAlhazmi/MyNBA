package com.example.mynba.ui.news

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mynba.WebViewActivity
import com.example.mynba.api.models.news.Value
import com.example.mynba.databinding.FragmentNewsBinding
import com.example.mynba.databinding.NewsListItemBinding

const val NEWS_KEY = "NewsKey"

class NewsFragment : Fragment() {

    private val newsViewModel: NewsViewModel by lazy { ViewModelProvider(this)[NewsViewModel::class.java] }
    private lateinit var binding: FragmentNewsBinding

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newsViewModel.getNews().observe(
            this, {
                binding.newsRc.adapter = NewsAdapter(it)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        binding.newsRc.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    private inner class NewsHolder(val binding: NewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: Value) {
            binding.newsImage.load(news.image.url)
            binding.titleTv.text = news.title

        }

    }


    private inner class NewsAdapter(val news: List<Value>) : RecyclerView.Adapter<NewsHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
            val binding = NewsListItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return NewsHolder(binding)
        }

        override fun onBindViewHolder(holder: NewsHolder, position: Int) {
            val news = news[position]
            holder.bind(news)
            holder.itemView.setOnClickListener {

                val intent = Intent(context, WebViewActivity::class.java).apply {
                    putExtra(NEWS_KEY, news.url)
                }
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int = news.size

    }

    override fun onResume() {
        super.onResume()
        newsViewModel.getNews().observe(
            this, {
                binding.newsRc.adapter = NewsAdapter(it)
            }
        )
    }


}