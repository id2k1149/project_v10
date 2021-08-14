package org.id2k1149.project_v10;

import org.id2k1149.project_v10.model.Role;
import org.id2k1149.project_v10.model.User;
import org.id2k1149.project_v10.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class ProjectV10Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjectV10Application.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null,"ROLE_USER"));
			userService.saveRole(new Role(null,"ROLE_ADMIN"));

			userService.saveUser(new User(
					null,
					"Brian",
					"admin1",
					"password",
					new ArrayList<>()));
			userService.saveUser(new User(
					null,
					"John",
					"user_1",
					"password",
					new ArrayList<>()));
			userService.saveUser(new User(
					null,
					"Paul",
					"user_2",
					"password",
					new ArrayList<>()));

			userService.addRoleToUser("admin1", "ROLE_ADMIN");
			userService.addRoleToUser("admin1", "ROLE_USER");
			userService.addRoleToUser("user_1", "ROLE_USER");
			userService.addRoleToUser("user_1", "ROLE_ADMIN");
			userService.addRoleToUser("user_2", "ROLE_USER");
		};
	}

	 */
}
