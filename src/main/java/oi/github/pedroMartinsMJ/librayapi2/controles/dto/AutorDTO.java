package oi.github.pedroMartinsMJ.librayapi2.controles.dto;

import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import java.time.LocalDate;

public record AutorDTO(
        String nome,
        LocalDate dataNascimento,
        String nacionalidade
) {
    public Autor mapearParaAutor(){
        Autor autor = new Autor(nome, nacionalidade, dataNascimento);
        return autor;
    }
}
