plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    android()

    // iOS targets: solo en macOS, para evitar descargar toolchains en Windows
    val isMacOs = org.gradle.internal.os.OperatingSystem.current().isMacOsX
    if (isMacOs) {
        iosX64()
        iosArm64()
        iosSimulatorArm64()
    }

    if (isMacOs) {
        targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().configureEach {
            binaries.framework {
                baseName = "shared"
                isStatic = false
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Añadiremos dependencias comunes (ktor, serialization, settings) según migremos lógica
            }
        }
        val commonTest by getting

        val androidMain by getting {
            dependencies {
                // Dependencias específicas Android si son necesarias
            }
        }
        val androidUnitTest by getting

        if (isMacOs) {
            val iosX64Main by getting
            val iosArm64Main by getting
            val iosSimulatorArm64Main by getting
            val iosMain by creating {
                dependsOn(commonMain)
                iosX64Main.dependsOn(this)
                iosArm64Main.dependsOn(this)
                iosSimulatorArm64Main.dependsOn(this)
            }
            val iosX64Test by getting
            val iosArm64Test by getting
            val iosSimulatorArm64Test by getting
            val iosTest by creating {
                dependsOn(commonTest)
                iosX64Test.dependsOn(this)
                iosArm64Test.dependsOn(this)
                iosSimulatorArm64Test.dependsOn(this)
            }
        }
    }
}

android {
    namespace = "com.wts.nequicooficial.shared"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
    }
}


