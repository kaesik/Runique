import com.android.build.api.dsl.ApplicationExtension
import com.kaesik.convention.ExtensionType
import com.kaesik.convention.configureBuildTypes
import com.kaesik.convention.configureKotlinAndroid
import com.kaesik.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
                apply("com.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = libs.findVersion("prjectApplicationId").get().toString()
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()
                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()
                }
            }

            configureKotlinAndroid(this.extensions.getByType())

            configureBuildTypes(
                commonExtension = this.extensions.getByType(),
                extensionType = ExtensionType.APPLICATION
            )
        }
    }
}