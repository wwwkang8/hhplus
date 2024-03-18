plugins {
	java
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
	id("jacoco")
}

allprojects {
	group = property("app.group").toString()
}

dependencyManagement {
	imports {
		mavenBom(libs.spring.cloud.dependencies.get().toString())
	}
}

group = "io.hhplus"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

with(extensions.getByType(JacocoPluginExtension::class.java)) {
	toolVersion = "0.8.7"
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencies {
	implementation(libs.spring.boot.starter.web)
	annotationProcessor(libs.spring.boot.configuration.processor)
	testImplementation(libs.spring.boot.starter.test)

	compileOnly(libs.lombok)
	annotationProcessor(libs.lombok)
}

// bundling tasks
tasks.getByName("bootJar") {
	enabled = true
}
tasks.getByName("jar") {
	enabled = false
}
// test tasks
tasks.test {
	ignoreFailures = true
	useJUnitPlatform()
}
