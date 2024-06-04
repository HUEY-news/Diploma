package ru.practicum.android.diploma.search.ui

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.details.ui.VacancyDetailsFragment
import ru.practicum.android.diploma.filter.presentation.model.FilterSearch
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy
import ru.practicum.android.diploma.search.presentation.SearchViewModel
import ru.practicum.android.diploma.search.presentation.model.VacanciesState
import ru.practicum.android.diploma.util.Constants.VACANCIES_PER_PAGE

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!
    private var inputTextFromSearch: String? = null
    private var searchAdapter: SearchVacancyAdapter? = null

    private val viewModel by viewModel<SearchViewModel>()

    val filterSearch by lazy(LazyThreadSafetyMode.NONE) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARGS_FILTER, FilterSearch::class.java)
        } else {
            arguments?.getParcelable(ARGS_FILTER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.searchRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            viewModel.setFilterSearch(filterSearch)
            val isWorkplaceFilter = filterSearch?.countryId != null || filterSearch?.regionId != null
            val isIndustryFilter = filterSearch?.industryId != null
            val isSalaryFilter = filterSearch?.expectedSalary != null || filterSearch?.isOnlyWithSalary != false

            if (isWorkplaceFilter || isIndustryFilter || isSalaryFilter) {
                binding.filterButton.setImageResource(R.drawable.icon_filter_on)
            } else {
                binding.filterButton.setImageResource(R.drawable.icon_filter_off)
            }
        }
        scrollListener()
        searchAdapterReset()
        setupToolbar()
        binding.apply {
            resetImageButton.setOnClickListener {
                viewModel.clearText()
                searchFieldEditText.setText("")
                activity?.window?.currentFocus?.let { view ->
                    val imm =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                    showPlaceholderSearch()
                }
            }
            val editText = viewModel.getText()
            if (!editText.isNullOrEmpty()) {
                searchFieldEditText.setText(editText)
                resetImageButton.setImageResource(R.drawable.icon_close)
                resetImageButton.isVisible = true
                viewModel.searchDebounce(editText)
            }
        }
        inputEditTextInit()
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
        binding.filterButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_searchFragment_to_filtrationFragment
            )
        }
    }

    private fun scrollListener() {
        binding.searchRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.searchRecyclerView.layoutManager
                if (layoutManager is LinearLayoutManager) {
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount
                    val isLoadingNeeded = lastVisibleItemPosition + 1 == totalItemCount
                    if (isLoadingNeeded && viewModel.totalVacanciesCount > VACANCIES_PER_PAGE && dy > 0
                    ) {
                        viewModel.uploadData()
                    }
                }
            }
        })
    }

    private fun inputEditTextInit() {
        binding.searchFieldEditText.addTextChangedListener(
            beforeTextChanged = { s, start, count, after -> },
            onTextChanged = { s, start, before, count ->
                binding.resetImageButton.visibility = clearButtonVisibility(s)
                if (s != null) {
                    if (s.trim().isNotEmpty() && viewModel.lastText != s.toString()) {
                        inputTextFromSearch = s.toString()
                        binding.vacancyMessageTextView.isVisible = false
                        searchAdapterReset()
                        viewModel.searchDebounce(inputTextFromSearch!!)
                    } else if (s.trim().isEmpty()) {
                        showPlaceholderSearch()
                        binding.vacancyMessageTextView.visibility = View.GONE
                    }
                }
            },
            afterTextChanged = { s ->
                inputTextFromSearch = s.toString()
                viewModel.saveText(inputTextFromSearch!!)
            }
        )

        binding.searchFieldEditText.setOnEditorActionListener { _, actionId, _ ->
            val isActionDone = actionId == EditorInfo.IME_ACTION_DONE
            val isSearchTextNotEmpty = binding.searchFieldEditText.text.trim().isNotEmpty()
            val isDownloadNotInProgress = !viewModel.flagSuccessfulDownload

            if (isActionDone && isSearchTextNotEmpty && isDownloadNotInProgress) {
                inputTextFromSearch?.let {
                    searchAdapterReset()
                    viewModel.downloadData(it)
                }
            }
            binding.centerProgressBar.visibility = View.GONE
            false
        }
    }

    private fun searchAdapterReset() {
        searchAdapter = null
        searchAdapter = SearchVacancyAdapter { vacancy ->
            if (clickDebounce()) {
                findNavController().navigate(
                    R.id.action_searchFragment_to_detailsVacancyFragment,
                    VacancyDetailsFragment.createArgs(vacancy.id)
                )
            }
        }
        binding.searchRecyclerView.adapter = searchAdapter
    }

    private fun render(state: VacanciesState) {
        when (state) {
            is VacanciesState.Content -> {
                showContent(state.vacancies)
                binding.vacancyMessageTextView.text = getString(
                    R.string.search_results_count,
                    viewModel.totalVacanciesCount
                )
                binding.vacancyMessageTextView.isVisible = true
            }

            is VacanciesState.Empty -> {
                showEmptyResult(state.message)
                binding.vacancyMessageTextView.isVisible = true
                binding.vacancyMessageTextView.text = getString(R.string.there_are_no_such_vacancies)
            }

            is VacanciesState.ErrorToast -> {
                Toast.makeText(requireContext(), getString(R.string.error_no_internet), Toast.LENGTH_SHORT).show()
            }

            is VacanciesState.Error -> showErrorConnection(state.errorMessage)
            is VacanciesState.Loading -> showLoading()
            is VacanciesState.BottomLoading -> showBottomLoading()
        }
    }

    private fun showPlaceholderSearch() {
        with(binding) {
            centerProgressBar.isVisible = false
            bottomProgressBar.isVisible = false
            placeholderContainer.isVisible = true
            placeholderTextView.isVisible = false
            searchRecyclerView.isVisible = false
            placeholderImageView.setImageResource(R.drawable.placeholder_empty_search)
        }
    }

    private fun showLoading() {
        with(binding) {
            centerProgressBar.isVisible = true
            bottomProgressBar.isVisible = false
            placeholderContainer.isVisible = false
            searchRecyclerView.isVisible = false
            vacancyMessageTextView.isVisible = false
        }
    }

    private fun showBottomLoading() {
        with(binding) {
            centerProgressBar.isVisible = false
            bottomProgressBar.isVisible = true
            placeholderContainer.isVisible = false
            searchRecyclerView.isVisible = true
            vacancyMessageTextView.isVisible = true
        }
    }

    private fun showErrorConnection(errorMessage: String) {
        hideKeyboard(requireActivity())
        with(binding) {
            centerProgressBar.isVisible = false
            bottomProgressBar.isVisible = false
            placeholderContainer.isVisible = true
            searchRecyclerView.isVisible = false
            placeholderTextView.isVisible = true
            placeholderTextView.text = errorMessage
            vacancyMessageTextView.isVisible = false
            placeholderImageView.setImageResource(
                if (errorMessage == getString(R.string.no_internet)) {
                    R.drawable.placeholder_no_internet_connection
                } else {
                    R.drawable.placeholder_server_error_search
                }
            )
        }
    }

    private fun showEmptyResult(message: String) {
        hideKeyboard(requireActivity())
        with(binding) {
            centerProgressBar.isVisible = false
            bottomProgressBar.isVisible = false
            placeholderContainer.isVisible = true
            searchRecyclerView.isVisible = false
            placeholderTextView.isVisible = true
            placeholderTextView.text = message
            placeholderImageView.setImageResource(R.drawable.placeholder_incorrect_request)
        }
    }

    private fun showContent(vacancies: ArrayList<SimpleVacancy>) {
        with(mutableListOf<SimpleVacancy>()) {
            addAll(vacancies)
            searchAdapter?.addItemsInRecycler(vacancies)
        }
        with(binding) {
            centerProgressBar.isVisible = false
            bottomProgressBar.isVisible = false
            placeholderContainer.isVisible = false
            searchRecyclerView.isVisible = true
        }
    }

    private fun setupToolbar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.searchToolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            title = getString(R.string.search_vacancies)
        }
        binding.searchToolbar.setTitleTextAppearance(requireContext(), R.style.ToolbarAppStyle)
    }

    private fun clickDebounce(): Boolean {
        var isClickAllowed = true
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

    private fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    override fun onDestroyView() {
        searchAdapter = null
        binding.searchRecyclerView.adapter = null
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val ARGS_FILTER = "from_workplace"
        fun createArgsFilter(createFilterFromShared: FilterSearch): Bundle =
            bundleOf(
                ARGS_FILTER to createFilterFromShared,
            )

        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}
