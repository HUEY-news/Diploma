package ru.practicum.android.diploma.filter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltrationBinding
import ru.practicum.android.diploma.filter.presentation.FiltrationViewModel
import ru.practicum.android.diploma.filter.presentation.model.FiltrationState

class FiltrationFragment : Fragment() {
    private var _binding: FragmentFiltrationBinding? = null
    private val binding get() = _binding!!
    private var inputTextFromApply: String? = null
    private val viewModel by viewModel<FiltrationViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFiltrationBinding.inflate(inflater, container, false)
        setupToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val industry = arguments?.getString(ARGS_INDUSTRY_NAME)
            binding.filtrationIndustryTextView.text = industry
        }
        binding.apply {
            filtrationWorkPlace.setOnClickListener {
                findNavController().navigate(R.id.action_filtrationFragment_to_placeOfWorkFragment)
            }
            filtrationIndustry.setOnClickListener {
                findNavController().navigate(R.id.action_filtrationFragment_to_industryFragment)
            }
            filtrationPayCheckbox.setOnClickListener {
                viewModel.checkboxClick()
            }
            resetFilterButton.setOnClickListener {
                binding.filtrationWorkPlaceTextView.text = getString(R.string.place_of_work)
                binding.filtrationIndustryTextView.text == getString(R.string.industry)
                binding.filtrationPayCheckbox.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.icon_checkbox_off,
                        null
                    )
                )
                binding.salaryEditText.setText("")
                binding.resetFilterButton.visibility = View.GONE
                binding.applyFilterButton.visibility = View.GONE
            }
        }
        binding.salaryEditText.addTextChangedListener(
            beforeTextChanged = { s, start, count, after -> },
            onTextChanged = { s, start, before, count ->
                if (s != null) {
                    if (s.isNotEmpty()) {
                        binding.resetFilterButton.visibility = View.VISIBLE
                        if (viewModel.lastText != s.toString()) {
                            binding.applyFilterButton.visibility = View.VISIBLE
                        } else {
                            binding.applyFilterButton.visibility = View.GONE
                        }
                    } else {
                        checkFilterStateReset()
                        checkFilterStateApply()
                    }
                }
            },
            afterTextChanged = { s ->
                inputTextFromApply = s.toString()
            }
        )
        viewModel.observeCheckBoxState().observe(viewLifecycleOwner){checkBox ->
            if (checkBox) {
                binding.filtrationPayCheckbox.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.icon_checkbox_on,
                        null
                    )
                )
            } else {
                binding.filtrationPayCheckbox.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.icon_checkbox_off,
                        null
                    )
                )
            }
            checkFilterStateApply()
            checkFilterStateReset()
        }
        viewModel.observeFiltersState().observe(viewLifecycleOwner) { state ->
            when (state.first) {
                true -> {
                    binding.applyFilterButton.visibility = View.VISIBLE
                    render(state.second)
                }

                false -> {
                    binding.applyFilterButton.visibility = View.GONE
                    render(state.second)
                }
            }
        }
    }

    private fun setupToolbar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.filtrationVacancyToolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.icon_back)
        }
        binding.filtrationVacancyToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun render(state: FiltrationState) {
        when (state) {
            is FiltrationState.NoFilters -> showEmptyFilters()
            is FiltrationState.FiltersContent -> showFiltersContent(
                state.workPlace,
                state.industry,
                state.salary,
                state.checkbox
            )
        }
    }

    private fun showEmptyFilters() {
        binding.applyFilterButton.visibility = View.GONE
        binding.resetFilterButton.visibility = View.GONE
    }

    private fun showFiltersContent(workPlace: String, industry: String, salary: String, checkBox: Boolean) {
        binding.resetFilterButton.visibility = View.VISIBLE
        binding.filtrationWorkPlaceTextView.text = workPlace
        binding.filtrationIndustryTextView.text = industry
        binding.salaryEditText.setText(salary)
    }

    private fun checkFilterStateReset(){
        when{
            binding.filtrationWorkPlaceTextView.text == getString(R.string.place_of_work) && binding.filtrationIndustryTextView.text == getString(R.string.industry) && !viewModel.checkbox && binding.salaryEditText.text.isNullOrEmpty() -> binding.resetFilterButton.visibility = View.GONE
            else ->  binding.resetFilterButton.visibility = View.VISIBLE
        }
    }

    private fun checkFilterStateApply(){
        when{
            binding.filtrationWorkPlaceTextView.text == viewModel.workPlace && binding.filtrationIndustryTextView.text == viewModel.industry && binding.salaryEditText.text.toString() == viewModel.lastText -> binding.applyFilterButton.visibility = View.GONE
            else ->  binding.applyFilterButton.visibility = View.VISIBLE
        }
    }

    companion object {
        private const val ARGS_INDUSTRY_NAME = "industry_name"
        fun createArgs(industryName: String): Bundle =
            bundleOf(
                ARGS_INDUSTRY_NAME to industryName,
            )
    }
}
