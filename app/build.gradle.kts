plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "com.jassycliq.pokedex"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.jassycliq.pokedex"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0-alpha02"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "33.0.1"
    ndkVersion = "25.1.8937393"

    applicationVariants.all {
        val variant = this
        variant.addJavaSourceFoldersToModel(file("build/generated/ksp/${variant.name}/kotlin"))
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.0-alpha01")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")

    // Compose UI
    implementation(platform("androidx.compose:compose-bom:2022.12.00"))
    implementation("androidx.compose.foundation:foundation:1.4.0-alpha04")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.1.0-alpha04")
    implementation("androidx.compose.material3:material3-window-size-class:1.0.1")
    implementation("androidx.navigation:navigation-compose")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha05")
    implementation("androidx.palette:palette-ktx:1.0.0")

    // Accompanist
    implementation("com.google.accompanist:accompanist-placeholder-material:0.28.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")
    runtimeOnly("com.google.accompanist:accompanist-permissions:0.28.0")

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:3.2.0-alpha03")
    implementation("androidx.paging:paging-compose:1.0.0-alpha17")

    // Dagger TODO: Convert to Dagger once KSP is fully supported
//    implementation 'com.google.dagger:dagger:2.44'
//    kapt 'com.google.dagger:dagger-compiler:2.44'

    // Koin for Android
    implementation("io.insert-koin:koin-android:3.3.2")
    implementation("io.insert-koin:koin-annotations:1.1.0")
    implementation("io.insert-koin:koin-androidx-compose:3.4.1")
    ksp("io.insert-koin:koin-ksp-compiler:1.1.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    //okHttp
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    //Coil (Image Uploading)
    implementation("io.coil-kt:coil-bom:2.2.2")
    implementation("io.coil-kt:coil")
    implementation("io.coil-kt:coil-compose")

    // Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")

    // Room
    implementation("androidx.room:room-runtime:2.5.0")
    implementation("androidx.room:room-ktx:2.5.0")
    implementation("androidx.room:room-paging:2.5.0")
    ksp("androidx.room:room-compiler:2.5.0")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Eithernet
//    implementation("com.slack.eithernet:eithernet:1.3.1")
    implementation("com.github.skydoves:sandwich:1.3.3")
    implementation("com.github.skydoves:sandwich-datasource:1.3.3")

    // Orbit MVI
    implementation("org.orbit-mvi:orbit-core:4.5.0")
    implementation("org.orbit-mvi:orbit-viewmodel:4.5.0")
    implementation("org.orbit-mvi:orbit-compose:4.5.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.12.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
