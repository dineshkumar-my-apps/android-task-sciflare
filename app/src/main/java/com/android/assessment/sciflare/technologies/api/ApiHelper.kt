package com.android.assessment.sciflare.technologies.api

import com.android.assessment.sciflare.technologies.model.GetData
import retrofit2.Response

interface ApiHelper {

    suspend fun getData(): Response<List<GetData>>

}