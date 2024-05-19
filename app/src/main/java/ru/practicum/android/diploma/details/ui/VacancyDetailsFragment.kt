package ru.practicum.android.diploma.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDetailsVacancyBinding
import ru.practicum.android.diploma.details.presentation.VacancyDetailsViewModel

class VacancyDetailsFragment : Fragment() {

    private var _binding: FragmentDetailsVacancyBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<VacancyDetailsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsVacancyBinding.inflate(inflater, container, false)
        setupToolbar()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var vacancyId = arguments?.getString(ARGS_VACANCY_ID)
        if (vacancyId != null) {
            viewModel.searchRequest(vacancyId)
        }
    }

    private fun setupToolbar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.detailsVacancyToolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.icon_back)
            title = getString(R.string.job_vacancy)
        }

        binding.detailsVacancyToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        private const val ARGS_VACANCY_ID = "vacancy_id"
        fun createArgs(vacancyId: String): Bundle {
            val bundle = Bundle()
            bundle.putString("ARGS_VACANCY_ID", vacancyId)
            return bundle
        }
    }
}
