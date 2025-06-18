package oi.github.pedroMartinsMJ.librayapi2.validador;

import oi.github.pedroMartinsMJ.librayapi2.execeptions.CamposPostMalInseridos;
import oi.github.pedroMartinsMJ.librayapi2.execeptions.RegistroDuplicadoException;
import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import oi.github.pedroMartinsMJ.librayapi2.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository){
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor) {
        if (existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }
    }

    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorEncontrado = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
        );

        // Se não encontrou nada, não é duplicado
        if (autorEncontrado.isEmpty()) {
            return false;
        }

        // Se é novo (sem ID), mas achou alguém igual, é duplicado
        if (autor.getId() == null) {
            return true;
        }

        // Se o ID encontrado for diferente, então é outro autor igual (duplicado)
        return !autor.getId().equals(autorEncontrado.get().getId());
    }

    public void validarCamposObrigatorios(Autor autor) {
        if (autor == null) {
            throw new CamposPostMalInseridos("Autor não pode ser nulo.");
        }

        if (autor.getNome() == null || autor.getNome().isBlank()) {
            throw new CamposPostMalInseridos("O campo 'nome' do autor é obrigatório.");
        }

        if (autor.getDataNascimento() == null) {
            throw new CamposPostMalInseridos("O campo 'dataNascimento' do autor é obrigatório.");
        }

        if (autor.getNacionalidade() == null || autor.getNacionalidade().isBlank()) {
            throw new CamposPostMalInseridos("A nacionalidade do autor é obrigatória.");}
    }

}
