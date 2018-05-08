package com.edgardjr.cursosb.services;

import org.springframework.mail.SimpleMailMessage;

import com.edgardjr.cursosb.domain.Pedido;

public interface EmailService {
	void sendOrderConfirmationEmail(Pedido pedido);
	void sendEmail (SimpleMailMessage msg);
}
