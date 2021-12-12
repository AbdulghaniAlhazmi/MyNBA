package com.example.mynba.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynba.databinding.FragmentStandingsBinding
import com.example.mynba.databinding.StandingsListItemBinding
import com.example.mynba.nba.models.Standing

class StandingsFragment : Fragment() {
    private val standingsViewModel : StandingsViewModel by lazy { ViewModelProvider(this)[StandingsViewModel::class.java] }
    private lateinit var binding: FragmentStandingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        standingsViewModel.getStandings().observe(
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
        return binding.root
    }

    private inner class TableHolder(val binding: StandingsListItemBinding):
            RecyclerView.ViewHolder(binding.root){

                fun bind(table : Standing){
                    binding.textView.text = table.teamId
                    binding.textView2.text = table.division.name
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