package com.example.mynba.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mynba.databinding.FragmentNewsBinding
import com.example.mynba.databinding.NewsListItemBinding
import com.example.mynba.nba.models.news.Article

class NewsFragment : Fragment() {

    private val newsViewModel : NewsViewModel by lazy { ViewModelProvider(this)[NewsViewModel::class.java] }
    private lateinit var binding: FragmentNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsViewModel.getNews().observe(
            this, Observer {
                binding.newsRC.adapter = NewsAdapter(it)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNewsBinding.inflate(layoutInflater)
        binding.newsRC.layoutManager = LinearLayoutManager(context)


        return binding.root
    }

    private inner class NewsHolder(val binding: NewsListItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind (news : Article){
            binding.imageView3.load(news.urlToImage)
            binding.title.text = news.title
        }

    }


    private inner class NewsAdapter(val news : List<Article>):RecyclerView.Adapter<NewsHolder>(){
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
        }

        override fun getItemCount(): Int = news.size

    }


}