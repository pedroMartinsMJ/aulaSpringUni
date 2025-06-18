package oi.github.pedroMartinsMJ.librayapi2.service;

import oi.github.pedroMartinsMJ.librayapi2.execeptions.AutorBuscaSQLnaoEncontrado;
import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import oi.github.pedroMartinsMJ.librayapi2.repository.AutorRepository;
import oi.github.pedroMartinsMJ.librayapi2.validador.AutorValidator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator validator;

    private AutorService(AutorRepository autorRepository, AutorValidator validator){
        this.autorRepository = autorRepository;
        this.validator = validator;
    }

    public Autor salvar(Autor autor) {
        validator.validarCamposObrigatorios(autor);
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return autorRepository.findById(id);
    }

    public void deletarPorAutor(Autor autor){
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

    private List<Autor> verificarSeListaEstaVazia(List<Autor> lista){
        if (lista.isEmpty()){
            throw new AutorBuscaSQLnaoEncontrado("Nenhum autor encontrado com os critérios fornecidos, busca SQL");
        }
        return lista;
    }


}
