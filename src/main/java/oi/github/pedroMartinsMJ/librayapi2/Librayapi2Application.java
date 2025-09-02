package oi.github.pedroMartinsMJ.librayapi2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Librayapi2Application {

	public static void main(String[] args) {
		SpringApplication.run(Librayapi2Application.class, args);
	}
}
