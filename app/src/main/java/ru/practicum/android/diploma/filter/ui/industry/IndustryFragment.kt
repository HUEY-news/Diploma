package ru.practicum.android.diploma.filter.ui.industry

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.filter.domain.model.Industry
import ru.practicum.android.diploma.filter.presentation.industry.IndustryViewModel
import ru.practicum.android.diploma.filter.presentation.industry.model.IndustriesState
import ru.practicum.android.diploma.filter.ui.FiltrationFragment
import java.util.Locale

class IndustryFragment : Fragment() {
    private var _binding: FragmentIndustryBinding? = null
    private val binding: FragmentIndustryBinding
        get() = _binding!!
    private var inputTextFromSearch: String? = null
    private var industryAdapter: IndustryAdapter? = null
    private var listIndustries = emptyList<Industry>()

    private val viewModel by viewModel<IndustryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentIndustryBinding.inflate(inflater, container, false)
        binding.industryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        binding.apply {
            resetImageButton.setOnClickListener {
                industryEditText.setText("")
                activity?.window?.currentFocus?.let { view ->
                    val imm =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        }
        industryAdapter = IndustryAdapter { industry ->
            viewModel.saveParameterName(industry.name)
        }
        initApplyButton()
        binding.industryRecyclerView.adapter = industryAdapter
        inputEditTextInit()
        viewModel.searchRequest()
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun initApplyButton() {
        viewModel.observeName().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val name = it
                with(binding.industryFilterButton) {
                    isVisible = true
                    setOnClickListener {
                        findNavController().navigate(
                            R.id.action_industryFragment_to_filtrationFragment,
                            FiltrationFragment.createArgs(name)
                        )
                    }
                }
            } else {
                binding.industryFilterButton.isVisible = false
            }
        }
    }

    private fun render(industriesState: IndustriesState) {
        when (industriesState) {
            is IndustriesState.Content -> showContent(industriesState.industries)
            is IndustriesState.Error -> showError(industriesState.errorMessage)
            is IndustriesState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        with(binding) {
            centerProgressBar.isVisible = true
            placeholderContainer.isVisible = false
            industryRecyclerView.isVisible = false
        }
    }

    private fun showError(errorMessage: String) {
        with(binding) {
            centerProgressBar.isVisible = false
            placeholderContainer.isVisible = true
            industryRecyclerView.isVisible = false
            placeholderTextView.isVisible = true
            placeholderImageView.isVisible = true
            placeholderTextView.text = errorMessage
            if (errorMessage == requireContext().getString(R.string.no_internet)) {
                placeholderImageView.setImageResource(R.drawable.placeholder_no_internet_connection)
            } else {
                placeholderImageView.setImageResource(R.drawable.placeholder_server_error_search)
            }
        }
    }

    private fun showContent(industries: ArrayList<Industry>) {
        with(binding) {
            centerProgressBar.isVisible = false
            placeholderContainer.isVisible = false
            industryRecyclerView.isVisible = true
        }
        with(mutableListOf<Industry>()) {
            addAll(industries)
            industryAdapter?.setItems(industries)
            listIndustries = industries
        }
    }

    private fun inputEditTextInit() {
        binding.industryEditText.addTextChangedListener(
            beforeTextChanged = { s, start, count, after -> },
            onTextChanged = { s, start, before, count ->
                binding.resetImageButton.visibility = clearButtonVisibility(s)
                if (!s.isNullOrEmpty()) {
                    inputTextFromSearch = s.toString()
                    containsMethod(inputTextFromSearch)
                }
            },
            afterTextChanged = { s ->
                inputTextFromSearch = s.toString()
            }
        )
        binding.industryEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && binding.industryEditText.text.isNotEmpty()) {
                inputTextFromSearch?.let {
                }
                true
            }
            binding.centerProgressBar.visibility = View.GONE
            false
        }
    }

    private fun containsMethod(inputTextFromSearch: String?) {
        if (!inputTextFromSearch.isNullOrEmpty()) {
            inputTextFromSearch.replaceFirstChar {
                if (it.isLowerCase()) {
                    it.titlecase(Locale.getDefault())
                } else {
                    it.toString()
                }
            }
            industryAdapter?.setItems(listIndustries.filter { industry ->
                industry.name.contains(inputTextFromSearch)
            })
        }
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun setupToolbar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.industryToolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.icon_back)
            title = getString(R.string.industry_selection)
        }
        binding.industryToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        binding.industryRecyclerView.adapter = null
        industryAdapter = null
        _binding = null
        super.onDestroyView()
    }
}
