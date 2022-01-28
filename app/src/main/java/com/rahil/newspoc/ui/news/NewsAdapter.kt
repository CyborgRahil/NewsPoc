package com.rahil.newspoc.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahil.newspoc.ui.model.NewsViewModel
import com.rahil.newspoc.R
import javax.inject.Inject

class NewsAdapter @Inject constructor(): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var  newsList: List<NewsViewModel> = arrayListOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val  news =  newsList[position]
        holder.title.text =  news.title
        holder.description.text =  news.description

        if (news.imgUrl!=null) {
            Glide.with(holder.itemView.context)
                    .load(news.imgUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_news, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return  newsList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.image)
        var title: TextView = view.findViewById(R.id.title)
        var description: TextView = view.findViewById(R.id.description)
    }

}