plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.app.roombasic"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.roombasic"
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //添加Room依赖
    implementation("androidx.room:room-runtime:2.2.0-alpha01")
    annotationProcessor("androidx.room:room-compiler:2.2.0-alpha01")
    // Test helpers
    testImplementation("androidxcom:room-testing:2.2.0-alpha01")
}