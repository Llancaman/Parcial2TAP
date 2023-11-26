package com.example.parcial2tap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var listFruits: List<Fruits> = emptyList()
    lateinit var onItemClickListener: (String) -> Unit

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imgFruit: ImageView = itemView.findViewById(R.id.image_itemlist)

        fun bind(fruit: Fruits) {

            itemView.findViewById<TextView>(R.id.textView_itemlist).text = fruit.name

            Picasso.get()
                .load(fruit.imageUrl)
                .into(imgFruit)

            itemView.setOnClickListener{
                onItemClickListener(fruit.name)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = listFruits[position]
        holder.bind(fruit)
    }

    override fun getItemCount(): Int {
        return listFruits.size
    }

    fun setFruits(fruits: List<Fruits>) {
        this.listFruits = fruits
        notifyDataSetChanged()
    }
}