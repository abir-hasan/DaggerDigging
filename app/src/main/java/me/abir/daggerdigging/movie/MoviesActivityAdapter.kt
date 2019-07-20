package me.abir.daggerdigging.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_tv.view.*
import me.abir.daggerdigging.R
import me.abir.daggerdigging.models.Result
import me.abir.daggerdigging.network.TMDbService

class MoviesActivityAdapter : ListAdapter<Result, MoviesActivityAdapter.ViewHolder>(diffUtil) {

    var onItemClickListener: ((Int) -> Unit)? = null

    companion object {
        const val TAG = "MoviesActivityAdapter"

        val diffUtil = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_tv, parent, false)
            .let { ViewHolder((it)) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindView(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(adapterPosition)
                }
            }
        }

        fun onBindView(model: Result) {
            itemView.tvName.text = model.name
            itemView.tvYear.text = model.firstAirDate
            itemView.tvRating.text = model.voteAverage.toString()
            itemView.tvCount.text = model.voteCount.toString()

            val posterPath = "${TMDbService.POSTER_URL}${model.posterPath}"
            Picasso.get().load(posterPath)
                    .fit()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(itemView.ivPoster)
        }
    }

    fun updateList(movieList: List<Result>) {
        submitList(movieList)
    }
}