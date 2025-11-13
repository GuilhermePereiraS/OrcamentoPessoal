package br.com.porquinho.util;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class PorquinhoUtils {

    public static boolean estaVazioOuNulo(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean temCaracterEspecial(String texto) {
        return texto.matches(".*[^a-zA-Z0-9].*");
    }

    public static void criaMensagemDeErro(RedirectAttributes redirectAttributes, String mensagem) {
        redirectAttributes.addFlashAttribute("tituloAlerta", "Erro!");
        redirectAttributes.addFlashAttribute("alerta", true);
        redirectAttributes.addFlashAttribute("mensagemAlerta", mensagem);
        redirectAttributes.addFlashAttribute("iconeAlerta", "error");
    }

    public static void criaMensagemDeAlerta(RedirectAttributes redirectAttributes, String mensagem) {
        redirectAttributes.addFlashAttribute("tituloAlerta", "Alerta!");
        redirectAttributes.addFlashAttribute("alerta", true);
        redirectAttributes.addFlashAttribute("mensagemAlerta", mensagem);
        redirectAttributes.addFlashAttribute("iconeAlerta", "warning");
    }

    public static void criaMensagemSucesso(RedirectAttributes redirectAttributes, String mensagem) {
        redirectAttributes.addFlashAttribute("tituloAlerta", "Sucesso!");
        redirectAttributes.addFlashAttribute("alerta", true);
        redirectAttributes.addFlashAttribute("mensagemAlerta", mensagem);
        redirectAttributes.addFlashAttribute("iconeAlerta", "success");
    }


}
