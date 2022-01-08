package com.example.mynba.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mynba.R
import com.example.mynba.api.models.standings.StandingsRow
import com.example.mynba.databinding.FragmentStandingsBinding
import com.example.mynba.databinding.StandingsListItemBinding


class StandingsFragment : Fragment() {
    private val standingsViewModel: StandingsViewModel by lazy { ViewModelProvider(this)[StandingsViewModel::class.java] }
    private lateinit var binding: FragmentStandingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        standingsViewModel.getStandings("Eastern Conference").observe(
            this, {
                binding.tableRC.adapter = TableAdapter(it)
                binding.confTv.text = getString(R.string.confEast)
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


        binding.east.setOnClickListener {
            standingsViewModel.getStandings("Eastern Conference").observe(
                viewLifecycleOwner, {
                    binding.tableRC.adapter = TableAdapter(it)
                    binding.confTv.text = getString(R.string.confEast)

                }
            )
        }

        binding.west.setOnClickListener {
            standingsViewModel.getStandings("Western Conference").observe(
                viewLifecycleOwner, {
                    binding.tableRC.adapter = TableAdapter(it)
                    binding.confTv.text = getString(R.string.confWest)

                }
            )
        }

        return binding.root
    }

    private inner class TableHolder(val binding: StandingsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(table: StandingsRow) {
            binding.teamRankTv.text = table.position.toString()
            binding.teamLogo.load(table.team.logo)
            binding.teamShortTv.text = table.team.name_code
            binding.totalTv.text = table.fields.wins_losses_total
            binding.perTv.text = table.fields.percentage_total
            val streakNum = table.fields.streak_total.toInt()
            val streak = if (streakNum <= 0 ){
                "L${streakNum*-1}"
            }else{
                "W$streakNum"
            }
            binding.strkTv.text = streak
            binding.gbTv.text = table.fields.games_behind_total

        }
    }


    private inner class TableAdapter(val table: List<StandingsRow>) :
        RecyclerView.Adapter<TableHolder>() {
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