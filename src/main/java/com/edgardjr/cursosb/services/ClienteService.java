package com.edgardjr.cursosb.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.edgardjr.cursosb.domain.Cliente;
import com.edgardjr.cursosb.repostories.ClienteRepository;
import com.edgardjr.cursosb.repostories.EnderecoRepository;

@Service
public class ClienteService extends GenericServiceImpl<Cliente, Integer, ClienteRepository> {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	public ClienteService(JpaRepository<Cliente, Integer> repository) {
		super(repository, Cliente.class);
	}
	
	@Override
	@Transactional
	public Cliente save (Cliente cliente) {
		cliente = super.save(cliente);
		this.enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
		
	}
	
}
