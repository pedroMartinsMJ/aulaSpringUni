package oi.github.pedroMartinsMJ.librayapi2.service;

import lombok.RequiredArgsConstructor;
import oi.github.pedroMartinsMJ.librayapi2.execeptions.BuscaSQLnaoEncontrado;
import oi.github.pedroMartinsMJ.librayapi2.model.GeneroLivro;
import oi.github.pedroMartinsMJ.librayapi2.model.Livro;
import oi.github.pedroMartinsMJ.librayapi2.repository.LivroRepository;
import oi.github.pedroMartinsMJ.librayapi2.repository.specs.LivroSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository livroRepository;

   public Livro salvar(Livro livro){
       return livroRepository.save(livro);
   }

   public Livro buscarLivroPorID(UUID id){
       Optional<Livro> supostoLivro = livroRepository.findById(id);
       if(supostoLivro.isEmpty()){
           throw new BuscaSQLnaoEncontrado("livro não encontrado, verifique o id");
       }
       return supostoLivro.get();
   }

    public void deletarLivroPorId(UUID id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new BuscaSQLnaoEncontrado("Livro não encontrado, verifique o id"));
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisa(String isbn,String titulo, String nomeAutor, GeneroLivro genero, Integer anoPubliccacao) {

        Specification<Livro> specs = Specification
                .where((root, query, cb) -> cb.conjunction());

        if (isbn != null){
            specs = specs.and(LivroSpecs.isbnEqual(isbn));
        }
        if (titulo != null){
            specs = specs.and(LivroSpecs.tituloLike(titulo));
        }
        if (genero != null){
            specs = specs.and(LivroSpecs.generoEqual(genero));
        }

        return livroRepository.findAll(LivroSpecs.isbnEqual(isbn));
    }
}
