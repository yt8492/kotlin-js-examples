plugins {
  kotlin("multiplatform") version "2.0.0"
}

group = "com.yt8492"
version = "1.0-SNAPSHOT"

kotlin {
  js {
    browser {
      commonWebpackConfig {
        outputFileName = "main.js"
      }
    }
    nodejs()
    binaries.executable()
  }
  sourceSets {
    val jsMain by getting {
      dependencies {
        implementation(kotlin("stdlib-js"))
      }
    }
  }
}

repositories {
  mavenCentral()
}
