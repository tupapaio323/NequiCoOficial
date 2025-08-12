plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.wts.nequicooficial"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.wts.nequicooficial"
        minSdk = 24
        targetSdk = 35
        versionCode = 3
        versionName = "1.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Limitar idiomas incluidos para reducir recursos
        androidResources {
            localeFilters += "es"
        }

        // Preferir vectores y evitar PNGs generados
        vectorDrawables {
            useSupportLibrary = true
        }
        
        // Configuración para compatibilidad con dispositivos de 16KB
        // Quitar abiFilters cuando se usan splits por ABI para evitar conflicto
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Ofuscar y comprimir código
            isDebuggable = false
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            // En debug, mantener readable para desarrollo
            // Dividir por ABI para evitar incluir x86/x86_64 en el mismo APK de pruebas
            applicationVariants.all {
                outputs.all {
                    // Nada aquí; configurar splits abajo
                }
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = false
        compose = true
    }

    lint {
        // No bloquear release por lint para permitir generar AAB de tamaño optimizado
        checkReleaseBuilds = false
        abortOnError = false
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    // Dividir por ABI y densidad cuando se genere bundle/apk para ahorrar tamaño por dispositivo
    @Suppress("UnstableApiUsage")
    bundle {
        language {
            // Mantener un único idioma en el bundle (resConfigs ya filtra), no dividir por idioma
            enableSplit = false
        }
        abi {
            enableSplit = true
        }
        density {
            enableSplit = true
        }
    }

    // Para APKs de debug separados por ABI (evitar problemas en dispositivos 16KB con x86)
    splits {
        abi {
            isEnable = true
            reset()
            include("armeabi-v7a", "arm64-v8a")
            isUniversalApk = false
        }
    }

    packaging {
        // Configuración para compatibilidad con dispositivos de 16KB
        jniLibs {
            useLegacyPackaging = false
            // Excluir ABIs problemáticos si es necesario (x86/x86_64)
            // keepDebugSymbols += ["arm64-v8a", "armeabi-v7a"]
        }
        resources {
            // Excluir metadatos innecesarios
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            // Excluir archivos de ML Kit que pueden causar conflictos
            excludes += "META-INF/proguard/**"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/license.txt"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.txt"
            excludes += "META-INF/notice.txt"
            excludes += "META-INF/ASL2.0"
            excludes += "META-INF/*.kotlin_module"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(project(":shared"))
    
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-appcheck-playintegrity")
    implementation("com.google.firebase:firebase-messaging:23.4.0")
    // Solo en debug para no aumentar el tamaño del release
    debugImplementation("com.google.firebase:firebase-appcheck-debug")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
    // WorkManager para notificaciones periódicas
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // ZXing eliminado: ya no se usa para alias

    // CameraX
    val cameraxVersion = "1.3.3"
    implementation("androidx.camera:camera-core:$cameraxVersion")
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation("androidx.camera:camera-view:$cameraxVersion")

    // ML Kit via Play Services: el modelo se descarga en runtime (reduce tamaño del APK)
    implementation("com.google.android.gms:play-services-mlkit-barcode-scanning:18.3.0")
    
    // Compose
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.activity:activity-compose:1.8.2")
    debugImplementation("androidx.compose.ui:ui-tooling")
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}