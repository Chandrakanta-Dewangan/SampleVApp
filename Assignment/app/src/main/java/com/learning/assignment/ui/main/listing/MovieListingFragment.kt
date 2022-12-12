package com.learning.assignment.ui.main.listing

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.learning.data.core.extension.gone
import com.learning.data.core.extension.visible
import com.learning.assignment.R
import com.learning.assignment.databinding.ListingFragmentBinding
import com.learning.assignment.ui.main.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListingFragment : BaseFragment<ListingFragmentBinding, MovieListingViewModel>() {
    lateinit var adapter: MovieAdapter
    private var search: String = ""
    private var isFavorite = false
    override val layoutId: Int
        get() = R.layout.listing_fragment

    override fun setupViewBinding(view: View): ListingFragmentBinding =
        ListingFragmentBinding.bind(view)

    override fun setupViewModel(): MovieListingViewModel {
        val viewModel: MovieListingViewModel by viewModels()
        return viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sort -> {
                viewModel.sort()
                return true
            }
            R.id.favorite -> {
                viewModel.favoriteList()
                isFavorite = true
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(inflater: LayoutInflater, view: View, savedInstanceState: Bundle?) {
        adapter = MovieAdapter(requireContext(), OnClickListener { movies, _ ->
            findNavController().navigate(
                directions = MovieListingFragmentDirections.actionMovieListingFragmentToMovieDetailsFragment(
                    movies
                )
            )
        })

        viewBinding.list.layoutManager = GridLayoutManager(requireContext(), 2)
        viewBinding.list.adapter = adapter

        var isDataLoaded = false
        viewModel.movies.observe(viewLifecycleOwner) {
            isDataLoaded = true
            setHasOptionsMenu(true)
            adapter.setAds(it)
        }

        viewModel.connectivityLiveData.observe(viewLifecycleOwner) {
            if (!it && !isDataLoaded) {
                viewModel.hideLoader()
                viewBinding.list.gone()
                viewBinding.internet.main.visible()
            } else {
                if (search.isEmpty() || !isDataLoaded) {
                    viewModel.loadData()
                    isFavorite = false
                }else if(isFavorite){
                    val favList= viewModel.favoriteList()
                    if(favList.isEmpty()){
                        isFavorite = false
                    }
                }
                viewBinding.list.visible()
                viewBinding.internet.main.gone()
            }
        }
        viewBinding.serachView.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(
                text: TextView,
                actionId: Int,
                event: KeyEvent?
            ): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_SEND
                    || event?.action == KeyEvent.ACTION_DOWN
                    && event.keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    search = text.text.toString()
                    viewModel.searchData(search)
                    //adapter.filter.filter(search)
                    hideKeyboard(requireActivity())
                    return true
                }
                return true
            }
        })
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}