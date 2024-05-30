package ru.practicum.android.diploma.filter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltrationBinding
import ru.practicum.android.diploma.filter.presentation.FiltrationViewModel
import ru.practicum.android.diploma.filter.presentation.model.AreaState
import ru.practicum.android.diploma.filter.presentation.model.CheckBoxState
import ru.practicum.android.diploma.filter.presentation.model.FiltrationState
import ru.practicum.android.diploma.filter.presentation.model.IndustryState

class FiltrationFragment : Fragment() {
    private var _binding: FragmentFiltrationBinding? = null
    private val binding get() = _binding!!
    private var inputTextFromApply: String? = null
    private val viewModel by viewModel<FiltrationViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFiltrationBinding.inflate(inflater, container, false)
        setupToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        setOnTextChangedListener()
        if (viewModel.salary != null) {
            binding.salaryEditText.setText(viewModel.salary.toString(), TextView.BufferType.EDITABLE)
        }
        viewModel.updateFilterParametersFromShared()
        viewModel.observeAreaState().observe(viewLifecycleOwner) {
            renderArea(it)
        }
        viewModel.observeIndustryState().observe(viewLifecycleOwner) {
            renderIndustry(it)
        }
        viewModel.observeCheckboxState().observe(viewLifecycleOwner) {
            renderCheckBox(it)
        }
        viewModel.observeFiltrationState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun renderCheckBox(checkBoxState: CheckBoxState) {
        when (checkBoxState) {
            is CheckBoxState.IsCheck -> setCheckBox(checkBoxState.isCheck)
        }
    }

    private fun renderIndustry(state: IndustryState) {
        when (state) {
            is IndustryState.FilterIndustryState -> showIndustry(state.industryName)
            is IndustryState.EmptyFilterIndustry -> setDefaultIndustry()
        }
    }

    private fun renderArea(state: AreaState) {
        when (state) {
            is AreaState.WorkPlaceState -> showWorkPlace(state.area)
            is AreaState.EmptyWorkplace -> setDefaultWorkplace()
        }
    }

    private fun render(state: FiltrationState) {
        when (state) {
            is FiltrationState.EmptyFilters -> showEmptyFilters()
        }
    }

    private fun showWorkPlace(workPlace: String) {
        binding.filtrationWorkPlaceTextView.text = workPlace
        showFiltersMenu()
    }

    private fun setDefaultWorkplace() {
        binding.filtrationWorkPlaceTextView.text = getString(R.string.place_of_work)
    }

    private fun showIndustry(industryName: String) {
        binding.filtrationIndustryTextView.text = industryName
        showFiltersMenu()
    }

    private fun setDefaultIndustry() {
        binding.filtrationIndustryTextView.text = getString(R.string.industry)
    }

    private fun setCheckBox(check: Boolean) {
        if (check) {
            binding.filtrationPayCheckbox.isChecked = true
            showFiltersMenu()
        }
    }

    private fun showEmptyFilters() {
        if (viewModel.salary == null) {
            binding.applyFilterButton.isVisible = false
            binding.resetFilterButton.isVisible = false
        }
    }

    private fun showFiltersMenu() {
        binding.applyFilterButton.isVisible = true
        binding.resetFilterButton.isVisible = true
    }

    private fun setOnClickListeners() {
        binding.apply {
            filtrationWorkPlace.setOnClickListener {
                findNavController().navigate(R.id.action_filtrationFragment_to_placeOfWorkFragment)
            }
            filtrationIndustry.setOnClickListener {
                findNavController().navigate(R.id.action_filtrationFragment_to_industryFragment)
            }
            filtrationPayCheckbox.setOnClickListener {
                viewModel.setCheckboxOnlyWithSalary(filtrationPayCheckbox.isChecked)
            }
            resetFilterButton.setOnClickListener {
                viewModel.clearAllFilters()
            }
            applyFilterButton.setOnClickListener {
                findNavController().navigate(R.id.action_filtrationFragment_to_searchFragment)
            }
        }
    }

    private fun setOnTextChangedListener() {
        binding.salaryEditText.addTextChangedListener(
            beforeTextChanged = { s, start, count, after -> },
            onTextChanged = { s, start, before, count -> },
            afterTextChanged = { s ->
                if (!s.isNullOrEmpty()) {
                    inputTextFromApply = s.toString()
                    viewModel.setSalary(inputTextFromApply!!)
                    showFiltersMenu()
                } else {
                    viewModel.setSalaryIsEmpty()
                }
            }
        )
    }

    private fun setupToolbar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.filtrationVacancyToolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.icon_back)
        }
        binding.filtrationVacancyToolbar.setTitleTextAppearance(requireContext(), R.style.ToolbarAppStyle)
        binding.filtrationVacancyToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
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
