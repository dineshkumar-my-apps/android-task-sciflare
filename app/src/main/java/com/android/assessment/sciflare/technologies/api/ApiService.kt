package com.android.assessment.sciflare.technologies.api

import com.android.assessment.sciflare.technologies.model.GetData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("827b2bb1794c42198a3aab1e7a4aa293/dinesh-task")
    suspend fun getData(): Response<List<GetData>>

}