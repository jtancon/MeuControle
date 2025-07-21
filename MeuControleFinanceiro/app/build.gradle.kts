// app/build.gradle.kts

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Se você for usar Firebase no futuro, o plugin 'com.google.gms.google-services'
    // será adicionado aqui, mas não agora.
}

android {
    namespace = "com.example.meucontrolefinanceiro"
    compileSdk = 34 // Mantemos SDK 34, pois 35 causou problemas anteriormente

    defaultConfig {
        applicationId = "com.example.meucontrolefinanceiro"
        minSdk = 28
        targetSdk = 34 // Mantemos SDK 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Remova a seção vectorDrawables se não for mais necessária
        // vectorDrawables {
        //     useSupportLibrary = true
        // }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true // Mantenha esta linha para View Binding
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Dependências Core do Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat) // AppCompat para Activities e Toolbar
    implementation(libs.material) // Material Design Components (AppBar, CardView, FAB, etc.)
    implementation(libs.androidx.constraintlayout) // Para ConstraintLayout
    implementation(libs.androidx.recyclerview) // Para RecyclerView

    // Dependências de Teste
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
