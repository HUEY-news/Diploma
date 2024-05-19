package ru.practicum.android.diploma.search.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
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
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy
import ru.practicum.android.diploma.search.presentation.SearchViewModel
import ru.practicum.android.diploma.search.presentation.model.VacanciesState

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!
    private var isClickAllowed = true
    private var inputTextFromSearch: String? = null
    private var flagSuccessfulDownload: Boolean = false

    private val recyclerViewVacancy: RecyclerView by lazy { binding.searchRecyclerView }
    private val inputEditTextSearch: EditText by lazy { binding.searchFieldEditText }
    private val progressBarSearchCenter: ProgressBar by lazy { binding.centerProgressBar }
    private val progressBarSearchBottom: ProgressBar by lazy { binding.bottomProgressBar }
    private val placeHolderContainer: LinearLayout by lazy { binding.placeholderContainer }
    private val textViewPlaceholder: TextView by lazy { binding.placeholderTextView }
    private val imageViewPlaceholder: ImageView by lazy { binding.placeholderImageView }
    private val searchAdapter: SearchVacancyAdapter by lazy {
        SearchVacancyAdapter { vacancy ->
            if (clickDebounce()) {
                findNavController().navigate(R.id.action_searchFragment_to_detailsVacancyFragment)
            }
        }
    }
    private val allItems = mutableSetOf<SimpleVacancy>()

    private val viewModel by viewModel<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.searchRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.searchRecyclerView.adapter = searchAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scrollListener()
        binding.apply {
            searchToolbar.setTitleTextAppearance(requireContext(), R.style.ToolbarAppStyle)
            resetImageButton.setOnClickListener {
                inputEditTextSearch.setText("")
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
                    // Позиция последнего видимого элемента
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    // Общее количество элементов
                    val totalItemCount = layoutManager.itemCount
                    // Условие, определяющее, когда нужно подгрузить новые данные
                    val isLoadingNeeded = lastVisibleItemPosition + 1 == totalItemCount
                    if (isLoadingNeeded) {
                        // Загрузите больше данных
                        viewModel.currentPage++
                        viewModel.searchRequest(viewModel.lastText)
                    }
                }
            }
        })
    }

    private fun inputEditTextInit() {
        inputEditTextSearch.addTextChangedListener(
            beforeTextChanged = { s, start, count, after -> },
            onTextChanged = { s, start, before, count ->
                binding.resetImageButton.visibility = clearButtonVisibility(s)
                if (s != null) {
                    if (s.isNotEmpty() && viewModel.lastText != s.toString()) {
                        inputTextFromSearch = s.toString()
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
        inputEditTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && inputEditTextSearch.text.isNotEmpty()) {
                if (!flagSuccessfulDownload) {
                    inputTextFromSearch?.let { viewModel.searchDebounce(it) }
                }
                true
            }
            progressBarSearchCenter.visibility = View.GONE
            false
        }
    }

    private fun render(state: VacanciesState) {
        when (state) {
            is VacanciesState.Content -> {
                showContent(state.vacancies)
                val vacanciesCount = state.vacancies.size
                binding.vacancyMessageTextView.text = getString(R.string.search_results_count, vacanciesCount)
                if (vacanciesCount > 0) {
                    binding.vacancyMessageTextView.visibility = View.VISIBLE
                } else {
                    binding.vacancyMessageTextView.visibility = View.VISIBLE
                    binding.vacancyMessageTextView.text = getString(R.string.there_are_no_such_vacancies)
                }
            }
            is VacanciesState.Empty -> showEmptyTrackList(state.message)
            is VacanciesState.Error -> showErrorConnection(state.errorMessage)
            is VacanciesState.Loading -> showLoading()
        }
    }

    private fun showPlaceholderSearch() {
        progressBarSearchCenter.isVisible = false
        progressBarSearchBottom.isVisible = false
        placeHolderContainer.isVisible = true
        textViewPlaceholder.isVisible = false
        recyclerViewVacancy.isVisible = false
        imageViewPlaceholder.setImageResource(R.drawable.placeholder_empty_search)
    }

    private fun showLoading() {
        progressBarSearchCenter.isVisible = true
        progressBarSearchBottom.isVisible = false
        placeHolderContainer.isVisible = false
        recyclerViewVacancy.isVisible = false
    }

    private fun showErrorConnection(errorMessage: String) {
        hideKeyboard(requireActivity())
        progressBarSearchCenter.isVisible = false
        progressBarSearchBottom.isVisible = false
        placeHolderContainer.isVisible = true
        recyclerViewVacancy.isVisible = false
        textViewPlaceholder.isVisible = true
        textViewPlaceholder.text = errorMessage
        imageViewPlaceholder.setImageResource(R.drawable.placeholder_no_internet_connection)
    }

    private fun showEmptyTrackList(message: String) {
        hideKeyboard(requireActivity())
        progressBarSearchCenter.isVisible = false
        progressBarSearchBottom.isVisible = false
        placeHolderContainer.isVisible = true
        recyclerViewVacancy.isVisible = false
        textViewPlaceholder.isVisible = true
        textViewPlaceholder.text = message
        imageViewPlaceholder.setImageResource(R.drawable.placeholder_empty_search)
    }

    private fun showContent(vacancies: ArrayList<SimpleVacancy>) {
        allItems.addAll(vacancies)
        searchAdapter.addItemsInRecycler(vacancies)
        progressBarSearchCenter.isVisible = false
        progressBarSearchBottom.isVisible = false
        placeHolderContainer.isVisible = false
        recyclerViewVacancy.isVisible = true
    }

    fun clickDebounce(): Boolean {
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
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}
