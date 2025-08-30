package oi.github.pedroMartinsMJ.librayapi2.execeptions;


public class OperacaoNaoPermitida extends RuntimeException {
    public OperacaoNaoPermitida(String message) {
        super(message);
    }
}
