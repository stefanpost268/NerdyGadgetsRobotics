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
    implementation("org.xhtmlrenderer:flying-saucer-pdf:9.7.2")
    implementation("com.fazecast:jSerialComm:[2.0.0,3.0.0)")
    implementation("org.slf4j:slf4j-nop:2.1.0-alpha1")
    implementation("org.json:json:20210307")

    // Database
    implementation("org.springframework.data:spring-data-jpa:3.2.5")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.3.3")
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0-M2")
    implementation("org.hibernate.orm:hibernate-core:6.5.0.Final")
    implementation("org.slf4j:slf4j-nop:2.1.0-alpha1")
    implementation("org.xhtmlrenderer:flying-saucer-pdf:9.7.2")

}

tasks.test {
    useJUnitPlatform()
}
