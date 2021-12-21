package com.example.mynba.ui.standings

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mynba.R
import com.example.mynba.databinding.FragmentStandingsBinding
import com.example.mynba.databinding.StandingsListItemBinding
import com.example.mynba.api.models.standings.StandingsRow


private const val TAG = "StandingsFragment"
class StandingsFragment : Fragment() {
    private val standingsViewModel : StandingsViewModel by lazy { ViewModelProvider(this)[StandingsViewModel::class.java] }
    private lateinit var binding: FragmentStandingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        standingsViewModel.getStandings("Eastern Conference").observe(
            this, {
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


        binding.east.setOnClickListener {
            standingsViewModel.getStandings("Eastern Conference").observe(
                viewLifecycleOwner, {
                    binding.tableRC.adapter = TableAdapter(it)
                }
            )
        }

        binding.west.setOnClickListener {
            standingsViewModel.getStandings("Western Conference").observe(
                viewLifecycleOwner, {
                    binding.tableRC.adapter = TableAdapter(it)
                }
            )
        }

        return binding.root
    }

    private inner class TableHolder(val binding: StandingsListItemBinding):
            RecyclerView.ViewHolder(binding.root) {

        fun bind(table: StandingsRow) {

            binding.imageView.load(table.team.logo)
            binding.rank.text = table.position.toString()
            binding.win.text = table.fields.wins_total
            binding.loss.text = table.fields.losses_total
            binding.winPer.text = table.fields.percentage_total
            binding.gb.text = table.fields.games_behind_total
        }
    }


    private inner class TableAdapter(val table: List<StandingsRow>):RecyclerView.Adapter<TableHolder>(){
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