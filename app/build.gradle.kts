plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinParcelize)
    id(BuildPlugins.safeArgsKotlin)
    id(BuildPlugins.androidHilt)

    kotlin("android")
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = ConfigData.appId
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildTypes {
        getByName("release") {
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

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(Deps.app)
    implementation(Deps.appCompat)
    implementation(Deps.materialDesign)
    implementation(Deps.constraintLayout)
    implementation(Deps.ktxNavigation)
    implementation(Deps.ktxUI)
    implementation(Deps.hilt)
    implementation(Deps.androidSwipeRefresh)
    implementation(Deps.retrofit)
    implementation(Deps.gson)
    implementation(Deps.retrofitGsonLib)
    implementation(Deps.okHttpLoggingInterceptor)
    implementation(Deps.pagination)
    implementation(Deps.glide)
    implementation(Deps.javaPoet)
    kapt(Deps.hiltCompilerGoogle)

    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.coroutineTest)
    testImplementation (TestDependencies.androidxArchCore)
    testImplementation (TestDependencies.mockk)
    testImplementation (TestDependencies.kluent)

    androidTestImplementation (TestDependencies.androidTestEspresso)
    androidTestImplementation (TestDependencies.androidTestJunit)
    androidTestImplementation (TestDependencies.adevinta){
        exclude(group = "org.jetbrains.kotlin")
    }
}