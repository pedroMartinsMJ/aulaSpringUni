package oi.github.pedroMartinsMJ.librayapi2.service;

import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import oi.github.pedroMartinsMJ.librayapi2.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    private AutorService(AutorRepository autorRepository){
        this.autorRepository = autorRepository;
    }

    public Autor salvar(Autor autor){
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
        autorRepository.save(autor);
    }

    public List<Autor> buscaNomeNacionalidade(String nome, String nacionalidade){
        if (nome != null && nacionalidade != null){
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }
        if (nome != null) {
            return autorRepository.findByNome(nome);
        }
        if (nacionalidade != null) {
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return null;
    }
}
