package oi.github.pedroMartinsMJ.librayapi2.model;


import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Table(name = "usuario")
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "login")
    private String login;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @Type(ListArrayType.class)
    @Column(name = "roles", columnDefinition = "varchar[]")
    private List<String> roles = new ArrayList<>();


    public Usuario(){
        //ola
    }
    public Usuario(String login, String senha, String[] roles){
        this.login = login;
        this.senha = senha;
        this.roles.addAll(Arrays.stream(roles).toList());
    }
    public Usuario(String login, String senha, String roles){
        this.login = login;
        this.senha = senha;
        this.roles.add(roles);
    }
}
