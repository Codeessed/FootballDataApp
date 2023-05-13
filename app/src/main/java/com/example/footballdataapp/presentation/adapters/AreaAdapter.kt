package com.example.footballdataapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.footballdataapp.model.Competition
import com.example.gomoneyapp.databinding.AreaItemBinding

class AreaAdapter(private val onItemClickListener: AreaItemClickListener, val context: Context): RecyclerView.Adapter<AreaAdapter.AreaViewHolder>() {

    inner class AreaViewHolder(var binding: AreaItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            val area = differ.currentList[position]
            binding.flagImg.load(
                area.area.flag
            ){
//                placeholder(R.drawable.pholder_img)
//                crossfade(true)
//                crossfade(1000)
            }
            binding.areaName.text = area.area.name
            binding.competitionName.text = area.name
            binding.competitionType.text = area.type
            binding.areaCard.setOnClickListener {
                onItemClickListener.onClick(area)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        return AreaViewHolder(AreaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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