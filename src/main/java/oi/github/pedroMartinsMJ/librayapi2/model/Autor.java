package oi.github.pedroMartinsMJ.librayapi2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "autor", schema = "public")// se o schema for public não é obrigado a botar
public class Autor {

    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNacimento;

    @Column(name = "nacionalidade", length = 50 ,nullable = false)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor")
    private List<Livro> livros;

    @Deprecated
    public Autor(){
    }

}
