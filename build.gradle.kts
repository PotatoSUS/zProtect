import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.serialization") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "dev.zprotect"
version = "0.2"

repositories {
    mavenCentral()
    maven {
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

dependencies {
    implementation("org.json:json:20211205")

    implementation("org.ow2.asm:asm:9.2")
    implementation("org.ow2.asm:asm-tree:9.2")
    implementation("org.ow2.asm:asm-commons:9.2")
    implementation("org.ow2.asm:asm-util:9.2")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    register<Jar>("standaloneJar") {
        manifest {
            attributes["Main-Class"] = "dev.zprotect.obfuscator.StandaloneKt"
        }
    }

    named<ShadowJar>("shadowJar") {
        manifest {
            attributes["Main-Class"] = "dev.zprotect.obfuscator.MainKt"
        }
        minimize()
    }

    withType<Jar> {
        manifest {
            attributes["Main-Class"] = "dev.zprotect.obfuscator.MainKt"
        }
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}
