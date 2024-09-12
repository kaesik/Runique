package com.kaesik.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

internal fun  Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>
) {
    commonExtension.defaultConfig {
        compileSdk = libs.findVersion("projectCompileSdk").get().toString().toInt()
        defaultConfig.minSdk = libs.findVersion("projectMinSdk").get().toString().toInt()

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }

    dependencies {

    }
}