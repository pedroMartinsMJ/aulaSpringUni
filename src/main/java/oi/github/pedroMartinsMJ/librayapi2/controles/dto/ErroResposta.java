package oi.github.pedroMartinsMJ.librayapi2.controles.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroResposta(int status, String mensagem, List<ErroCampo> erros) {

    public static ErroResposta respostaPadao(String mensagem){
        return new ErroResposta(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static  ErroResposta conflito(String mensagem){
        return  new ErroResposta(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }
    
}
