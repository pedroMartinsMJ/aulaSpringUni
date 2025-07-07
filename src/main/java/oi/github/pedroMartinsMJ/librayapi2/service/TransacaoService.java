package oi.github.pedroMartinsMJ.librayapi2.service;

import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import oi.github.pedroMartinsMJ.librayapi2.model.GeneroLivro;
import oi.github.pedroMartinsMJ.librayapi2.model.Livro;
import oi.github.pedroMartinsMJ.librayapi2.repository.AutorRepository;
import oi.github.pedroMartinsMJ.librayapi2.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void executar(){
        Livro livro = new Livro();
        livro.setIsbn("1234-5678");
        livro.setTitulo("Heryporter");
        livro.setDataPublicacao(LocalDate.of(2002,05,05));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setPreco(BigDecimal.valueOf(50.50));

        Autor autor = autorRepository
                .findById(UUID.fromString("26d625ba-51b8-42a9-88c9-6e23a458e1fe"))
                .orElse(null);
        livro.setAutor(autor);

        livroRepository.save(livro);
        System.out.println("livro salvo");
    }
}
