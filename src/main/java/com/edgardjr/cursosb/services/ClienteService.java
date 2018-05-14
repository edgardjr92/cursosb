package com.edgardjr.cursosb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.edgardjr.cursosb.domain.Cidade;
import com.edgardjr.cursosb.domain.Cliente;
import com.edgardjr.cursosb.domain.Endereco;
import com.edgardjr.cursosb.domain.enums.TipoCliente;
import com.edgardjr.cursosb.dto.ClienteNewDTO;
import com.edgardjr.cursosb.repostories.ClienteRepository;

@Service
public class ClienteService extends GenericServiceImpl<Cliente, Integer, ClienteRepository> {
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	@Autowired
	public ClienteService(JpaRepository<Cliente, Integer> repository) {
		super(repository, Cliente.class);
	}
	
	public Cliente parse (ClienteNewDTO clienteNewDTO) {
		Cliente cliente = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), 
				clienteNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewDTO.getTipo()), this.bCrypt.encode(clienteNewDTO.getSenha()));
		
		Endereco endereco = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(), 
				clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), 
				cliente, new Cidade(clienteNewDTO.getCidadeId(), null, null));
		
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(clienteNewDTO.getTelefone1());
		
		if (!StringUtils.isEmpty(clienteNewDTO.getTelefone2())) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone2());
		}
		
		if (!StringUtils.isEmpty(clienteNewDTO.getTelefone3())) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone3());
		}
		
		return cliente;
	}
	
}
