package ru.practicum.android.diploma.details.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.details.data.dto.SearchDetailsResponse
import ru.practicum.android.diploma.details.data.model.ContactsDto
import ru.practicum.android.diploma.details.data.model.EmployerDto
import ru.practicum.android.diploma.details.data.model.SalaryDto
import ru.practicum.android.diploma.details.domain.api.SearchDetailsRepository
import ru.practicum.android.diploma.details.domain.model.Contacts
import ru.practicum.android.diploma.details.domain.model.Employer
import ru.practicum.android.diploma.details.domain.model.Phone
import ru.practicum.android.diploma.details.domain.model.Salary
import ru.practicum.android.diploma.details.domain.model.Vacancy
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.sharing.data.ResourceProvider
import ru.practicum.android.diploma.util.Constants
import ru.practicum.android.diploma.util.Resource

class SearchDetailsRepositoryImpl(
    private val client: NetworkClient,
    private val resourceProvider: ResourceProvider
) : SearchDetailsRepository {

    override suspend fun searchVacancy(expression: String): Flow<Resource<Vacancy>> = flow {
        val response = client.doRequestDetails(SearchRequest(expression))
        when (response.resultCode) {
            Constants.CONNECTION_ERROR -> emit(handleConnectionError())
            Constants.SUCCESS -> emit(handleSuccessResponse(response as SearchDetailsResponse))
            else -> emit(handleServerError())
        }
    }

    private fun handleConnectionError(): Resource.Error<Vacancy> {
        return Resource.Error(resourceProvider.getErrorInternetConnection())
    }

    private fun handleServerError(): Resource.Error<Vacancy> {
        return Resource.Error(resourceProvider.getErrorServer())
    }

    private fun handleSuccessResponse(response: SearchDetailsResponse): Resource.Success<Vacancy> {
        val vacancy = createVacancyFromResponse(response)
        return Resource.Success(vacancy)
    }

    private fun createVacancyFromResponse(response: SearchDetailsResponse): Vacancy {
        return Vacancy(
            address = response.address?.city,
            alternateUrl = response.alternateUrl,
            contacts = createContactsFromResponse(response.contacts),
            description = response.description,
            employer = createEmployerFromResponse(response.employer),
            experience = response.experience?.name,
            keySkills = response.keySkills?.map { it.name },
            name = response.name,
            professionalRoles = response.professionalRoles?.map { it.name },
            salary = createSalaryFromResponse(response.salary),
            schedule = response.schedule?.name
        )
    }

    private fun createContactsFromResponse(contacts: ContactsDto?): Contacts {
        return Contacts(
            email = contacts?.email,
            name = contacts?.name,
            phones = contacts?.phones?.map { phone ->
                Phone(
                    phone?.city,
                    phone?.comment,
                    phone?.country,
                    phone?.number
                )
            }
        )
    }

    private fun createEmployerFromResponse(employer: EmployerDto?): Employer {
        return Employer(
            logoUrls = employer?.logoUrls?.original,
            name = employer?.name
        )
    }

    private fun createSalaryFromResponse(salary: SalaryDto?): Salary {
        return Salary(
            currency = salary?.currency,
            from = salary?.from,
            to = salary?.to
        )
    }
}

