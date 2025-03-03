package oi.github.pedroMartinsMJ.librayapi2;

import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import oi.github.pedroMartinsMJ.librayapi2.model.Livro;
import oi.github.pedroMartinsMJ.librayapi2.repository.AutorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class Librayapi2Application {

	public static void main(String[] args) {
		SpringApplication.run(Librayapi2Application.class, args);
	}
}
