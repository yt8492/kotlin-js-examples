import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin.Companion.kotlinNodeJsExtension
import org.jetbrains.kotlin.gradle.targets.js.yarn.yarn

plugins {
    kotlin("multiplatform") version "2.0.20"
    id("app.cash.sqldelight") version "2.0.2"
}

group = "com.yt8492"
version = "1.0-SNAPSHOT"

kotlin {
    js {
        useCommonJs()
        nodejs()
        binaries.executable()
        compilerOptions {
            target.set("es2015")
        }
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("cz.sazel.sqldelight:node-sqlite3-driver-js:0.3.2")
                implementation(npm("express", "4.19.2"))
                implementation(npm("cors", "2.8.5"))
            }
        }
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.yt8492.express.db")
            generateAsync.set(true)
        }
    }
}

val bindingsInstall = tasks.register("sqlite3BindingsInstall") {
    doLast {
        val sqlite3moduleDir = buildDir.resolve("js/node_modules/sqlite3")
        if (!sqlite3moduleDir.resolve("lib/binding").exists()) {
            exec {
                workingDir = sqlite3moduleDir
                val yarnPath="${yarn.yarnSetupTaskProvider.get().destination.absolutePath}/bin"
                val nodePath="${kotlinNodeJsExtension.nodeJsSetupTaskProvider.get().destination.absolutePath}/bin"
                environment(
                    "PATH",
                    System.getenv("PATH") + ":$yarnPath:$nodePath"
                )
                val commandLine = "$yarnPath/yarn"
                commandLine(commandLine)
            }
        }
    }
}.get()
tasks["kotlinNpmInstall"].finalizedBy(bindingsInstall)

repositories {
    mavenCentral()
}
