package com.arabicpdftoword.app.core.util

object ChildSafetyFilter {
    private val blockedTerms = listOf(
        "gore", "violence", "weapon", "drug", "alcohol",
        "porn", "sex", "nude", "explicit", "suicide",
        "self-harm", "terrorism", "extremism"
    )

    fun checkQuery(query: String): Boolean {
        val lower = query.lowercase()
        return blockedTerms.none { lower.contains(it) }
    }

    fun filterResponse(response: String): String {
        return response
    }
}
