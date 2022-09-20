package com.android.assessment.sciflare.technologies.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetData {

    @SerializedName("_id")
    @Expose
    var _id: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("mobile")
    @Expose
    var mobile: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("gender")
    @Expose
    var gender: String? = null
}