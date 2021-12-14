package com.example.mynba.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynba.R
import com.example.mynba.databinding.FragmentStandingsBinding
import com.example.mynba.databinding.StandingsListItemBinding
import com.example.mynba.nba.models.Standing
import com.example.mynba.nba.models.Team
import java.util.*

class StandingsFragment : Fragment() {
    private val standingsViewModel : StandingsViewModel by lazy { ViewModelProvider(this)[StandingsViewModel::class.java] }
    private lateinit var binding: FragmentStandingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        standingsViewModel.getStandingsEast().observe(
            this, Observer {
                binding.tableRC.adapter = TableAdapter(it)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStandingsBinding.inflate(layoutInflater)
        binding.tableRC.layoutManager = LinearLayoutManager(context)

        binding.switch1.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked) {
                binding.switch1.text = "East"
                standingsViewModel.getStandingsEast().observe(
                    viewLifecycleOwner, Observer {
                        binding.tableRC.adapter = TableAdapter(it)
                    }
                )
            }
            else {
                binding.switch1.text = "West"
                standingsViewModel.getStandingsWest().observe(
                    viewLifecycleOwner, Observer {
                        binding.tableRC.adapter = TableAdapter(it)
                    }
                )
            }
        }
        return binding.root
    }

    private inner class TableHolder(val binding: StandingsListItemBinding):
            RecyclerView.ViewHolder(binding.root) {

        fun bind(table: Standing) {
                binding.rank.text = table.conference.rank.toString()
                binding.win.text = table.win
                binding.loss.text = table.loss
                binding.winPer.text = table.winPercentage
                binding.gb.text = table.gamesBehind
                setImage(table.teamId)
        }

        private fun setImage(teamId: String) {
            when (teamId) {
                "1" -> { binding.imageView.setImageResource(R.drawable.id_1) }
                "10" -> { binding.imageView.setImageResource(R.drawable.id_10) }
                "15" -> { binding.imageView.setImageResource(R.drawable.id_15) }
                "2" -> { binding.imageView.setImageResource(R.drawable.id_2) }
                "20" -> { binding.imageView.setImageResource(R.drawable.id_20) }
                "21" -> { binding.imageView.setImageResource(R.drawable.id_21) }
                "24" -> { binding.imageView.setImageResource(R.drawable.id_24) }
                "26" -> { binding.imageView.setImageResource(R.drawable.id_26) }
                "27" -> { binding.imageView.setImageResource(R.drawable.id_27) }
                "38" -> { binding.imageView.setImageResource(R.drawable.id_38) }
                "4" -> { binding.imageView.setImageResource(R.drawable.id_4) }
                "41" -> { binding.imageView.setImageResource(R.drawable.id_41) }
                "5" -> { binding.imageView.setImageResource(R.drawable.id_5) }
                "6" -> { binding.imageView.setImageResource(R.drawable.id_6) }
                "7" -> { binding.imageView.setImageResource(R.drawable.id_7) }
                "11" -> { binding.imageView.setImageResource(R.drawable.id_11) }
                "14" -> { binding.imageView.setImageResource(R.drawable.id_14) }
                "16" -> { binding.imageView.setImageResource(R.drawable.id_16) }
                "17" -> { binding.imageView.setImageResource(R.drawable.id_17) }
                "19" -> { binding.imageView.setImageResource(R.drawable.id_19) }
                "22" -> { binding.imageView.setImageResource(R.drawable.id_22) }
                "23" -> { binding.imageView.setImageResource(R.drawable.id_23) }
                "25" -> { binding.imageView.setImageResource(R.drawable.id_25) }
                "28" -> { binding.imageView.setImageResource(R.drawable.id_28) }
                "29" -> { binding.imageView.setImageResource(R.drawable.id_29) }
                "30" -> { binding.imageView.setImageResource(R.drawable.id_30) }
                "31" -> { binding.imageView.setImageResource(R.drawable.id_31) }
                "40" -> { binding.imageView.setImageResource(R.drawable.id_40) }
                "8" -> { binding.imageView.setImageResource(R.drawable.id_8) }
                "9" -> { binding.imageView.setImageResource(R.drawable.id_9) }

            }
        }
    }


    private inner class TableAdapter(val table : List<Standing>):RecyclerView.Adapter<TableHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableHolder {
            val binding = StandingsListItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return TableHolder(binding)
        }

        override fun onBindViewHolder(holder: TableHolder, position: Int) {
            val table = table[position]
            holder.bind(table)
        }

        override fun getItemCount(): Int = table.size

    }
}