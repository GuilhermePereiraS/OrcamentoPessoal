package br.com.porquinho.service;

public class LoginJaExistenteException extends RuntimeException {
    public LoginJaExistenteException(String message) {
        super(message);
    }
}
