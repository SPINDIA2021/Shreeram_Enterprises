package com.satmatgroup.shreeram.adapters_recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.islamkhsh.CardSliderAdapter
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.BannerModel
import kotlinx.android.synthetic.main.item_card_content.view.*

class BannerAdapter(private val movies: List<BannerModel>, context: Context) :
    CardSliderAdapter<BannerAdapter.MovieViewHolder>() {
    private var mContext: Context? = context


    override fun bindVH(holder: MovieViewHolder, position: Int) {

        val movie = movies[position]

        holder.itemView.run {
            Glide.with(mContext!!)
                .load(movie.image)
                .into(movie_poster)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_content, parent, false)

        return MovieViewHolder(view)
    }

    override fun getItemCount() = movies.size


    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view)
}