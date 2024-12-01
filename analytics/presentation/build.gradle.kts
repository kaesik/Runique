plugins {
    alias(libs.plugins.runique.android.feature.ui)
}

android {
    namespace = "com.kaesik.analytics.presentation"
}

dependencies {
    implementation(projects.analytics.domain)

}
