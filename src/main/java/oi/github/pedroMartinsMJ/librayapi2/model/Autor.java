package oi.github.pedroMartinsMJ.librayapi2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "autor", schema = "public")//  se o schema for public não é obrigado a botar
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

    //@OneToMany(mappedBy = "autor")
    @Transient
    private List<Livro> livros;

    @Deprecated
    public Autor(){
    }
}
