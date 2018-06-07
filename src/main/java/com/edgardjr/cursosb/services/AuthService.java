package com.edgardjr.cursosb.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.edgardjr.cursosb.domain.Cliente;
import com.edgardjr.cursosb.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	@Autowired
	private EmailService emailService;
	
	private Random random = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = this.clienteService.getByEmail(email);
		
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(bCrypt.encode(newPass));
		
		this.clienteService.save(cliente);
		this.emailService.sendNewPasswordEmail(cliente, newPass);
	}
	
	private String newPassword() {
		char[] vet = new char[10];
		
		for (int i = 0; i < vet.length; i++) {
			vet[i] = randomChar();
		}
		
		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		char character;
		
		switch (opt) {
			// gera um digito
			case 0:
				character = (char) (random.nextInt(10) + 48);
				break;
			// gera letra maiuscula
			case 1:
				character = (char) (random.nextInt(26) + 65);
				break;
			// gera letra minuscula
			default:
				character = (char) (random.nextInt(26) + 97);
				break;
			}
		
		return character;
	}
}
