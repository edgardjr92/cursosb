package com.edgardjr.cursosb.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgardjr.cursosb.domain.Cliente;
import com.edgardjr.cursosb.repostories.ClienteRepository;
import com.edgardjr.cursosb.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository ClienteRepository;
	
	public Cliente getById(Integer id) throws ObjectNotFoundException {
		Optional<Cliente> Cliente = this.ClienteRepository.findById(id);
		return Cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
}
