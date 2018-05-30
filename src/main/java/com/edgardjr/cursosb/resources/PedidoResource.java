package com.edgardjr.cursosb.resources;

import java.net.URI;

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

import com.edgardjr.cursosb.domain.Pedido;
import com.edgardjr.cursosb.dto.CategoriaDTO;
import com.edgardjr.cursosb.dto.PedidoDTO;
import com.edgardjr.cursosb.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService pedidoService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> getById(@PathVariable Integer id) {
		Pedido pedido = this.pedidoService.getById(id);
		
		return ResponseEntity.ok().body(pedido);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody PedidoDTO pedidoDTO) {
		Pedido pedido = this.pedidoService.save(new Pedido(pedidoDTO));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(pedido.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<Pedido>> getByPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="size", defaultValue="24") Integer size, 
			@RequestParam(value="order", defaultValue="createdAt") String order, 
			@RequestParam(value="direction", defaultValue="DESC") String direction) {
		
		Page<Pedido> pedidos = this.pedidoService.getByCliente(page, size, order, direction);
		
		return ResponseEntity.ok().body(pedidos);
	}

}
