import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.models.ProductRelease

plugins {
  id("java")
  id("org.jetbrains.intellij.platform") version "2.7.2"
}

group = "com.bymarcin"
version = "1.7.5"

repositories {
  mavenCentral()
  intellijPlatform {
    defaultRepositories()
  }
}

// Configure Gradle IntelliJ Plugin - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
  intellijPlatform {
    create("IC", "2025.2")
    pluginVerifier()
  }
}

intellijPlatform {
  pluginVerification {
    ides {
      ide(IntelliJPlatformType.IntellijIdeaCommunity, "2025.2")
      recommended()
      select {
        types = listOf(IntelliJPlatformType.IntellijIdeaCommunity)
        channels = listOf(ProductRelease.Channel.RELEASE)
        sinceBuild = "241"
        untilBuild = "252.*"
      }
    }
  }
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "21"
    targetCompatibility = "21"
  }

  patchPluginXml {
    sinceBuild.set("241")
    untilBuild.set("252.*")
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
