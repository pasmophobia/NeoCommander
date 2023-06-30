import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.dokka") version "1.6.10"
    id("com.vanniktech.maven.publish") version "0.18.0"
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://libraries.minecraft.net")
}

dependencies {
    compileOnly("com.mojang:brigadier:1.0.18")
    compileOnly("io.papermc.paper:paper-api:1.20-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot:1.20.1-R0.1-SNAPSHOT")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    create<Copy>("buildPlugin") {
        from(jar)
        into("server/plugins")
    }

    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    val javadocJar by creating(Jar::class) {
        archiveClassifier.set("javadoc")
        from(javadoc, dokkaJavadoc)
    }
    artifacts {
        archives(sourcesJar)
        archives(javadocJar)
    }
}