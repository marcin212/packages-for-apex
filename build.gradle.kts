plugins {
  id("java")
  id("org.jetbrains.intellij") version "1.13.2"
}

group = "com.bymarcin"
version = "1.7.2"

repositories {
  mavenCentral()
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
  version.set("2022.3")
  type.set("IC") // Target IDE Platform

  plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "11"
    targetCompatibility = "11"
  }

  patchPluginXml {
    sinceBuild.set("212")
    untilBuild.set("223.*")
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
