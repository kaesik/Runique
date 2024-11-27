plugins {
    alias(libs.plugins.runique.android.feature.ui)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.kaesik.analytics.presentation"
}

dependencies {
    implementation(projects.analytics.domain)

}
