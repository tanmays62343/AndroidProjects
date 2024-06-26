plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.navigation.safe.args)
}

android {
    namespace = "com.trx"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.trx"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    //Basic Dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Navigation Libraries
    implementation (libs.androidx.navigation.fragment.ktx.v235)
    implementation (libs.androidx.navigation.ui.ktx.v235)

    //CameraX core Library
    implementation (libs.androidx.camera.core)

    // CameraX Camera2 extensions
    implementation (libs.androidx.camera.camera2)

    // CameraX Lifecycle library
    implementation (libs.androidx.camera.lifecycle)

    // CameraX View class
    implementation (libs.androidx.camera.view)

    //WindowManager
    implementation (libs.androidx.window)


    //Dependencies for Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Dependencies for tensor flow
    implementation (libs.tensorflow.lite.task.vision)
    // Import the GPU delegate plugin Library for GPU inference
    implementation (libs.tensorflow.lite.gpu.delegate.plugin)
    implementation (libs.tensorflow.lite.gpu)

}