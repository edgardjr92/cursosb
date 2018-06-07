package com.edgardjr.cursosb.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.edgardjr.cursosb.domain.Cliente;
import com.edgardjr.cursosb.domain.Pedido;

public interface EmailService {
	void sendOrderConfirmationEmail(Pedido pedido);
	void sendEmail (SimpleMailMessage msg);
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	void sendHtmlEmail(MimeMessage msg);
	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
