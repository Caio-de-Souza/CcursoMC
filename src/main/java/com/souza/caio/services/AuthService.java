package com.souza.caio.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.souza.caio.domain.Cliente;
import com.souza.caio.repositories.ClienteRepository;
import com.souza.caio.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptEncoder;
	
	@Autowired
	private EmailService emailSevice;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if(cliente == null) {
			throw new ObjectNotFoundException("E-mail não encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(bCryptEncoder.encode(newPass));
		
		clienteRepository.save(cliente);
		emailSevice.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		
		for(int i = 0; i < 0; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		//A base para os caracteres é a tabela de códigos unicode
		
		int opt = rand.nextInt(3);
		
		if(opt == 0) { //Gera um dígito
			return (char) (rand.nextInt(10)+48);
		}else if(opt == 1) { //Gera uma letra maiúscula
			return (char) (rand.nextInt(26)+65);
		}else { //Gera uma letra minúscula
			return (char) (rand.nextInt(26)+97);
		}
	}
}
