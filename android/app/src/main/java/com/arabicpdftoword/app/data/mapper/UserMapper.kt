package com.arabicpdftoword.app.data.mapper

import com.arabicpdftoword.app.core.util.DateUtils
import com.arabicpdftoword.app.data.dto.AuthResponse
import com.arabicpdftoword.app.data.dto.UserResponse
import com.arabicpdftoword.app.domain.model.User

fun UserResponse.toDomainModel(): User {
    return User(
        id = this.id,
        email = this.email,
        fullName = this.fullName ?: "",
        isPremium = this.isPremium,
        premiumUntil = this.premiumUntil?.let { DateUtils.parseApiDate(it) },
        conversionCount = this.conversionCount,
        dailyConversionCount = this.dailyConversionCount
    )
}

fun AuthResponse.toUserModel(): User {
    return User(
        id = this.user?.id ?: "",
        email = this.user?.email ?: "",
        fullName = this.user?.fullName ?: "",
        isPremium = this.user?.isPremium ?: false,
        premiumUntil = this.user?.premiumUntil?.let { DateUtils.parseApiDate(it) },
        conversionCount = this.user?.conversionCount ?: 0,
        dailyConversionCount = this.user?.dailyConversionCount ?: 0
    )
}
