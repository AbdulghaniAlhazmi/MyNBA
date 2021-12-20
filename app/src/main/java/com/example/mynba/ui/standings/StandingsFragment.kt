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


//                binding.win.text = table.standings_rows.forEach { it.fields.wins_total }.toString()
//                binding.loss.text = table.standings_rows.forEach { it.fields.losses_total }.toString()
//                binding.winPer.text = table.standings_rows.forEach { it.fields.percentage_total }.toString()
//                binding.gb.text = table.standings_rows.forEach { it.fields.games_behind_total }.toString()
//                binding.imageView.load(table.standings_rows.forEach{it.team.logo}.toString())
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