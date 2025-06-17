package oi.github.pedroMartinsMJ.librayapi2.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "autor", schema = "public")//  se o schema for public não é obrigado a botar
@ToString(exclude = "livros")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "nacionalidade", length = 50 ,nullable = false)
    private String nacionalidade;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    //@Transient
    @OneToMany(mappedBy = "autor")
    private List<Livro> livros;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;

    public Autor(String nome, String nascionalidade, LocalDate dataNascimento) {
        this.nome = nome;
        this.nacionalidade = nascionalidade;
        this.dataNascimento = dataNascimento;
    }

    public Autor(String nome, String nascionalidade, LocalDate dataNascimento, List<Livro> livros) {
        this.nome = nome;
        this.nacionalidade = nascionalidade;
        this.dataNascimento = dataNascimento;
        this.livros = livros;
    }

    public UUID getId() {
        return this.id;
    }

    @Deprecated
    public Autor(){
    }
}
