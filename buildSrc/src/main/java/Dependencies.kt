/**
 * To define plugins
 */
object BuildPlugins {
    val android by lazy { "com.android.application:${Versions.gradlePlugin}" }
    val kotlin by lazy { "org.jetbrains.kotlin.android:${Versions.kotlin}" }
    val hilt by lazy { "org.jetbrains.kotlin.android:${Versions.kotlin}" }
}

/**
 * To define dependencies
 */
object Deps {
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}" }
    val materialDesign by lazy { "com.google.android.material:material:${Versions.material}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
    val junit by lazy { "junit:junit:${Versions.jUnit}" }
    val app by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
    val ktxNavigation by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.ktx}" }
    val androidSwipeRefresh by lazy { "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.androidSwipeRefresh}" }
    val ktxUI by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.ktx}" }
    val androidTestEspresso by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }
    val androidTestJunit by lazy { "androidx.test.ext:junit:${Versions.testJunit}" }

    //hilt
    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltCompilerGoogle by lazy { "com.google.dagger:hilt-compiler:${Versions.hilt}" }
    val hiltCompilerAndroidx by lazy { "androidx.hilt:hilt-compiler:${Versions.hilt}" }

    //retrofit
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"}
    val retrofitGsonLib by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"}
    //gson
    val gson by lazy { "com.google.code.gson:gson:${Versions.gson}"}

    val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLoggingInterceptor}"
}

object ConfigData {
    const val compileSdkVersion = 32
    const val minSdkVersion = 21
    const val targetSdkVersion = 32
    const val versionCode = 1
    const val versionName = "1.0"
}