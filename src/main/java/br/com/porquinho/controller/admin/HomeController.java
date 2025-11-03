package br.com.porquinho.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/admin/home")
    public String dashboard() {
        return "pages/admin/home";
    }
}
