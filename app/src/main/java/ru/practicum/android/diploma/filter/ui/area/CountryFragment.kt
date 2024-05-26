package ru.practicum.android.diploma.filter.ui.area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltrationCountryBinding
import ru.practicum.android.diploma.filter.domain.model.Country
import ru.practicum.android.diploma.filter.presentation.country.CountryViewModel
import ru.practicum.android.diploma.filter.presentation.country.model.CountryState
import ru.practicum.android.diploma.filter.ui.PlaceOfWorkFragment

class CountryFragment : Fragment() {
    private var _binding: FragmentFiltrationCountryBinding? = null
    private val binding: FragmentFiltrationCountryBinding get() = _binding!!

    private var countryAdapter: CountryAdapter? = null

    private val viewModel by viewModel<CountryViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltrationCountryBinding.inflate(inflater, container, false)
        binding.countryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countryAdapterInit()
        binding.buttonBack.setOnClickListener { parentFragmentManager.popBackStack() }
        viewModel.searchRequest()
        viewModel.observeState().observe(viewLifecycleOwner) { render(it) }
    }

    private fun render(state: CountryState) {
        when (state) {
            is CountryState.Content -> showContent(state.countries)
            is CountryState.Error -> showError(state.errorMessage)
            is CountryState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        with(binding) {
            progressBar.isVisible = true
            placeholderContainer.isVisible = false
            countryRecyclerView.isVisible = false
        }
    }

    private fun showError(errorMessage: String) {
        with(binding) {
            progressBar.isVisible = false
            placeholderContainer.isVisible = true
            countryRecyclerView.isVisible = false
            placeholderMessage.isVisible = true
            placeholderImage.isVisible = true
            placeholderMessage.text = errorMessage
            if (errorMessage == requireContext().getString(R.string.no_internet)) {
                placeholderImage.setImageResource(R.drawable.placeholder_no_internet_connection)
            } else {
                placeholderImage.setImageResource(R.drawable.placeholder_server_error_search)
            }
        }
    }

    private fun showContent(countries: ArrayList<Country>) {
        with(binding) {
            progressBar.isVisible = false
            placeholderContainer.isVisible = false
            countryRecyclerView.isVisible = true
        }
        with(mutableListOf<Country>()) {
            addAll(countries)
            countryAdapter?.setItems(countries)
        }
    }

    private fun countryAdapterInit() {
        countryAdapter = null
        countryAdapter = CountryAdapter { country ->
            if (country.name != null) {
                findNavController().navigate(
                    R.id.action_countryFragment_to_placeOfWorkFragment,
                    PlaceOfWorkFragment.createArgsCountryName(country.name)
                )
            }
        }
        binding.countryRecyclerView.adapter = countryAdapter
    }

    override fun onDestroyView() {
        binding.countryRecyclerView.adapter = null
        countryAdapter = null
        _binding = null
        super.onDestroyView()
    }
}
