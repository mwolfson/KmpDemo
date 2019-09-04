import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask

buildscript {
    repositories {
        mavenLocal()
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://plugins.gradle.org/m2/")
        google()
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.50")
        classpath("com.android.tools.build:gradle:3.5.0")
        classpath("co.touchlab:kotlinxcodesync:0.1.5")
    }
}

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

allprojects {
    repositories {
        mavenLocal()
        google()
        jcenter()
        mavenCentral()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://kotlin.bintray.com/ktor")
        maven("https://kotlin.bintray.com/kotlinx")
    }
}

group = "com.ableandroid.kmpdemo"
version = "0.0.1"

apply(plugin = "maven-publish")
apply(plugin = "com.android.library")
apply(plugin = "kotlin-android-extensions")
apply(plugin = "org.jetbrains.kotlin.multiplatform")

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
    }
}

kotlin {
    android()
    val frameworkName = "SharedCode"
    val ios = iosX64("ios") {
        binaries.framework {
            baseName = frameworkName
        }
    }

    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }
        val iosTest by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }
    }

    tasks.create("framework", FatFrameworkTask::class) {
        baseName = frameworkName
        from(
            ios.binaries.getFramework("DEBUG")
        )
        destinationDir = buildDir.resolve("xcode-frameworks")
        group = "iOS frameworks"
        description = "Builds a simulator only framework to build from xcode directly"
        doLast {
            val file = File(destinationDir, "gradlew")
            file.writeText(text = "#!/bin/bash\nexport JAVA_HOME=${System.getProperty("java.home")}\ncd ${rootProject.rootDir}\n./gradlew \$@\n")
            file.setExecutable(true)
        }
    }
}

val packForXcode by tasks.creating(Sync::class) {
    val targetDir = File(buildDir, "xcode-frameworks")

    /// selecting the right configuration for the iOS
    /// framework depending on the environment
    /// variables set by Xcode build
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets
        .getByName<KotlinNativeTarget>("ios")
        .binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    from({ framework.outputDirectory })
    into(targetDir)

    /// generate a helpful ./gradlew wrapper with embedded Java path
    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText("#!/bin/bash\n"
                + "export 'JAVA_HOME=${System.getProperty("java.home")}'\n"
                + "cd '${rootProject.rootDir}'\n"
                + "./gradlew \$@\n")
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)