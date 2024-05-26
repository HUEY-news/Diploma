package ru.practicum.android.diploma.filter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltrationWorkplaceBinding

class PlaceOfWorkFragment : Fragment() {
    private var _binding: FragmentFiltrationWorkplaceBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltrationWorkplaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val countryName = arguments?.getString(ARGS_COUNTRY_NAME)
            binding.filtrationCountryUnselected.isVisible = false
            binding.filtrationCountySelected.isVisible = true
            binding.filtrationCountryName.text = countryName
        }
        binding.apply {
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
    }
}
