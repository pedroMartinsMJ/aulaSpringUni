package oi.github.pedroMartinsMJ.librayapi2.controles.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record AutorDTO(
        @NotBlank(message = "campo obrigatorio")
        @Size(min = 2, max = 100, message = "campo fora do padrão")
        String nome,
        @NotNull(message = "campo obrigatorio")
        @Past(message = "não pode ser um data futura")
        LocalDate dataNascimento,
        @NotBlank(message = "campo obrigatorio")
        @Size(min = 2, max = 50, message = "campo fora do padrão")
        String nacionalidade
) {

}
