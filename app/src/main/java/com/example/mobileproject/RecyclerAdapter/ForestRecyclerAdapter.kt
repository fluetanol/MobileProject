package com.example.mobileproject.RecyclerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileproject.databinding.RecyclelistBinding
import com.example.mobileproject.dataclass.Forestdata




class ForestRecyclerAdapter(var data:ArrayList<Forestdata>) : RecyclerView.Adapter<ForestRecyclerAdapter.ViewHolder>(){

    interface Clicklistener{
        fun onItemClickListener()
    }
    lateinit var ItemClickListener:Clicklistener

    inner class ViewHolder(binding: RecyclelistBinding): RecyclerView.ViewHolder(binding.root) {
        val binder = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecyclelistBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
            holder.binder.Name.text = item.name
            holder.binder.Num.text = item.num
            holder.binder.description.text = item.description
            holder.binder.description.setTypeface(null, item.font)
    }

    override fun getItemCount(): Int = data.size

}