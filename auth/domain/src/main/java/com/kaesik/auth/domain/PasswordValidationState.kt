package com.kaesik.auth.domain

data class PasswordValidationState (
    val hasMinimalLength: Boolean = false,
    val hasNumber: Boolean = false,
    val hasLowerCaseChar: Boolean = false,
    val hasUpperCaseChar: Boolean = false,
) {
    val isValidPassword: Boolean
        get() = hasMinimalLength && hasNumber && hasLowerCaseChar && hasUpperCaseChar
}