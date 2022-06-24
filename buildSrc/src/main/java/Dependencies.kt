/**
 * To define plugins
 */
object BuildPlugins {
    object BuildVersion{
        val gradlePlugin = "7.2.1"
        val kotlin = "1.7.0"
        val navigationSafeArgs = "2.4.2"
        val hilt = "2.42"
    }

    val android by lazy { "com.android.tools.build:gradle:${BuildVersion.gradlePlugin}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${BuildVersion.kotlin}" }
    val navigationSafeArgs by lazy { "androidx.navigation:navigation-safe-args-gradle-plugin:${BuildVersion.navigationSafeArgs}" }
    val hilt by lazy { "com.google.dagger:hilt-android-gradle-plugin:${BuildVersion.hilt}" }

    const val androidApplication = "com.android.application"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinParcelize = "kotlin-parcelize"
    const val safeArgsKotlin = "androidx.navigation.safeargs.kotlin"
    const val androidHilt = "dagger.hilt.android.plugin"
}

object ScriptPlugins {
    const val infrastructure = "scripts.infrastructure"
    const val variants = "scripts.variants"
    const val quality = "scripts.quality"
    const val compilation = "scripts.compilation"
}


/**
 * To define dependencies
 */
object Deps {
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}" }
    val materialDesign by lazy { "com.google.android.material:material:${Versions.material}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
    val app by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
    val ktxNavigation by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.ktx}" }
    val androidSwipeRefresh by lazy { "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.androidSwipeRefresh}" }
    val ktxUI by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.ktx}" }

    //hilt
    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltCompilerGoogle by lazy { "com.google.dagger:hilt-compiler:${Versions.hilt}" }
    val hiltCompilerAndroidx by lazy { "androidx.hilt:hilt-compiler:${Versions.hilt}" }
    val javaPoet by lazy { "com.squareup:javapoet:${Versions.javaPoet}" }

    //retrofit
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}" }
    val retrofitGsonLib by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}" }

    //gson
    val gson by lazy { "com.google.code.gson:gson:${Versions.gson}" }

    val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLoggingInterceptor}"
    val glide by lazy { "com.github.bumptech.glide:glide:${Versions.glide}" }

    //paging
    val pagination by lazy { "androidx.paging:paging-runtime:${Versions.pagination}" }
}

object TestDependencies {

    object TestVersions {
        const val androidCore = "2.1.0"
        const val coroutineTest = "1.3.2"
        const val mockk = "1.10.0"
        const val kluent = "1.68"
        const val adevinta = "4.1.0"
    }

    val junit by lazy { "junit:junit:${Versions.jUnit}" }
    val androidTestEspresso by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }
    val androidTestJunit by lazy { "androidx.test.ext:junit:${Versions.testJunit}" }
    val kluent by lazy { "org.amshove.kluent:kluent-android:${TestVersions.kluent}" }
    val androidxArchCore by lazy { "androidx.arch.core:core-testing:${TestVersions.androidCore}" }
    val coroutineTest by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:${TestVersions.coroutineTest}" }
    val mockk by lazy { "io.mockk:mockk:${TestVersions.mockk}" }
    val adevinta by lazy { "com.adevinta.android:barista:${TestVersions.adevinta}" }
}

object ConfigData {
    const val appId = "com.mercadolibre.items"
    const val compileSdkVersion = 32
    const val minSdkVersion = 21
    const val targetSdkVersion = 32
    const val versionCode = 1
    const val versionName = "1.0"
}

object DevLibraries {
    private object Versions {
        const val leakCanary = "2.9.1"
    }

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
}
