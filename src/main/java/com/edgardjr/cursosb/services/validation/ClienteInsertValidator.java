package com.edgardjr.cursosb.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.edgardjr.cursosb.domain.Cliente;
import com.edgardjr.cursosb.domain.enums.TipoCliente;
import com.edgardjr.cursosb.dto.ClienteNewDTO;
import com.edgardjr.cursosb.repostories.ClienteRepository;
import com.edgardjr.cursosb.resources.exceptions.FieldMessage;
import com.edgardjr.cursosb.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> errors = new ArrayList<>();
		
		if (objDto.getTipo().equals(TipoCliente.PESSOA_FISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			errors.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		} else if (objDto.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			errors.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente cliente = this.clienteRepository.findByEmail(objDto.getEmail());
		
		if (cliente != null) {
			errors.add(new FieldMessage("email", "Email já cadastrado"));
		}
		
		// inclua os testes aqui, inserindo erros na lista
		errors.forEach(e -> {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getField())
				.addConstraintViolation();
		});
		
		return errors.isEmpty();
	}
}