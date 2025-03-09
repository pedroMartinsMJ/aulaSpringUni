package oi.github.pedroMartinsMJ.librayapi2.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "livro")
@ToString(exclude = "autor")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate data_publicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 10, scale = 2)
    private BigDecimal preco;  //opição alternativa
    //private Double preco;

    @ManyToOne(
            //cascade = CascadeType.ALL
            fetch = FetchType.LAZY//so carrega dentro de uma @Transactional
    )
    @JoinColumn(name = "id_autor")
    private Autor autor;

    public Livro(String isbn, String titulo, LocalDate data_publicacao, GeneroLivro genero, BigDecimal preco, Autor autor) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.data_publicacao = data_publicacao;
        this.genero = genero;
        this.preco = preco;
        this.autor = autor;
    }


    public Livro() {
        //throw new UnsupportedOperationException("Use o construtor parametrizado.");
    }
}
