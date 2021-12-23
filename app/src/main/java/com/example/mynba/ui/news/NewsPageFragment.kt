package com.example.mynba.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.mynba.databinding.NewsPageFragmentBinding

class NewsPageFragment : Fragment() {

    private val newPageViewModel: NewsPageViewModel by lazy { ViewModelProvider(this)[NewsPageViewModel::class.java] }
    private lateinit var binding: NewsPageFragmentBinding
    private lateinit var title: String
    private lateinit var image: String
    private lateinit var content: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = requireArguments().getString(NEWS_ID_TITLE).toString()
        image = requireArguments().getString(NEWS_ID_IMAGE).toString()
        content = requireArguments().getString(NEWS_ID_CONTENT).toString()
        (activity as AppCompatActivity).supportActionBar?.title = title
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = NewsPageFragmentBinding.inflate(layoutInflater)
        binding.newTitle.text = title
        binding.newsImage.load(image)
        binding.content.text = content

        return binding.root
    }


}