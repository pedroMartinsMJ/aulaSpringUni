package oi.github.pedroMartinsMJ.librayapi2.repository;

import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import oi.github.pedroMartinsMJ.librayapi2.model.GeneroLivro;
import oi.github.pedroMartinsMJ.librayapi2.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    Page<Livro> findByAutor(Autor autor, Pageable pageable);

    // Query Method
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    List<Livro> findByPreco(BigDecimal preco);

    Optional<Livro> findByIsbn(String isbn);

    //select * from livro where data_publicacao between ? and ?
    @Query("SELECT l FROM Livro l WHERE l.dataPublicacao BETWEEN :inicio AND :fim")
    List<Livro> findByDataPublicacaoBetween(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);


    // JPQL -> referencia as entidades e as propiedades
    // select l.* from livro as l order by l.titulo, l.preco
    @Query(" select l from Livro as l order by l.titulo, l.preco ")
    List<Livro> listarTodosOrdernadosPorTituloEPreco();

    //select autor.* from livro join autor on autor.id = livro.id_autor
    @Query(" select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    @Query("""
        select l.genero from Livro l
        join l.autor a where a.nacionalidade = 'Brasileira'
        order by l.genero
    """)
    List<String> listarGenerosAutoresBrasileiros();

    //usando parametros na query
    @Query("select l from Livro l where l.genero = :nomeDoParamentro")
    List<Livro> buscarGeneroComParam(
            @Param("nomeDoParamentro") GeneroLivro generoLivro,
            Pageable pageable
    );

    @Query(" select l from Livro l where l.genero = :genero order by :nomeDoParamentro ")
    List<Livro> buscarGeneroComParam2(
            @Param("genero") GeneroLivro generoLivro,
            @Param("nomeDoParamentro") String nomeParamentro
    );

    // positional parameters
    @Query(" select l from Livro l where l.genero = ?1 order by ?2 ")
    List<Livro> buscarGeneroComParamPosi(GeneroLivro generoLivro, String nomeParamentro);

    @Transactional
    @Modifying
    @Query(" delete from Livro where genero = ?1 ")
    void deleteByGenero(GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query(" update Livro set dataPublicacao = ?1 where genero = 'BIOGRAFIA' ")
    void updateDataPubliccao(LocalDate localDate);

    boolean existsByAutor(Autor autor);
}
