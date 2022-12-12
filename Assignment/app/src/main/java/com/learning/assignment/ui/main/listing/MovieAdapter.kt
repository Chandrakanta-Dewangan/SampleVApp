package com.learning.assignment.ui.main.listing

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.learning.domain.model.Movies
import com.learning.assignment.R
import com.learning.assignment.databinding.ItemMovieBinding
import java.util.*


class OnClickListener(val clickListener: (movies: Movies, imageView: ImageView) -> Unit) {
    fun onClick(movies: Movies, imageView: ImageView) = clickListener(movies, imageView)
}

class MovieAdapter(
    private val context: Context,
    private val clickListener: OnClickListener
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>(), Filterable {

    private var movies = mutableListOf<Movies>()
    private var movieFilterList = mutableListOf<Movies>()
    fun setAds(movieList: List<Movies>) {
        movies.clear()
        movies.addAll(movieList)
        notifyDataSetChanged()
    }

    fun getMovieAtPosition(position: Int): Movies = movies[position]

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movies: Movies) {
            binding.imageView.transitionName = movies.imdbID
            binding.cardView.setOnClickListener {
                clickListener.onClick(movies, binding.imageView)
            }
            binding.titleTextview.text = movies.title
            binding.releaseTextview.text = movies.year

            val drawable = CircularProgressDrawable(context).apply {
                setColorSchemeColors(
                    R.color.purple_200,
                    R.color.purple_700,
                    R.color.purple_500
                )
                centerRadius = 30f
                strokeWidth = 5f
                start()
            }

            Glide
                .with(context)
                .load(movies.poster)
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                .placeholder(drawable)
                .into(binding.imageView)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                println("charSearch : " + charSearch)
                if (charSearch.isEmpty()) {
//                    movieFilterList = backupList
                } else {
                    val resultList = mutableListOf<Movies>()
                    for (row in movies) {
                        if (row.title.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    movieFilterList = resultList
                    println("resultList : " + resultList.size)
                    resultList.forEach { item ->
                        println("resultList:::---->" + item)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = movieFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                movieFilterList = results?.values as MutableList<Movies>
                movies = movieFilterList
                notifyDataSetChanged()
            }

        }


    }
}