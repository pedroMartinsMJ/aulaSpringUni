package oi.github.pedroMartinsMJ.librayapi2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;


    /**
     * Commit -> confirma as alterações
     * Rollback -> desfazer as alterações
     */
    @Test
    @Transactional
    void transacaoSimples() {

    }


}
