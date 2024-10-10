package com.kaesik.auth.domain

interface PatternValidator {
    fun matches(email: String): Boolean
}