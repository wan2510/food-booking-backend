plugins {
    java
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.flywaydb.flyway") version "9.22.3" 
    kotlin("plugin.spring") version "2.0.20"
    kotlin("jvm") version "2.0.20"

}

group = "com.app"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(23) 
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation") // Giữ lại
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    implementation("org.modelmapper:modelmapper:3.2.2")
    implementation("org.springframework.boot:spring-boot-starter-mail:3.4.3")
    implementation("io.jsonwebtoken:jjwt:0.12.6")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310") // Giữ lại
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation ("org.springframework.boot:spring-boot-starter-websocket")
}

tasks.withType<Test> {
    useJUnitPlatform()

}

