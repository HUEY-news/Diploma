package ru.practicum.android.diploma.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDetailsVacancyBinding

class VacancyDetailsFragment : Fragment() {

    private var _binding: FragmentDetailsVacancyBinding? = null
    private val binding get() = _binding!!
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
}
