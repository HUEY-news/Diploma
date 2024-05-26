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
import ru.practicum.android.diploma.filter.domain.model.FiltersSave
import ru.practicum.android.diploma.filter.presentation.FiltrationViewModel

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
            applyFilterButton.setOnClickListener {
                viewModel.saveFilters(FiltersSave(binding.filtrationWorkPlaceTextView.text.toString(),
                    binding.filtrationIndustryTextView.text.toString(),
                    inputTextFromApply,
                    viewModel.checkbox))
            }
        }
        binding.salaryEditText.addTextChangedListener(
            beforeTextChanged = { s, start, count, after -> },
            onTextChanged = { s, start, before, count -> },
            afterTextChanged = { s ->
                inputTextFromApply = s.toString()
                checkFilterStateApply()
                checkFilterStateReset()
            }
        )
        viewModel.observeCheckBoxState().observe(viewLifecycleOwner) { checkBox ->
            checkFilterStateApply()
            checkFilterStateReset()
            when (checkBox) {
                true -> {
                    binding.filtrationPayCheckbox.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.icon_checkbox_on,
                            null
                        )
                    )
                }

                false -> {
                    binding.filtrationPayCheckbox.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.icon_checkbox_off,
                            null
                        )
                    )
                }
            }
        }
        viewModel.observeFiltersState().observe(viewLifecycleOwner) { filters ->
            render(filters)
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

    private fun render(filtersSave: FiltersSave) {
        binding.resetFilterButton.visibility = View.VISIBLE
        binding.filtrationWorkPlaceTextView.text = filtersSave.workPlace
        binding.filtrationIndustryTextView.text = filtersSave.industry
        binding.salaryEditText.setText(filtersSave.salary)
        }

    private fun checkFilterStateReset() {
        when {
            binding.filtrationWorkPlaceTextView.text == getString(R.string.place_of_work)
                && binding.filtrationIndustryTextView.text == getString(R.string.industry)
                && !viewModel.checkbox
                && inputTextFromApply.isNullOrEmpty()
            -> binding.resetFilterButton.visibility = View.GONE

            else -> binding.resetFilterButton.visibility = View.VISIBLE
        }
    }

    private fun checkFilterStateApply() {
        when {
            binding.filtrationWorkPlaceTextView.text == viewModel.workPlace
                && binding.filtrationIndustryTextView.text == viewModel.industry
                && inputTextFromApply == viewModel.lastText
                && !viewModel.checkboxChange
            -> binding.applyFilterButton.visibility = View.GONE

            else -> binding.applyFilterButton.visibility = View.VISIBLE
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
