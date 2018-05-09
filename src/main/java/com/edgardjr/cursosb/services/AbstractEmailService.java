package com.edgardjr.cursosb.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.edgardjr.cursosb.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	@Async
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(pedido);
		this.sendEmail(sm);
	}

	@Override
	@Async
	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
		try {
			MimeMessage mm = prepareMimeMessageMessageFromPedido(pedido);
			this.sendHtmlEmail(mm);
		} catch (MessagingException e) {
			this.sendOrderConfirmationEmail(pedido);
		}
	}
	
	protected MimeMessage prepareMimeMessageMessageFromPedido(Pedido pedido) throws MessagingException {
		MimeMessage mm = this.javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mm, true);
		helper.setTo(pedido.getCliente().getEmail());
		helper.setFrom(this.sender);
		helper.setSubject("Pedido Confirmado! Código: " + pedido.getId());
		helper.setSentDate(new Date(System.currentTimeMillis()));
		helper.setText(this.htmlFromTemplatePedido(pedido), true);
		
		return mm;
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(pedido.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido Confirmado! Código: " + pedido.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(pedido.toString());
		return sm;
	}
	
	protected String htmlFromTemplatePedido(Pedido pedido) {
		Context context =  new Context();
		context.setVariable("pedido", pedido);
		return this.templateEngine.process("email/confirmacaoPedido", context);
	}
}
