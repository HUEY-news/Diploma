package ru.practicum.android.diploma.search.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy
import ru.practicum.android.diploma.search.presentation.SearchViewModel
import ru.practicum.android.diploma.search.presentation.model.VacanciesState
import ru.practicum.android.diploma.util.Constants.VACANCIES_PER_PAGE

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!
    private var inputTextFromSearch: String? = null
    private var flagSuccessfulDownload: Boolean = false
    private var searchAdapter: SearchVacancyAdapter? = null

    private val viewModel by viewModel<SearchViewModel>()

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
        scrollListener()
        searchAdapterReset()
        binding.apply {
            searchToolbar.setTitleTextAppearance(requireContext(), R.style.ToolbarAppStyle)
            resetImageButton.setOnClickListener {
                searchFieldEditText.setText("")
                activity?.window?.currentFocus?.let { view ->
                    val imm =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                    showPlaceholderSearch()
                }
            }
        }
        inputEditTextInit()
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
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
                    if (isLoadingNeeded && (lastVisibleItemPosition < viewModel.totalVacanciesCount
                            && viewModel.totalVacanciesCount > VACANCIES_PER_PAGE)
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
                    if (s.isNotEmpty() && viewModel.lastText != s.toString()) {
                        inputTextFromSearch = s.toString()
                        searchAdapterReset()
                        viewModel.searchDebounce(inputTextFromSearch!!)
                    } else {
                        showPlaceholderSearch()
                        binding.vacancyMessageTextView.visibility = View.GONE
                    }
                }
            },
            afterTextChanged = { s ->
                inputTextFromSearch = s.toString()
            }
        )
        binding.searchFieldEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && binding.searchFieldEditText.text.isNotEmpty()) {
                if (!flagSuccessfulDownload) {
                    inputTextFromSearch?.let {
                        searchAdapterReset()
                        viewModel.downloadData(it)
                    }
                }
                true
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
            searchRecyclerView.isVisible = false
            vacancyMessageTextView.isVisible = false
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
            if (errorMessage == requireContext().getString(R.string.no_internet)) {
                placeholderImageView.setImageResource(R.drawable.placeholder_no_internet_connection)
            } else {
                placeholderImageView.setImageResource(R.drawable.placeholder_server_error_search)
            }
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
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}
