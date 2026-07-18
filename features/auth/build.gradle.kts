plugins {
    alias(libs.plugins.android.library)

    alias(libs.plugins.kotlin.compose)

    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.auth"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {

    implementation(project(":core:model"))
    implementation(project(":core:network"))

    implementation("com.google.dagger:hilt-android:2.57.1")
    implementation(libs.androidx.material3)
    ksp("com.google.dagger:hilt-android-compiler:2.57.1")

    implementation("androidx.navigation:navigation-compose:2.9.8")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")


    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}