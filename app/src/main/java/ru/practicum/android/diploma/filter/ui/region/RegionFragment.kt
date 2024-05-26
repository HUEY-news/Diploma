package ru.practicum.android.diploma.filter.ui.region

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
import ru.practicum.android.diploma.databinding.FragmentFiltrationRegionBinding
import ru.practicum.android.diploma.filter.domain.model.Area
import ru.practicum.android.diploma.filter.presentation.region.RegionViewModel
import ru.practicum.android.diploma.filter.presentation.region.model.RegionState
import ru.practicum.android.diploma.filter.ui.PlaceOfWorkFragment

class RegionFragment : Fragment() {
    private var _binding: FragmentFiltrationRegionBinding? = null
    private val binding: FragmentFiltrationRegionBinding get() = _binding!!

    private var regionAdapter: RegionAdapter? = null

    private val viewModel by viewModel<RegionViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFiltrationRegionBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        regionAdapterInit()
        binding.buttonBack.setOnClickListener { parentFragmentManager.popBackStack() }
        viewModel.searchRequest()
        viewModel.observeState().observe(viewLifecycleOwner) { render(it) }
    }

    private fun render(state: RegionState) {
        when (state) {
            is RegionState.Content -> showContent(state.regions)
            is RegionState.Error -> showError(state.errorMessage)
            is RegionState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        with(binding) {
            progressBarCountry.isVisible = true
            placeholderContainer.isVisible = false
            recyclerView.isVisible = false
        }
    }

    private fun showError(errorMessage: String) {
        with(binding) {
            progressBarCountry.isVisible = false
            placeholderContainer.isVisible = true
            recyclerView.isVisible = false
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

    private fun showContent(regions: ArrayList<Area>) {
        with(binding) {
            progressBarCountry.isVisible = false
            placeholderContainer.isVisible = false
            recyclerView.isVisible = true
        }
        with(mutableListOf<Area>()) {
            addAll(regions)
            regionAdapter?.setItems(regions)
        }
    }

    private fun regionAdapterInit() {
        regionAdapter = null
        regionAdapter = RegionAdapter { region ->
            if (region.name != null) {
                findNavController().navigate(
                    R.id.action_regionFragment_to_placeOfWorkFragment,
                    PlaceOfWorkFragment.createArgsRegionName(region.name)
                )
            }
        }
        binding.recyclerView.adapter = regionAdapter
    }

    override fun onDestroyView() {
        binding.recyclerView.adapter = null
        regionAdapter = null
        _binding = null
        super.onDestroyView()
    }
}
