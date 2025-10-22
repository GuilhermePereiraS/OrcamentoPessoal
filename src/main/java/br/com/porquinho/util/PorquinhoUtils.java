package br.com.porquinho.util;

public class PorquinhoUtils {

    public static boolean estaVazioOuNulo(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean temCaracterEspecial(String texto) {
        return texto.matches(".*[^a-zA-Z0-9].*");
    }


}
