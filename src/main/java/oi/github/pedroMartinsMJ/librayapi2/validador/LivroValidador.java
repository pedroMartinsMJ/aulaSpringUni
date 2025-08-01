package oi.github.pedroMartinsMJ.librayapi2.validador;

import lombok.RequiredArgsConstructor;
import oi.github.pedroMartinsMJ.librayapi2.execeptions.CampoInvalidoException;
import oi.github.pedroMartinsMJ.librayapi2.execeptions.RegistroDuplicadoException;
import oi.github.pedroMartinsMJ.librayapi2.model.Livro;
import oi.github.pedroMartinsMJ.librayapi2.repository.LivroRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidador {
    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository livroRepository;

    public void validar(Livro livro){
        if (existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("Livro dublicado, ISBN ja existente");
        }

        if (isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preco","preco deve de ser obrigaorio");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean existeLivroComIsbn(Livro livro){
        Optional<Livro> livroOptional = livroRepository.findByIsbn(livro.getIsbn());

        if (livro.getId() == null){
            return livroOptional.isPresent();
        }

        return  livroOptional
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));
    }
}
