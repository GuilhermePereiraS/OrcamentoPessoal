package br.com.porquinho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class PorquinhoApplication {

	public static void main(String[] args) {
		System.out.println("Iniciando servidor em http://localhost:8080/");
		SpringApplication.run(PorquinhoApplication.class, args);
	}

}
