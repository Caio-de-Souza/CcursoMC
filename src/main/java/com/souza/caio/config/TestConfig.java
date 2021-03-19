package com.souza.caio.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.souza.caio.services.DBService;
import com.souza.caio.services.EmailService;
import com.souza.caio.services.MockEmailService;

@Configuration
@Profile("test") //Todos os beans da classe serão ativados somente quando o profile for 'test'
public class TestConfig {

	
	@Autowired // TUTORIAL: Instanciada automaticamente
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
