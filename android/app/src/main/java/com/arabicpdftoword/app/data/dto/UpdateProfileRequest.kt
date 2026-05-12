package com.arabicpdftoword.app.data.dto

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("full_name")
    val fullName: String
)
