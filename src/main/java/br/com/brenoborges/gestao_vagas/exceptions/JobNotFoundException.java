package br.com.brenoborges.gestao_vagas.exceptions;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException() {
        super("Vaga não encontrada!");
    }
}
