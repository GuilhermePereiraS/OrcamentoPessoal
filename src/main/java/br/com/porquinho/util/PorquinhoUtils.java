package br.com.porquinho.util;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class PorquinhoUtils {

    public static boolean estaVazioOuNulo(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean temCaracterEspecial(String texto) {
        return texto.matches(".*[^a-zA-Z0-9].*");
    }

    public static void criaErro(RedirectAttributes redirectAttributes, String mensagemErro) {
        redirectAttributes.addFlashAttribute("alerta", true);
        redirectAttributes.addFlashAttribute("mensagemAlerta", mensagemErro);
        redirectAttributes.addFlashAttribute("iconeAlerta", "error");
    }


}
