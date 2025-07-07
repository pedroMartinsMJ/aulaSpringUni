package oi.github.pedroMartinsMJ.librayapi2.config;

import lombok.RequiredArgsConstructor;
import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import oi.github.pedroMartinsMJ.librayapi2.model.GeneroLivro;
import oi.github.pedroMartinsMJ.librayapi2.model.Livro;
import oi.github.pedroMartinsMJ.librayapi2.repository.AutorRepository;
import oi.github.pedroMartinsMJ.librayapi2.repository.LivroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("test")
public class inicializacao implements CommandLineRunner {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    @Override
    public void run(String... args) throws Exception {

        // Criando autores
        Autor autor1 = new Autor("Maria", "Brasil", LocalDate.of(2000, 5, 5));
        Autor autor2 = new Autor("Pedro", "Espanha", LocalDate.of(2010, 12, 23));
        Autor autor3 = new Autor("Rafaela", "Japão", LocalDate.of(1955, 7, 10));
        Autor autor4 = new Autor("João", "Portugal", LocalDate.of(1988, 3, 1));
        Autor autor5 = new Autor("Ana", "França", LocalDate.of(1975, 9, 14));
        Autor autor6 = new Autor("Carlos", "México", LocalDate.of(1993, 6, 30));
        Autor autor7 = new Autor("Juliana", "Itália", LocalDate.of(1966, 11, 21));
        Autor autor8 = new Autor("Fernanda", "Alemanha", LocalDate.of(1983, 2, 8));
        Autor autor9 = new Autor("Luiz", "Canadá", LocalDate.of(1990, 1, 19));
        Autor autor10 = new Autor("Tatiane", "Austrália", LocalDate.of(1970, 4, 25));

        autorRepository.saveAll(Arrays.asList(
                autor1, autor2, autor3, autor4, autor5,
                autor6, autor7, autor8, autor9, autor10
        ));

// Criando livros
        List<Livro> livros = List.of(
                new Livro("978-0-345-33970-6", "Messias de Duna", LocalDate.of(1969, 10, 15), GeneroLivro.FICCAO, new BigDecimal("49.99"), autor1),
                new Livro("978-0-261-10230-1", "O Hobbit", LocalDate.of(1937, 9, 21), GeneroLivro.FANTASIA, new BigDecimal("39.90"), autor2),
                new Livro("978-0-7432-7356-5", "Anjos e Demônios", LocalDate.of(2000, 5, 1), GeneroLivro.SUSPENSE, new BigDecimal("45.00"), autor3),
                new Livro("978-0-141-03435-8", "1984", LocalDate.of(1949, 6, 8), GeneroLivro.FICCAO, new BigDecimal("29.99"), autor4),
                new Livro("978-0-307-59291-3", "A Garota no Trem", LocalDate.of(2015, 1, 13), GeneroLivro.THRILLER, new BigDecimal("42.75"), autor5),
                new Livro("978-0-06-112008-4", "O Sol é para Todos", LocalDate.of(1960, 7, 11), GeneroLivro.DRAMA, new BigDecimal("38.50"), autor6),
                new Livro("978-0-7432-7355-8", "O Código Da Vinci", LocalDate.of(2003, 3, 18), GeneroLivro.SUSPENSE, new BigDecimal("48.90"), autor7),
                new Livro("978-0-7432-7357-2", "Inferno", LocalDate.of(2013, 5, 14), GeneroLivro.SUSPENSE, new BigDecimal("50.00"), autor8),
                new Livro("978-0-307-74444-1", "A Culpa é das Estrelas", LocalDate.of(2012, 1, 10), GeneroLivro.ROMANCE, new BigDecimal("35.00"), autor9),
                new Livro("978-0-553-21311-7", "O Apanhador no Campo de Centeio", LocalDate.of(1951, 7, 16), GeneroLivro.DRAMA, new BigDecimal("31.50"), autor10)
        );

        livroRepository.saveAll(livros);

    }
}
