package oi.github.pedroMartinsMJ.librayapi2.repository;

import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import org.hibernate.boot.SchemaAutoTooling;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void salvarTest(){
        Autor newAutor = criarAutor("Pedro", "Brasileira", LocalDate.of(2004, 9, 21));

        Autor x = repository.save(newAutor);
        System.out.println("autor savo: " + x);
    }

    @Test
    public void atualizarAutorTest(){
        UUID id = UUID.fromString("a6b51cc0-1c44-484c-ac04-f5d7e05f553d");

        repository.findById(id).ifPresentOrElse(autorEncontrado -> {
            atualizarAutor(autorEncontrado, "Mateus", "Americano", LocalDate.of(2020, 12, 5));
            repository.save(autorEncontrado);
            System.out.println("Autor atualizado para " + autorEncontrado);
        }, () -> {
            System.out.println("Autor não encontrado [ ID UUID ]");
        });
    }

    @Test
    public void listaTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void deletarTest(){
        UUID id = UUID.fromString("44821199-372f-4b18-92e4-07d356789964");
        Optional<Autor> supostoAutor = repository.findById(id);

        if(supostoAutor.isPresent()){
            repository.deleteById(supostoAutor.get().getId());
            System.out.println("Autor "+ supostoAutor.get().getNome() + " deletado");
        }else{
            System.out.println("Autor não encontrado");
        }

    }

    @Test
    public void deletarTudoTest(){
        repository.deleteAll();
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
