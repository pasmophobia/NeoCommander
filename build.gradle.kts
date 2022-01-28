import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    `maven-publish`
}

group = "net.propromp"
version = "1.1"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://libraries.minecraft.net")
}
dependencies {
    compileOnly("com.mojang:brigadier:1.0.18")
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.create<Copy>("buildPlugin") {
    from(tasks.shadowJar)
    into("server/plugins")
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            groupId = "net.propromp"
            artifactId = "neocommander"
            version = version

            from(components["java"])
        }
    }
}