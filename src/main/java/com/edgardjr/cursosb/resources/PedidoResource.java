package com.edgardjr.cursosb.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edgardjr.cursosb.domain.Pedido;
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

}
