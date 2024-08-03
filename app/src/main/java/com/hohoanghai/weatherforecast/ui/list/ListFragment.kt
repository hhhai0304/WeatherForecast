package com.hohoanghai.weatherforecast.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hohoanghai.weatherforecast.databinding.FragmentListBinding
import com.hohoanghai.weatherforecast.ui.list.ListFragmentDirections
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(FlowPreview::class)
class ListFragment : Fragment() {
    private val viewModel: ListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val favoriteAdapter = CityAdapter {
            viewModel.onCitySelected(it)
        }
        val suggestionAdapter = CityAdapter {
            viewModel.onCitySelected(it)
        }

        binding.apply {
            searchView.editText.addTextChangedListener {
                viewModel!!.searchTextFlow.value = it?.toString() ?: ""
            }

            rvFavoriteCities.adapter = favoriteAdapter
            rvSuggestionCities.adapter = suggestionAdapter
        }

        viewModel.apply {
            favoriteCities.observe(viewLifecycleOwner) {
                favoriteAdapter.submitList(it)
                binding.imgEmptyList.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            }
            suggestionCities.observe(viewLifecycleOwner) {
                suggestionAdapter.submitList(it)
            }
            currentCity.observe(viewLifecycleOwner) {
                if (it != null) {
                    findNavController().navigate(
                        ListFragmentDirections.actionListFragmentToDetailFragment(
                            it
                        )
                    )
                    viewModel.resetState()
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchTextFlow.debounce(400).distinctUntilChanged()
                    .collectLatest { viewModel.searchCities(it) }
            }
        }
        return binding.root
    }
}