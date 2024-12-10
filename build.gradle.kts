import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    application
}

group = "org.ruanf"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.28.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.1.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("org.ruanf.MainKt")
}

tasks.withType<Jar>() {
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    manifest {
        attributes["Main-Class"] = "org.ruanf.MainKt"
    }
}