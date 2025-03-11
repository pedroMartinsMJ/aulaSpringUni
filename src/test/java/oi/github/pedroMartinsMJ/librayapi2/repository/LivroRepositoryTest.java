package oi.github.pedroMartinsMJ.librayapi2.repository;

import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import oi.github.pedroMartinsMJ.librayapi2.model.GeneroLivro;
import oi.github.pedroMartinsMJ.librayapi2.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {
    @Autowired
    public AutorRepository autorRepository;
    @Autowired
    public LivroRepository livroRepository;

    @Test
    public void addLivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("1234-5678");
        livro.setTitulo("Heryporter");
        livro.setData_publicacao(LocalDate.of(2002,05,05));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setPreco(BigDecimal.valueOf(50.50));

        Autor autor = autorRepository
                .findById(UUID.fromString("a6b51cc0-1c44-484c-ac04-f5d7e05f553d"))
                .orElse(null);
        //livro.setAutor(autor);

        livroRepository.save(livro);
        System.out.println("livro salvo");
    }

    @Test
    public void addLivroCascadeTest(){

        Livro livro = new Livro(
                "1234-5678", "Heryporter",
                LocalDate.of(2002,05,05),
                GeneroLivro.FANTASIA , BigDecimal.valueOf(50.50),
                criarAutor("joao", "japao", LocalDate.of(2010, 9, 30))
        );

        livroRepository.save(livro);
        System.out.println("livro salvo");
    }

    @Test
    public void deletarLivro() {
        UUID id = UUID.fromString("");
        Livro livro = livroRepository.findById(id).orElse(null);

        if(livro != null){
            livroRepository.deleteById(livro.getId());
            System.out.println("livro " + livro.getTitulo() + " deletado");
        }else{
            System.out.println("[ERRO] Livro não existe");
        }
    }

    public void deletarAllLivro(){
        livroRepository.deleteAll();
    }

    @Test
    public void atualizarLivroAutor(){
        UUID id = UUID.fromString("");
        Livro livro = livroRepository.findById(id).orElse(null);
        Autor autor = autorRepository.findById(UUID.fromString("")).orElse(null);

        if(livro != null || autor != null) {

            atualizarAutor(autor,"joao atu", "angola", LocalDate.of(1990,8,06));
            livro.setAutor(autor);
            livroRepository.save(livro);
        }
    }

    @Test
    @Transactional
    public void buscarLivroTest() {
        Livro livro = livroRepository.findById(UUID
                .fromString("d495629c-ba0a-4e30-9429-868e44fa7863"))
                .orElse(null);

        System.out.println("livro: " + livro.getTitulo());
        System.out.println("livro: " + livro.getAutor().getNome());
    }

    @Test
    public void pesquisaPorTitulo() {
        List<Livro> listaLivro = livroRepository.findByTitulo("Senhor dos aneis");
        listaLivro.addAll(livroRepository.findByPreco(BigDecimal.valueOf(70.00)));

        listaLivro.forEach(System.out::println);
    }

    @Test
    public void listarLivrosPeloTituloPreco() {
        List<Livro> listaDeLivro = livroRepository.listarTodosOrdernadosPorTituloEPreco();

        listaDeLivro.forEach(System.out::println);
    }

    @Test
    public void listarAutoresDeTodosLivros() {
        List<Autor> listaDeAutores = livroRepository.listarAutoresDosLivros();
        listaDeAutores.forEach(System.out::println);
    }

    @Test
    public void listarPorGeneroQueryParamTest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("data_publicacao").ascending());
        List<Livro> generoLivro = livroRepository.buscarGeneroComParam(
                GeneroLivro.FANTASIA, pageable
        );

        List<Livro> generoLivro2 = livroRepository.buscarGeneroComParam2(
                GeneroLivro.FANTASIA, "data_publicacao"
        );

        generoLivro2.forEach(System.out::println);
        System.out.println("------------------------------");
        generoLivro.forEach(System.out::println);
    }

    @Test
    public void deletePorGeneroTest() {
        livroRepository.deleteByGenero(GeneroLivro.BIOGRAFIA);
    }

    @Test
    public void updateDatalivroGenero() {
        livroRepository.updateDataPubliccao(LocalDate.of(2025, 11, 3));
    }

    private Autor criarAutor(String  nome, String nacionalidade, LocalDate dataNascimento) {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        autor.setDataNascimento(dataNascimento);
        return autor;
    }

    private void atualizarAutor(Autor autor, String nome, String nacionalidade, LocalDate dataNascimento) {
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        autor.setDataNascimento(dataNascimento);
    }
}