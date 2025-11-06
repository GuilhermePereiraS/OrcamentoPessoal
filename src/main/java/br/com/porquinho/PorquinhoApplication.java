package br.com.porquinho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class PorquinhoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = SpringApplication.run(PorquinhoApplication.class, args);
		String porta = contexto.getEnvironment().getProperty("local.server.port");
		System.out.println("Iniciando servidor em http://localhost:" + porta +  "/");
	}

}
