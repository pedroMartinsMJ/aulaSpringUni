package oi.github.pedroMartinsMJ.librayapi2.service;


import lombok.RequiredArgsConstructor;
import oi.github.pedroMartinsMJ.librayapi2.execeptions.BuscaSQLnaoEncontrado;
import oi.github.pedroMartinsMJ.librayapi2.model.GeneroLivro;
import oi.github.pedroMartinsMJ.librayapi2.model.Livro;
import oi.github.pedroMartinsMJ.librayapi2.repository.LivroRepository;
import oi.github.pedroMartinsMJ.librayapi2.repository.specs.LivroSpecs;
import oi.github.pedroMartinsMJ.librayapi2.security.SecurityService;
import oi.github.pedroMartinsMJ.librayapi2.validador.LivroValidador;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository livroRepository;
    private final SecurityService securityService;
    private final LivroValidador livroValidador;

   public Livro salvar(Livro livro){
       livroValidador.validar(livro);
       livro.setIdUsuario(securityService.obterUsuarioLogado().getId());
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

    public void atulaizarLivro(UUID id, Livro livroAtualizado){
       Optional<Livro> livroOptional = livroRepository.findById(id);

       if (livroOptional.isEmpty()){
           throw new BuscaSQLnaoEncontrado("livro a ser atualizado não existe");
       }
       Livro livro = livroOptional.get();
       livro.setTitulo(livroAtualizado.getTitulo());
       livro.setIsbn(livroAtualizado.getIsbn());
       livro.setDataPublicacao(livroAtualizado.getDataPublicacao());
       livro.setGenero(livroAtualizado.getGenero());
       livro.setPreco(livroAtualizado.getPreco());
       livro.setAutor(livroAtualizado.getAutor());
       livroValidador.validar(livro);
       livroRepository.save(livro);
    }

    public Page<Livro> pesquisa(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao, Pageable pageable) {
        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (isbn != null) {
            specs = specs.and(LivroSpecs.isbnEqual(isbn));
        }
        if (titulo != null) {
            specs = specs.and(LivroSpecs.tituloLike(titulo));
        }
        if (genero != null) {
            specs = specs.and(LivroSpecs.generoEqual(genero));
        }
        if (anoPublicacao != null) {
            specs = specs.and(LivroSpecs.anoPublicacaoEqual(anoPublicacao));
        }
        if (nomeAutor != null) {
            specs = specs.and(LivroSpecs.nomeAutorLike(nomeAutor));
        }

        return livroRepository.findAll(specs, pageable);
    }
}
