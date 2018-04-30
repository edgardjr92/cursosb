package com.edgardjr.cursosb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.edgardjr.cursosb.domain.Cliente;
import com.edgardjr.cursosb.repostories.ClienteRepository;

@Service
public class ClienteService extends GenericServiceImpl<Cliente, Integer, ClienteRepository> {
	
	@Autowired
	public ClienteService(JpaRepository<Cliente, Integer> repository) {
		super(repository, Cliente.class);
	}
	
}
