package com.example.footballdataapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.footballdataapp.model.areas.AreaData
import com.example.footballdataapp.model.competition.Competition
import com.example.gomoneyapp.R
import com.example.gomoneyapp.databinding.CompetitionItemBinding
import fromIsoToString

class CompetitioAdapter(private val onItemClickListener: AreaItemClickListener, val context: Context): RecyclerView.Adapter<CompetitioAdapter.AreaViewHolder>() {

    inner class AreaViewHolder(var binding: CompetitionItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            val competition = differ.currentList[position]
            if (competition.emblem == null){
                binding.emblemImg.setImageResource(R.drawable.img_placeholder)
            }else{
                binding.emblemImg.load(
                    competition.emblem
                ){
                    placeholder(R.drawable.img_placeholder)
                    crossfade(true)
                    crossfade(1000)
                }
            }

            binding.competitionName.text = competition.name
            binding.competitionPlan.text = competition.plan
            binding.competitionType.text = competition.type
            binding.competitionDuration.text = context.getString(R.string.date_duration, competition.area.name, competition.currentSeason?.startDate.fromIsoToString("yyyy-MM-dd", "yyyy/MM/dd")?:"...",  competition.currentSeason?.endDate.fromIsoToString("yyyy-MM-dd", "yyyy/MM/dd")?:"...")
            binding.competitionCard.setOnClickListener {
                onItemClickListener.onClick(competition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        return AreaViewHolder(CompetitionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        return holder.bind(position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var differList = object : DiffUtil.ItemCallback<Competition>(){
        override fun areItemsTheSame(oldItem: Competition, newItem: Competition): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Competition, newItem: Competition): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, differList)

    interface AreaItemClickListener{
        fun onClick(competition: Competition)
    }
}