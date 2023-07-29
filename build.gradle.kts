plugins {
    id("java")
}

group = "com.github.hyeyoom-lecture"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.reflections:reflections:0.10.2")

    implementation("org.eclipse.jetty:jetty-server:11.0.15")
    implementation("org.eclipse.jetty:jetty-servlet:11.0.15")
}

tasks.test {
    useJUnitPlatform()
}