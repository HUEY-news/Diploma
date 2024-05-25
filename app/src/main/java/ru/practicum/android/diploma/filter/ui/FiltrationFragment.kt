package ru.practicum.android.diploma.filter.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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
                binding.resetFilterButton.visibility = View.VISIBLE
                binding.applyFilterButton.visibility = View.VISIBLE

                viewModel.checkboxClick()
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
                       // binding.resetFilterButton.visibility = View.GONE
                    }
                }
            },
            afterTextChanged = { s ->
                inputTextFromApply = s.toString()
            }
        )
        viewModel.observeState().observe(viewLifecycleOwner) { state ->
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
        binding.filtrationPayCheckbox.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.icon_checkbox_off,
                null
            )
        )
    }

    private fun showFiltersContent(workPlace: String, industry: String, salary: String, checkBox: Boolean) {
        binding.resetFilterButton.visibility = View.VISIBLE
        binding.filtrationWorkPlaceTextView.text = workPlace
        binding.filtrationIndustryTextView.text = industry
        binding.salaryEditText.setText(salary)
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
    }

    companion object {
        private const val ARGS_INDUSTRY_NAME = "industry_name"
        fun createArgs(industryName: String): Bundle =
            bundleOf(
                ARGS_INDUSTRY_NAME to industryName,
            )
    }
}
