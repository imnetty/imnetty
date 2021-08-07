plugins {
    kotlin("jvm") version "1.5.21"
}

group = "org.launch"
version = "1.0"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("org.projectlombok:lombok:1.18.20")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("com.google.guava:guava:30.1-jre")
    implementation("com.google.inject:guice:5.0.1")
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("ch.qos.logback:logback-core:1.2.3")
    implementation("javax.inject:javax.inject:1")
    implementation("commons-io:commons-io:2.11.0")
    implementation("com.beust:klaxon:4.0.2")
}
