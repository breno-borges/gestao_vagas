package br.com.brenoborges.gestao_vagas.exceptions;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException() {
        super("Empresa não encontrada!");
    }
}
