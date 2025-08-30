package oi.github.pedroMartinsMJ.librayapi2.service;

import lombok.RequiredArgsConstructor;
import oi.github.pedroMartinsMJ.librayapi2.execeptions.BuscaSQLnaoEncontrado;
import oi.github.pedroMartinsMJ.librayapi2.execeptions.OperacaoNaoPermitida;
import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import oi.github.pedroMartinsMJ.librayapi2.repository.AutorRepository;
import oi.github.pedroMartinsMJ.librayapi2.repository.LivroRepository;
import oi.github.pedroMartinsMJ.librayapi2.security.SecurityService;
import oi.github.pedroMartinsMJ.librayapi2.validador.AutorValidator;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator validator;
    private final SecurityService securityService;
    private final LivroRepository livroRepository;

    public Autor salvar(Autor autor) {
        validator.validarCamposObrigatorios(autor);
        autor.setIdUsuario(securityService.obterUsuarioLogado().getId());
        validator.validar(autor);
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return autorRepository.findById(id);
    }

    public void deletarPorAutor(Autor autor){
        if (possuiLivro(autor)){
            throw new OperacaoNaoPermitida("Autor possui livros cadastrados");
        }
        autorRepository.delete(autor);
    }

    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("id null");
        }
        validator.validar(autor);
        autorRepository.save(autor);
    }

    public List<Autor> buscaNomeNacionalidade(String nome, String nacionalidade){
        if (nome != null && nacionalidade != null){
            List<Autor> autores = autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
            return verificarSeListaEstaVazia(autores);
        }
        if (nome != null) {
            List<Autor> autores = autorRepository.findByNome(nome);
            return verificarSeListaEstaVazia(autores);
        }
        if (nacionalidade != null) {
            List<Autor> autores = autorRepository.findByNacionalidade(nacionalidade);
            return verificarSeListaEstaVazia(autores);
        }

        // Retorna lista vazia por padrão
        return Collections.emptyList();
    }

    public List<Autor> buscaNomeNacionalidadeByExample(String nome, String nacionalidade){
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "dataNascimento", "dataCadastro", "dataAtualizacao")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);
        return autorRepository.findAll(autorExample);
    }

    private List<Autor> verificarSeListaEstaVazia(List<Autor> lista){
        if (lista.isEmpty()){
            throw new BuscaSQLnaoEncontrado("Nenhum autor encontrado com os critérios fornecidos, busca SQL");
        }
        return lista;
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }

}
