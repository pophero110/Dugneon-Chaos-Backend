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
		SpringApplication.run(DungeonChaosApplication.class, args);
	}

}
