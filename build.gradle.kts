buildscript {
    val kotlinVersion = "1.5.21"

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
        classpath("com.android.tools.build:gradle:7.1.0")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.3")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.6.10")


    }

}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}