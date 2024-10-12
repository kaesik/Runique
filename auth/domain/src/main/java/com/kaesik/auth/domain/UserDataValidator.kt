package com.kaesik.auth.domain

class UserDataValidator (
    private val patternValidator: PatternValidator
) {
    fun isValidEmail(email: String): Boolean {
        return patternValidator.matches(email.trim())
    }

    fun validatePassword(password: String): PasswordValidationState {
        val hasMinLength = password.length >= MIN_PASSWORD_LENGTH
        val hasDigit = password.any { it.isDigit() }
        val hasLowerCaseChar = password.any { it.isLowerCase() }
        val hasUpperCaseChar = password.any { it.isUpperCase() }

        return PasswordValidationState(
            hasMinimalLength = hasMinLength,
            hasNumber = hasDigit,
            hasLowerCaseChar = hasLowerCaseChar,
            hasUpperCaseChar = hasUpperCaseChar
        )
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 9
    }
}