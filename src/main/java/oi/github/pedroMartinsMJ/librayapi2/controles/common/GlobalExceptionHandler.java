package oi.github.pedroMartinsMJ.librayapi2.controles.common;

import oi.github.pedroMartinsMJ.librayapi2.controles.dto.ErroCampo;
import oi.github.pedroMartinsMJ.librayapi2.controles.dto.ErroResposta;
import oi.github.pedroMartinsMJ.librayapi2.execeptions.BuscaSQLnaoEncontrado;
import oi.github.pedroMartinsMJ.librayapi2.execeptions.CamposPostMalInseridos;
import oi.github.pedroMartinsMJ.librayapi2.execeptions.OperacaoNaoPermitida;
import oi.github.pedroMartinsMJ.librayapi2.execeptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "erro de validação",
                listaErros);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta RegistroDuplicadoException(RegistroDuplicadoException e) {
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitida.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta operacaoNaoPermitida(OperacaoNaoPermitida e) {
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(BuscaSQLnaoEncontrado.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta autorBuscaSQLnaoEncontrado(BuscaSQLnaoEncontrado e) {
        return ErroResposta.conflito(e.getMessage());
    }


    @ExceptionHandler(CamposPostMalInseridos.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta camposPostMalInseridos(CamposPostMalInseridos e) {
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErrosNaoTratados(RuntimeException e){
        e.printStackTrace();
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro inesperado, entre em contato com a administração",
                List.of());
    }
}
