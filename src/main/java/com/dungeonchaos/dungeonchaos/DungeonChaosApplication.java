package com.dungeonchaos.dungeonchaos;

import com.dungeonchaos.dungeonchaos.config.CorsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@Import(CorsConfig.class)
public class DungeonChaosApplication {

	public static void main(String[] args) {


		// Set the default profile as 'dev' to be used if the 'SPRING_PROFILES_ACTIVE' environment variable is not set
		System.setProperty("spring.profiles.default", "dev");

		SpringApplication.run(DungeonChaosApplication.class, args);
	}

}
