package com.edgardjr.cursosb.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.edgardjr.cursosb.domain.Pedido;
import com.edgardjr.cursosb.repostories.PedidoRepository;

@Service
public class PedidoService extends GenericServiceImpl<Pedido, Integer, PedidoRepository> {

	public PedidoService(JpaRepository<Pedido, Integer> repository) {
		super(repository, Pedido.class);
	}
	
}
