package oi.github.pedroMartinsMJ.librayapi2.repository;

import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import oi.github.pedroMartinsMJ.librayapi2.model.GeneroLivro;
import oi.github.pedroMartinsMJ.librayapi2.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {
    @Autowired
    public AutorRepository autorRepository;
    @Autowired
    public LivroRepository livroRepository;

    @Test
    public void addLivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("1234-5678");
        livro.setTitulo("Heryporter");
        livro.setData_publicacao(LocalDate.of(2002,05,05));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setPreco(BigDecimal.valueOf(50.50));

        Autor autor = autorRepository
                .findById(UUID.fromString("a6b51cc0-1c44-484c-ac04-f5d7e05f553d"))
                .orElse(null);
        livro.setAutor(autor);

        livroRepository.save(livro);
        System.out.println("livro salvo");
    }
}