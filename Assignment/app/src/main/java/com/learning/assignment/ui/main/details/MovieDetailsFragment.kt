package com.learning.assignment.ui.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.learning.assignment.R
import com.learning.assignment.databinding.DetailsFragmentBinding
import com.learning.assignment.ui.main.base.BaseFragment
import com.learning.data.core.extension.gone
import com.learning.data.core.extension.visible
import com.learning.domain.model.Movies
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<DetailsFragmentBinding, MovieDetailsViewModel>(),
    View.OnClickListener {
    private lateinit var movies: Movies
    override fun setupViewBinding(view: View): DetailsFragmentBinding {
        return DetailsFragmentBinding.bind(view)
    }

    override fun setupViewModel(): MovieDetailsViewModel {
        val viewModel: MovieDetailsViewModel by viewModels()
        return viewModel
    }

    override val layoutId: Int
        get() = R.layout.details_fragment

    override fun onViewCreated(
        @NonNull inflater: LayoutInflater,
        @NonNull view: View,
        @Nullable savedInstanceState: Bundle?
    ) {
        val args: MovieDetailsFragmentArgs by navArgs()
        val movieList = args.movies
        movies = movieList

        var isDataLoaded = false
        viewModel.connectivityLiveData.observe(viewLifecycleOwner) {
            if (!isDataLoaded) {
                viewModel.loadData(movies.imdbID.trim())
            }
        }
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            isDataLoaded = true
            viewBinding!!.titleTv.text = it.title
            viewBinding!!.releaseTv.text = "Released on : " + it.year
            viewBinding!!.plotTv.text = "Plot : " + it.plot
            viewBinding!!.actorTv.text = "Cast : " + it.actor
            setImage(it.poster)
        }
        setFav()
        viewBinding!!.favouriteImageView.setOnClickListener(::onClick)

    }

    private fun setImage(poster: String) {
        val drawable = CircularProgressDrawable(requireContext())
        drawable.setColorSchemeColors(
            R.color.purple_200,
            R.color.purple_700,
            R.color.purple_500
        )
        drawable.centerRadius = 30f
        drawable.strokeWidth = 5f
        drawable.start()
        Glide.with(requireContext())
            .load(poster)
            .placeholder(drawable)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(
                viewBinding!!.imageView
            )

    }

    fun setFav() {
        if (viewModel.isFav(movies) == 0) {
            viewBinding!!.favouriteImageView.setImageResource(R.drawable.ic_favorite_border)
        } else {
            viewBinding!!.favouriteImageView.setImageResource(R.drawable.ic_favorite)
        }
    }

    override fun onClick(view: View) {
        viewModel.insertDeleteFavList(movies)
        setFav()
    }
}