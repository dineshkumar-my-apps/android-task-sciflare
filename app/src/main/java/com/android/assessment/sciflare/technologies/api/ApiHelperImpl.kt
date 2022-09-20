package com.android.assessment.sciflare.technologies.api

import com.android.assessment.sciflare.technologies.model.GetData
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getData(): Response<List<GetData>> = apiService.getData()

}