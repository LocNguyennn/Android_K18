package com.example.android_w1.viewModel_Adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_w1.RestaurantAdapter
import com.example.android_w1.databinding.ItemViewMovieBinding
import com.example.android_w1.model.Movie

class MovieAdapter(val mListener : OnItemClickListener) : ListAdapter<Movie, MovieAdapter.MovieVH>(MovieDiffUtilCallback()) {

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    class MovieVH private constructor(var binding: ItemViewMovieBinding,listener : OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup,listener : OnItemClickListener): MovieVH {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemViewMovieBinding.inflate(layoutInflater, parent, false)
                return MovieVH(binding,listener)
            }
        }
        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

        fun binding(item: Movie) {
            binding.txtMovieName.text = item.title?.trim()
            binding.txtMovieName.ellipsize = TextUtils.TruncateAt.MARQUEE
            binding.txtMovieName.isSelected = true
            binding.txtMovieDescription.text = item.overview?.trim()
            binding.txtMovieDescription.ellipsize = TextUtils.TruncateAt.MARQUEE
            binding.txtMovieDescription.isSelected = true

            val urlImage = "https://image.tmdb.org/t/p/w500${item.posterPath}"
            Glide.with(itemView.context).load(urlImage)
                .into(binding.imgMovie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        return MovieVH.from(parent,mListener)
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        val movie = getItem(position)
        holder.binding(movie)
    }
    fun getMovie(position: Int): Movie {
        return getItem(position);
    }
}