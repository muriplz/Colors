plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.0"
}

group = "dev.muriplz"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://cursemaven.com")
}

dependencies {
    implementation("curse.maven:hyxin-1405491:7399430")
    compileOnly(files("libs/HytaleServer.jar"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}