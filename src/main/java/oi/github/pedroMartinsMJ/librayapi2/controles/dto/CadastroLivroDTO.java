package oi.github.pedroMartinsMJ.librayapi2.controles.dto;

import jakarta.validation.constraints.*;
import oi.github.pedroMartinsMJ.librayapi2.execeptions.CamposPostMalInseridos;
import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import oi.github.pedroMartinsMJ.librayapi2.model.GeneroLivro;
import oi.github.pedroMartinsMJ.librayapi2.model.Livro;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @NotBlank(message = "campo obrigatorio")
        @ISBN
        String isbn,
        @NotBlank(message = "campo obrigatorio")
        @Size(min = 2, max = 100, message = "campo fora do padrão")
        String titulo,
        @NotNull(message = "campo obrigatorio")
        @Past(message = "não pode ser um data futura")
        LocalDate dataPublicacao,
        @NotNull(message = "campo obrigatorio")
        GeneroLivro genero,
        @NotNull(message = "campo obrigatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "preço deve ser maior que zero")
        BigDecimal preco,
        @NotNull(message = "campo obrigatorio")
        UUID idAutor
) {
}
