package com.edgardjr.cursosb.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edgardjr.cursosb.domain.Cliente;
import com.edgardjr.cursosb.dto.ClienteDTO;
import com.edgardjr.cursosb.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> getById(@PathVariable Integer id) {
		Cliente cliente = this.clienteService.getById(id);
		
		return ResponseEntity.ok().body(cliente);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteDTO clienteDTO) {
		Cliente cliente = this.clienteService.save(new Cliente(clienteDTO));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(cliente.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id){
		clienteDTO.setId(id);
		this.clienteService.save(new Cliente(clienteDTO));
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		this.clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> getAll() {
		List<ClienteDTO> clientes = this.clienteService.getAll()
				.stream().map(c -> new ClienteDTO(c))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(clientes);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> getByPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="size", defaultValue="24") Integer size, 
			@RequestParam(value="order", defaultValue="nome") String order, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		Page<ClienteDTO> clientes = this.clienteService.getAll(page, size, order, direction)
				.map(c -> new ClienteDTO(c));
		
		return ResponseEntity.ok().body(clientes);
	}

}
