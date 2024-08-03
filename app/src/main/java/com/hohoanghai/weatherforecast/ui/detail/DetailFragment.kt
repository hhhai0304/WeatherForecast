package com.hohoanghai.weatherforecast.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hohoanghai.weatherforecast.R
import com.hohoanghai.weatherforecast.databinding.FragmentDetailBinding
import com.hohoanghai.weatherforecast.ui.detail.viewmodel.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().navigate(R.id.listFragment)
            }
            btnRetry.setOnClickListener {
                viewModel!!.getCurrentWeather()
            }
        }

        val city = DetailFragmentArgs.fromBundle(requireArguments()).selectedCity
        viewModel.getCurrentWeather(city)

        return binding.root
    }
}