rootProject.name = "terracotta"

pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.ornithemc.net/releases/")
        maven("https://babric.github.io/m2/")
        maven("https://maven.quiltmc.org/repository/release/")
        maven("https://maven.parchmentmc.org")
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(":terracotta-core")
