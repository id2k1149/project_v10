package org.id2k1149.project_v10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProjectV10Application {
	public static void main(String[] args) {
		SpringApplication.run(ProjectV10Application.class, args);
	}

}