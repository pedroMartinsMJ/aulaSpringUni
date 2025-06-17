package oi.github.pedroMartinsMJ.librayapi2.repository;

import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import oi.github.pedroMartinsMJ.librayapi2.model.GeneroLivro;
import oi.github.pedroMartinsMJ.librayapi2.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void addAutorTest(){
        Autor newAutor = criarAutor("Pedro", "Brasileira", LocalDate.of(2004, 9, 21));

        Autor x = autorRepository.save(newAutor);
        System.out.println("autor savo: " + x);
    }

    @Test
    public void atualizarAutorTest(){
        UUID id = UUID.fromString("a6b51cc0-1c44-484c-ac04-f5d7e05f553d");

        autorRepository.findById(id).ifPresentOrElse(autorEncontrado -> {
            atualizarAutor(autorEncontrado, "Mateus", "Americano", LocalDate.of(2020, 12, 5));
            autorRepository.save(autorEncontrado);
            System.out.println("Autor atualizado para " + autorEncontrado);
        }, () -> {
            System.out.println("Autor não encontrado [ ID UUID ]");
        });
    }

    @Test
    public void listaAutordTest(){
        List<Autor> lista = autorRepository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void deletarAutorTest(){
        UUID id = UUID.fromString("44821199-372f-4b18-92e4-07d356789964");
        Optional<Autor> supostoAutor = autorRepository.findById(id);

        if(supostoAutor.isPresent()){
            autorRepository.deleteById(supostoAutor.get().getId());
            System.out.println("Autor "+ supostoAutor.get().getNome() + " deletado");
        }else{
            System.out.println("Autor não encontrado");
        }
    }

    @Test
    public void salvarAutorComLivrosTest() {
        Autor autor = criarAutor("Antonio", "Americana", LocalDate.of(1970, 8, 5));

        Livro livro = new Livro( "4584-9999", "Senhor dos aneis"
                , LocalDate.of(1952,5,5)
                , GeneroLivro.FANTASIA, BigDecimal.valueOf(60.00), autor);

        Livro livro2 = new Livro( "4584-5454", "As duas torres"
                , LocalDate.of(1954,10,28)
                , GeneroLivro.FANTASIA, BigDecimal.valueOf(70.00), autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);
        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    //@Transactional
    public void listarLivrosAutor() {
        Autor autor = autorRepository.findById(
                UUID.fromString("5823371b-d7d8-4a1f-8d4f-1a7251edda9c")
        ).orElse(null);

        if (autor != null) {
            List<Livro> listaLivro = livroRepository.findByAutor(autor);
            listaLivro.forEach(System.out::println);
        }else {
            System.out.println("autor estar null, id nao encontrado");
        }
    }

    @Test
    public void deletarTudoTest(){
        autorRepository.deleteAll();
    }

    private Autor criarAutor(String  nome, String nacionalidade, LocalDate dataNascimento) {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        autor.setDataNascimento(dataNascimento);
        return autor;
    }
    private void atualizarAutor(Autor autor, String nome, String nacionalidade, LocalDate dataNascimento) {
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        autor.setDataNascimento(dataNascimento);
    }
}
