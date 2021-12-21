package com.example.mynba.ui.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mynba.R
import com.example.mynba.api.models.news.Value
import com.example.mynba.databinding.FragmentNewsBinding
import com.example.mynba.databinding.NewsListItemBinding

const val NEWS_ID_TITLE = "title"
const val NEWS_ID_IMAGE = "IMAGE"
const val NEWS_ID_CONTENT = "CONTENT"


class NewsFragment : Fragment() {

    private val newsViewModel : NewsViewModel by lazy { ViewModelProvider(this)[NewsViewModel::class.java] }
    private lateinit var binding: FragmentNewsBinding

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newsViewModel.getNews().observe(
            this, {
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

        fun bind (news : Value){
            binding.imageView3.load(news.image.url)
            binding.title.text = news.title
        }

    }


    private inner class NewsAdapter(val news : List<Value>):RecyclerView.Adapter<NewsHolder>(){
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
                findNavController().navigate(R.id.action_navigation_news_to_newsPageFragment,Bundle().apply {
                    putString(NEWS_ID_TITLE,news.title)
                    putString(NEWS_ID_IMAGE,news.image.url)
                    putString(NEWS_ID_CONTENT,news.body)
                })
            }
        }

        override fun getItemCount(): Int = news.size

    }

    override fun onResume() {
        super.onResume()
        newsViewModel.getNews().observe(
            this, {
                binding.newsRC.adapter = NewsAdapter(it)
            }
        )
    }


}