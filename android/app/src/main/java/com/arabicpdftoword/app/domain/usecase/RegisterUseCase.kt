package com.arabicpdftoword.app.domain.usecase

import com.arabicpdftoword.app.core.common.Resource
import com.arabicpdftoword.app.domain.model.User
import com.arabicpdftoword.app.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, fullName: String): Resource<User> {
        if (email.isBlank()) {
            return Resource.Error("البريد الإلكتروني مطلوب")
        }
        if (password.isBlank()) {
            return Resource.Error("كلمة المرور مطلوبة")
        }
        if (fullName.isBlank()) {
            return Resource.Error("الاسم الكامل مطلوب")
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Resource.Error("البريد الإلكتروني غير صحيح")
        }
        if (password.length < 6) {
            return Resource.Error("كلمة المرور يجب أن تكون 6 أحرف على الأقل")
        }
        return authRepository.register(email, password, fullName)
    }
}
