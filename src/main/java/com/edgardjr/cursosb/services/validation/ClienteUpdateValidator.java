package com.edgardjr.cursosb.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.edgardjr.cursosb.domain.Cliente;
import com.edgardjr.cursosb.dto.ClienteDTO;
import com.edgardjr.cursosb.repostories.ClienteRepository;
import com.edgardjr.cursosb.resources.exceptions.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId =  Integer.parseInt(map.get("id"));
		
		List<FieldMessage> errors = new ArrayList<>();
		
		Cliente cliente = this.clienteRepository.findByEmail(objDto.getEmail());
		
		if (cliente != null && !cliente.getId().equals(uriId)) {
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