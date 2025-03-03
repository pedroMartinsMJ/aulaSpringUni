package oi.github.pedroMartinsMJ.librayapi2.repository;

import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import org.hibernate.boot.SchemaAutoTooling;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void salvarTest(){
        Autor newAutor = new Autor();
        newAutor.setNome("Pedro");
        newAutor.setNacionalidade("Brasileira");
        newAutor.setDataNacimento(LocalDate.of(2004,9,21));

        Autor x = repository.save(newAutor);
        System.out.println("autor savo: " + x);
    }

    @Test
    public void atualizarAutorTest(){
        UUID id = UUID.fromString("cc9d99b8-687e-44de-9b4e-56f18f7e1749");

        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()){

            Autor autorEcontrado = possivelAutor.get();
            autorEcontrado.setNome("Mateus");
            autorEcontrado.setNacionalidade("Americano");
            autorEcontrado.setDataNacimento(LocalDate.of(2020,12,05));

            repository.save(autorEcontrado);
        }else {
            System.out.println("Autor não encontrado [ ID UUID ]");
        }


    }
}
