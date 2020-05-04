package com.example.byrecipe.Model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.byrecipe.Model.ListResep.ListViewHolder
import com.example.byrecipe.R
import kotlinx.android.synthetic.main.recipe_row.view.*

class ListResep(private val listresep: ArrayList<Resep>) : RecyclerView.Adapter<ListResep.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.recipe_row,viewGroup,false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listresep.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listresep[position])
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(resep:Resep){
            with(itemView){
                Glide.with(itemView.context)
                    .load(resep.foto)
                    .apply(RequestOptions().override(55,55))
                    .into(foto)

                tv_item_name.text = resep.nama
                tv_item_description.text = resep.waktu

            }
        }
    }
}