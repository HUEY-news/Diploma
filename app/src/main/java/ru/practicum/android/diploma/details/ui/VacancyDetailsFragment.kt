package ru.practicum.android.diploma.details.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDetailsVacancyBinding
import ru.practicum.android.diploma.details.domain.model.Contacts
import ru.practicum.android.diploma.details.domain.model.Phone
import ru.practicum.android.diploma.details.domain.model.Salary
import ru.practicum.android.diploma.details.domain.model.Vacancy
import ru.practicum.android.diploma.details.presentation.VacancyDetailsViewModel
import ru.practicum.android.diploma.details.presentation.model.StateLoadVacancy
import ru.practicum.android.diploma.util.dpToPx

class VacancyDetailsFragment : Fragment() {

    private var _binding: FragmentDetailsVacancyBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<VacancyDetailsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
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
        val vacancyId = requireArguments().getString(ARGS_VACANCY_ID)
        if (vacancyId != null) {
            viewModel.searchRequest(vacancyId)
        }
        viewModel.observeVacancy().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun showContent(vacancy: Vacancy) {
        binding.apply {
            progressBar.isVisible = false
            vacancyDetailsNestedScrollView.isVisible = true
            placeholderContainer.isVisible = false
            shareButton.setOnClickListener { viewModel.shareVacancy(vacancy.alternateUrl.toString()) }
            vacancyNameTextView.text = vacancy.name
            salaryTextView.text = getSalary(vacancy.salary)
            Glide.with(employerLogo)
                .load(vacancy.employer?.logoUrls)
                .placeholder(R.drawable.icon_android_placeholder)
                .transform(CenterCrop(), RoundedCorners(dpToPx(RADIUS_IN_DP)))
                .into(employerLogo)
            employerNameTextView.text = vacancy.employer?.name
            employerCityTextView.text = vacancy.address
            experienceTextView.text = vacancy.experience
            employmentAndScheduleTextView.text = vacancy.schedule
            if (vacancy.description != null) {
                vacancyDescriptionValueTextView.setText(
                    Html.fromHtml(
                        vacancy.description,
                        Html.FROM_HTML_MODE_COMPACT
                    )
                )
            } else {
                sectionVacancyDescription.isVisible = false
            }
            if (vacancy.keySkills.isNullOrEmpty()) {
                sectionKeySkills.isVisible = false
                keySkillsValueTextView.isVisible = false
            } else {
                keySkillsValueTextView.text = getKeySkills(vacancy.keySkills)
            }
            Log.d("VacancyContacts", vacancy.contacts.toString())
            showVacancyContacts(vacancy.contacts)
        }
    }

    private fun showVacancyContacts(contacts: Contacts?) {
        with(binding) {
            if (contacts?.name.isNullOrEmpty() &&
                contacts?.email.isNullOrEmpty() && contacts?.phones.isNullOrEmpty()
            ) {
                contactsContainer.isVisible = false
            } else {
                contactsContainer.isVisible = true
                contactPersonValueTextView.text = contacts?.name
                if (!contacts?.email.isNullOrEmpty()) {
                    emailValueTextView.isClickable = true
                    emailValueTextView.isVisible = true
                    emailValueTextView.text = contacts?.email
                    emailValueTextView.setOnClickListener {
                        contacts?.email?.let { mail -> viewModel.writeToEmployer(mail) }
                    }
                }
                if (!contacts?.phones.isNullOrEmpty()) {
                    phoneValueTextView.isClickable = true
                    phoneValueTextView.isVisible = true
                    phoneValueTextView.text = getPhones(contacts?.phones)
                    phoneValueTextView.setOnClickListener {
                        viewModel.callPhoneNumber(getPhones(contacts?.phones))
                    }
                }
                commentValueTextView.text = getComments(contacts?.phones)
            }
        }
    }

    private fun getComments(phones: List<Phone>?): String {
        var resultString = ""
        phones?.forEach { phone ->
            resultString += "${phone.comment} \n"
        }
        return resultString
    }

    private fun getPhones(phones: List<Phone>?): String {
        var resultString = ""
        phones?.forEach { phone ->
            resultString += "${phone.country} ${phone.city} " +
                "${phone.number} \n"
        }
        return resultString
    }

    private fun getKeySkills(keySkills: List<String?>?): String {
        var resultString = ""
        if (keySkills.isNullOrEmpty()) {
            binding.sectionKeySkills.isVisible = false
        } else {
            binding.sectionKeySkills.isVisible = true
            keySkills.forEach { skill ->
                resultString += "$skill+\n"
            }
        }
        return resultString
    }

    @SuppressLint("StringFormatMatches")
    private fun getSalary(salary: Salary?): String {
        return when {
            salary == null -> getString(R.string.salary_not_specified)
            salary.from != null && salary.to != null -> getString(
                R.string.salary_from_to,
                salary.from,
                salary.to,
                salary.currency
            )

            salary.from != null -> getString(
                R.string.salary_from,
                salary.from,
                salary.currency
            )

            salary.to != null -> getString(
                R.string.salary_to,
                salary.to,
                salary.currency
            )

            else -> getString(R.string.salary_not_specified)
        }
    }

    private fun render(state: StateLoadVacancy) {
        when (state) {
            is StateLoadVacancy.Content -> {
                showContent(state.vacancies)
            }

            is StateLoadVacancy.Error -> {
                showError(state.errorMessage)
            }

            is StateLoadVacancy.Loading -> {
                showProgressBar()
            }
        }
    }

    private fun showProgressBar() {
        with(binding) {
            progressBar.isVisible = true
            vacancyDetailsNestedScrollView.isVisible = false
            placeholderContainer.isVisible = false
        }
    }

    private fun showError(message: String) {
        with(binding) {
            progressBar.isVisible = false
            vacancyDetailsNestedScrollView.isVisible = false
            placeholderContainer.isVisible = true
            placeholderMessage.text = message
            placeholderImage.setImageResource(R.drawable.placeholder_server_error_vacancy)
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
        private const val RADIUS_IN_DP = 8
        fun createArgs(vacancyId: String): Bundle =
            bundleOf(
                ARGS_VACANCY_ID to vacancyId,
            )
    }
}
