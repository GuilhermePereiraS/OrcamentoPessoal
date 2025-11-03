package br.com.porquinho.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiteController {

    @GetMapping("/")
    public String index(Model model) {   
        if (model.containsAttribute("usuario")) {
            return "redirect:/admin/home";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        if (model.containsAttribute("usuario")) {
            return "redirect:/admin/home";
        } else {
            return "redirect:/login";
        }
    }

}