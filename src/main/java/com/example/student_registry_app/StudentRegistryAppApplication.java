package com.example.student_registry_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
Start gomb és http://localhost:8080/ a böngészőben. */
/*
TODO: ennek a hibának nézz utána:
2026-02-21T08:48:06.638Z  WARN 1 --- [student-registry-app] [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
*/
@SpringBootApplication
public class StudentRegistryAppApplication {
	// ! DTO-t megvalósítani.
	public static void main(String[] args) {
		SpringApplication.run(StudentRegistryAppApplication.class, args);
	}

}
