package com.souza.caio.services;

import org.springframework.mail.SimpleMailMessage;

import com.souza.caio.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
}
