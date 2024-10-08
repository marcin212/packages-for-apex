import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.models.ProductRelease

plugins {
  id("java")
  id("org.jetbrains.intellij.platform") version "2.1.0"
}

group = "com.bymarcin"
version = "1.7.4"

repositories {
  mavenCentral()
  intellijPlatform {
    defaultRepositories()
  }
}

// Configure Gradle IntelliJ Plugin - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
  intellijPlatform {
    create("IC", "2024.2.3")
    instrumentationTools()
    pluginVerifier()
  }
}

intellijPlatform {
  pluginVerification {
    ides {
      ide(IntelliJPlatformType.IntellijIdeaCommunity, "2024.2.3")
      recommended()
      select {
        types = listOf(IntelliJPlatformType.IntellijIdeaCommunity)
        channels = listOf(ProductRelease.Channel.RELEASE)
        sinceBuild = "232"
        untilBuild = "242.*"
      }
    }
  }
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "17"
    targetCompatibility = "17"
  }

  patchPluginXml {
    sinceBuild.set("212")
    untilBuild.set("242.*")
    changeNotes.set("""
        <ul>
            <li>Update to new version of idea</li>
        </ul>
    """)
  }

  signPlugin {
    //certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
    //privateKey.set(System.getenv("PRIVATE_KEY"))
    //password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
  }

  publishPlugin {
    token.set(System.getenv("PUBLISH_TOKEN"))
  }
}
