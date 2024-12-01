package com.kaesik.analytics.presentation

sealed interface AnalyticsAction {
    data object OnBackClick: AnalyticsAction
}
