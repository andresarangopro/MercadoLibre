
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath (BuildPlugins.android)
        classpath (BuildPlugins.kotlin)
        classpath( BuildPlugins.navigationSafeArgs)
        classpath (BuildPlugins.hilt)
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}