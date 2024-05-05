plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.fazecast:jSerialComm:[2.0.0,3.0.0)")
    implementation("com.mysql:mysql-connector-j:8.3.0")

    // Database
    implementation("org.springframework.data:spring-data-jpa:3.2.5")
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0-M2")
    implementation("org.hibernate.orm:hibernate-core:6.5.0.Final")
    implementation("org.slf4j:slf4j-nop:2.1.0-alpha1")
}

tasks.test {
    useJUnitPlatform()
}
