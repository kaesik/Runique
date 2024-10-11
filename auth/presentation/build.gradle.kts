plugins {
    alias(libs.plugins.runique.android.feature.ui)
}

android {
    namespace = "com.kaesik.auth.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.auth.domain)
    implementation(project(":auth:data"))
}