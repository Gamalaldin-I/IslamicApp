plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-kapt") // Apply the kapt plugin

}

android {
    namespace = "com.example.zekronhakeem"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.zekronhakeem"
        minSdk = 26
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        viewBinding = true
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Coroutines dependencies
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)

    // Room dependencies
    implementation (libs.androidx.room.runtime)
    //noinspection KaptUsageInsteadOfKsp
    kapt (libs.androidx.room.compiler)

    // Coroutines support for Room
    implementation (libs.androidx.room.ktx)

    // Retrofit dependencies
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // Retrofit coroutine support
    implementation (libs.retrofit.v290)
    implementation (libs.okhttp)
    (libs.retrofit2.kotlin.coroutines.adapter)
    implementation (libs.androidx.viewpager2)
    //Gson
    implementation (libs.gson)
    // ViewModel
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    //paging
    implementation (libs.androidx.paging.runtime)

}

