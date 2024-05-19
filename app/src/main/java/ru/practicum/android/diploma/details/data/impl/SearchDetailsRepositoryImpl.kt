package ru.practicum.android.diploma.details.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.details.data.dto.SearchDetailsResponse
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

class SearchDetailsRepositoryImpl(private val client: NetworkClient, private val resourceProvider: ResourceProvider) :
    SearchDetailsRepository {
    override suspend fun searchVacancy(expression: String): Flow<Resource<Vacancy>> = flow {
        val response = client.doRequestDetails(SearchRequest(expression))
        when (response.resultCode) {
            Constants.CONNECTION_ERROR -> emit(Resource.Error(resourceProvider.getErrorInternetConnection()))

            Constants.SUCCESS -> {
                (response as SearchDetailsResponse)
                val vacancy =
                    Vacancy(
                        address = response.address?.city,
                        alternateUrl = response.alternateUrl,
                        contacts = Contacts(
                            email = response.contacts?.email,
                            name = response.contacts?.name,
                            phones = response.contacts?.phones?.map { phone ->
                                Phone(
                                    phone?.city,
                                    phone?.country,
                                    phone?.number
                                )
                            }
                        ),
                        description = response.description,
                        employer = Employer(
                            logoUrls =
                            response.employer?.logoUrls?.original,
                            name = response.employer?.name
                        ),
                        experience = response.experience?.name,
                        keySkills = response.keySkills?.map { skill ->
                            skill.name
                        },
                        name = response.name,
                        professionalRoles = response.professionalRoles?.map { professionalRole ->
                            professionalRole.name
                        },
                        salary = Salary(
                            currency = response.salary?.currency,
                            from = response.salary?.from,
                            to = response.salary?.to,
                        ),
                        schedule = response.schedule?.name,
                    )
                emit(Resource.Success(vacancy))
            }

            else -> emit(Resource.Error(resourceProvider.getErrorServer()))
        }
    }
}

