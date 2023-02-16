plugins {
    java
    jacoco
    id("io.quarkus") version "2.15.2.Final"
}

repositories {
    mavenCentral()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-arc")
    /*
    * The Data Binding concept is used to serialize and deserialize the data. It is used for converting JSON into POJO or vice-versa by using annotations or property accessors.
    * */
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.6")
    /*
    * RESTEasy is JBOSS-provided implementation of Jakarta-RS/JAX-RS specification for building RESTful Web Services.
    * Jackson is is a multi-purpose Java library for processing JSON data format.
    */
    implementation("io.quarkus:quarkus-resteasy-jackson")
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-rest-client-jackson")
    implementation("io.quarkus:quarkus-smallrye-fault-tolerance")
    implementation("io.quarkus:quarkus-quartz")
    implementation("io.quarkus:quarkus-cache")

    // Hibernate and Flyway specific dependencies
    implementation("io.quarkus:quarkus-hibernate-orm")
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkus:quarkus-flyway")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    //?
    implementation( "org.assertj:assertj-core:3.11.1")

    //test dependencies
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.quarkus:quarkus-junit5-mockito")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("com.tngtech.archunit:archunit-junit5:1.0.0-rc1") // for testing architecture
}

group = "com.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}