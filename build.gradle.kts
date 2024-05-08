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
//    implementation("com.fazecast:jSerialComm:2.0.0,3.0.0")
    implementation("com.mysql:mysql-connector-j:8.3.0")
    implementation("org.xhtmlrenderer:flying-saucer-pdf:9.7.2")
    implementation("com.mysql:mysql-connector-j:8.3.0")
}

tasks.test {
    useJUnitPlatform()
}