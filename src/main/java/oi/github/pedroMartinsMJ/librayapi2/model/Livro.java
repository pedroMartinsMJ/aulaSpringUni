package oi.github.pedroMartinsMJ.librayapi2.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;

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
