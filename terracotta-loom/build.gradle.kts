plugins {
    id("terracotta.convention.kotlin-jvm")
}

repositories {
    maven(url = "https://maven.fabricmc.net/") {
		name = "Fabric"
	}
}

dependencies {
	api(project(":terracotta-core"))
	implementation(libs.fabric.loom.gradlePlugin)
}
