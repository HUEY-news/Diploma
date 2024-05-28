package ru.practicum.android.diploma.filter.ui.workplace

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltrationWorkplaceBinding
import ru.practicum.android.diploma.filter.presentation.workplace.PlaceOfWorkViewModel
import ru.practicum.android.diploma.filter.presentation.workplace.model.AreaState

class PlaceOfWorkFragment : Fragment() {
    private var _binding: FragmentFiltrationWorkplaceBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<PlaceOfWorkViewModel>()
    private var savedArgumentCountry: String? = null
    private var savedArgumentRegion: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFiltrationWorkplaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDefaultScreenState()
        if (arguments != null) {
            var countryName = arguments?.getString(ARGS_COUNTRY_NAME)
            if (countryName == null) {
                countryName = savedArgumentCountry
                Log.d("WorkPlace", countryName.toString())
            }
            var regionName = arguments?.getString(ARGS_REGION_NAME)
            if (regionName == null) {
                regionName = savedArgumentRegion
                Log.d("WorkPlace", countryName.toString())
            }
            if (countryName != null && regionName != null) {
                viewModel.setState(state = AreaState.FullArea(country = countryName, region = regionName))
            } else if (countryName != null) {
                viewModel.setState(state = AreaState.CountryName(country = countryName))
            } else if (regionName != null) {
                viewModel.setState(state = AreaState.RegionName(region = regionName))
            }
        }
        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        savedArgumentCountry = savedInstanceState?.getString(ARGS_COUNTRY_NAME)
        savedArgumentRegion = savedInstanceState?.getString(ARGS_REGION_NAME)
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (arguments != null) {
            if (arguments?.getString(ARGS_COUNTRY_NAME) != null) {
                outState.putString(ARGS_COUNTRY_NAME, arguments?.getString(ARGS_COUNTRY_NAME))
            } else if (arguments?.getString(ARGS_REGION_NAME) != null) {
                outState.putString(ARGS_REGION_NAME, arguments?.getString(ARGS_COUNTRY_NAME))
            }
        }
        super.onSaveInstanceState(outState)
    }

    private fun render(state: AreaState) {
        when (state) {
            is AreaState.Empty -> setDefaultScreenState()
            is AreaState.CountryName -> setCountryNameScreenState(state.country)
            is AreaState.RegionName -> setRegionNameScreenState(state.region)
            is AreaState.FullArea -> setFullAreaScreenState(state.country, state.region)
        }
    }

    private fun setFullAreaScreenState(countryName: String, regionName: String) {
        binding.apply {
            filtrationRegionUnselected.isVisible = false
            filtrationRegionSelected.isVisible = true
            filtrationRegionName.text = regionName
            filtrationRegionSelected.setOnClickListener {
                findNavController().navigate(R.id.action_placeOfWorkFragment_to_regionFragment)
            }
            filtrationCountryUnselected.isVisible = false
            filtrationCountySelected.isVisible = true
            filtrationCountryName.text = countryName
            filtrationCountySelected.setOnClickListener {
                findNavController().navigate(R.id.action_placeOfWorkFragment_to_countryFragment)
            }
        }
    }

    private fun setRegionNameScreenState(regionName: String) {
        binding.apply {
            filtrationRegionUnselected.isVisible = false
            filtrationRegionSelected.isVisible = true
            filtrationRegionName.text = regionName
            filtrationRegionSelected.setOnClickListener {
                findNavController().navigate(R.id.action_placeOfWorkFragment_to_regionFragment)
            }
        }
    }

    private fun setCountryNameScreenState(countryName: String) {
        binding.apply {
            filtrationCountryUnselected.isVisible = false
            filtrationCountySelected.isVisible = true
            filtrationCountryName.text = countryName
            filtrationCountySelected.setOnClickListener {
                findNavController().navigate(R.id.action_placeOfWorkFragment_to_countryFragment)
            }
        }
    }

    private fun setDefaultScreenState() {
        with(binding) {
            buttonBack.setOnClickListener { parentFragmentManager.popBackStack() }
            filtrationCountryUnselected.setOnClickListener {
                findNavController().navigate(R.id.action_placeOfWorkFragment_to_countryFragment)
            }
            filtrationRegionUnselected.setOnClickListener {
                findNavController().navigate(R.id.action_placeOfWorkFragment_to_regionFragment)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val ARGS_COUNTRY_NAME = "country_name"
        private const val ARGS_REGION_NAME = "region_name"
        fun createArgsCountryName(name: String): Bundle =
            bundleOf(
                ARGS_COUNTRY_NAME to name,
            )

        fun createArgsRegionName(name: String): Bundle =
            bundleOf(
                ARGS_REGION_NAME to name,
            )
    }
}
