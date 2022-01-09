package com.example.mynba

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mynba.databinding.FragmentTabsBinding
import com.example.mynba.ui.boxScore.BoxScoreFragment
import com.example.mynba.ui.gameComments.GameCommentsFragment
import com.example.mynba.ui.gameMedia.GameMediaFragment
import com.example.mynba.ui.gameStatus.GameStatusFragment
import com.example.mynba.ui.games.*
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.properties.Delegates


class TabsFragment : Fragment() {

    private lateinit var binding : FragmentTabsBinding
    private val statusFragment =  GameStatusFragment()
    private val boxScoreFragment = BoxScoreFragment()
    private val gameMediaFragment = GameMediaFragment()
    private val gameCommentsFragment =  GameCommentsFragment()
    private var gameId by Delegates.notNull<Int>()
    private lateinit var homeLogo : String
    private lateinit var awayLogo : String
    private lateinit var homeCode : String
    private lateinit var awayCode : String
    private lateinit var awayScore : String
    private lateinit var homeScore : String
    private var homeId by Delegates.notNull<Int>()
    private var awayId by Delegates.notNull<Int>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabsBinding.inflate(layoutInflater)
        gameId = requireArguments().getInt(KEY_GAME_ID)
        homeId = requireArguments().getInt(KEY_HOME_ID)
        awayId = requireArguments().getInt(KEY_AWAY_ID)
        homeLogo = requireArguments().getString(KEY_HOME_LOGO).toString()
        awayLogo = requireArguments().getString(KEY_AWAY_LOGO).toString()
        homeCode = requireArguments().getString(KEY_HOME_SHORT).toString()
        awayCode = requireArguments().getString(KEY_AWAY_SHORT).toString()
        homeScore = requireArguments().getString(KEY_HOME_SCORE).toString()
        awayScore = requireArguments().getString(KEY_AWAY_SCORE).toString()

        gameStatusArgs()
        gameBoxScoreArgs()
        gameMediaArgs()
        gameCommentArgs()


        binding.viewPager2.adapter = ViewPagerAdapter(parentFragmentManager,lifecycle)


        TabLayoutMediator(binding.tabs,binding.viewPager2){tabs,position ->
            when(position){
                0 ->{
                    tabs.text = getString(R.string.status)
                }
                1 ->{
                    tabs.text = getString(R.string.boxScore)
                }
                2 ->{
                    tabs.text = getString(R.string.highlights)
                }
                3 ->{
                    tabs.text = getString(R.string.discuss)
                }
            }

        }.attach()


        return binding.root

    }

    private fun gameCommentArgs() {
        gameCommentsFragment.arguments = Bundle().apply {
            putInt(KEY_GAME_ID,gameId)

        }
    }

    private fun gameMediaArgs() {
        gameMediaFragment.arguments = Bundle().apply {
            putInt(KEY_GAME_ID,gameId)
        }
    }

    private fun gameBoxScoreArgs() {
        boxScoreFragment.arguments = Bundle().apply {
            putInt(KEY_GAME_ID,gameId)
            putString(KEY_HOME_SHORT,homeCode)
            putString(KEY_AWAY_SHORT,awayCode)
            putInt(KEY_HOME_ID,homeId)
            putInt(KEY_AWAY_ID,awayId)
        }
    }

    private fun gameStatusArgs() {
        statusFragment.arguments = Bundle().apply {
            putInt(KEY_GAME_ID,gameId)
            putString(KEY_HOME_LOGO,homeLogo)
            putString(KEY_AWAY_LOGO,awayLogo)
            putString(KEY_HOME_SHORT,homeCode)
            putString(KEY_AWAY_SHORT,awayCode)
            putString(KEY_HOME_SCORE,homeScore)
            putString(KEY_AWAY_SCORE,awayScore)
        }
    }

    private inner class ViewPagerAdapter(fragmentManager: FragmentManager , lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle){
        override fun getItemCount(): Int {
            return 4
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 ->{
                    statusFragment
                }
                1 ->{
                    boxScoreFragment
                }
                2 ->{
                    gameMediaFragment
                }
                3 ->{
                    gameCommentsFragment
                }
                else ->{
                    Fragment()
                }
            }
        }

    }

}