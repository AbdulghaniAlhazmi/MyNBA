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
            RecyclerView.ViewHolder(binding.root) {

        fun bind(table: Standing) {
            if (table.conference.name == "east") {
                binding.textView.text = table.conference.name
                binding.textView2.text = table.conference.rank
                setImage(table.teamId)
            }
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