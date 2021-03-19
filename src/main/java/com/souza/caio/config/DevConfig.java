package com.souza.caio.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.souza.caio.services.DBService;

@Configuration
@Profile("dev") //Todos os beans da classe serão ativados somente quando o profile for 'dev'
public class DevConfig {

	
	@Autowired // TUTORIAL: Instanciada automaticamente
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		if(!strategy.equals("create")) {
			return false;
		}
		
		dbService.instantiateTestDatabase();
		return true;
	}
}
