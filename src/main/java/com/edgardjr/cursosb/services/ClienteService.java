package com.edgardjr.cursosb.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.edgardjr.cursosb.domain.Cidade;
import com.edgardjr.cursosb.domain.Cliente;
import com.edgardjr.cursosb.domain.Endereco;
import com.edgardjr.cursosb.domain.enums.Perfil;
import com.edgardjr.cursosb.domain.enums.TipoCliente;
import com.edgardjr.cursosb.dto.ClienteNewDTO;
import com.edgardjr.cursosb.repostories.ClienteRepository;
import com.edgardjr.cursosb.security.UserSS;
import com.edgardjr.cursosb.services.exceptions.AuthorizationException;
import com.edgardjr.cursosb.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService extends GenericServiceImpl<Cliente, Integer, ClienteRepository> {
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	@Autowired
	public ClienteService(JpaRepository<Cliente, Integer> repository) {
		super(repository, Cliente.class);
	}
	
	@Override
	public Cliente getById(Integer id) throws ObjectNotFoundException {
		Optional<UserSS> userSS = UserService.authenticated();
		
		userSS.ifPresent(user -> {
			if (!user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
				throw new AuthorizationException("Acesso Negado");
			}
		});
		
		return super.getById(id);
	}
	
	public Cliente getByEmail(String email) {
		return ((ClienteRepository) this.getRepository()).findByEmail(email);
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
