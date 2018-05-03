package com.edgardjr.cursosb.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edgardjr.cursosb.domain.PagamentoBoleto;
import com.edgardjr.cursosb.domain.Pedido;
import com.edgardjr.cursosb.domain.enums.EstadoPagamento;
import com.edgardjr.cursosb.repostories.PedidoRepository;

@Service
public class PedidoService extends GenericServiceImpl<Pedido, Integer, PedidoRepository> {
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	public PedidoService(JpaRepository<Pedido, Integer> repository) {
		super(repository, Pedido.class);
	}
	
	@Override
	@Transactional
	public Pedido save(Pedido pedido) {
		pedido.setCreatedAt(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if(pedido.getPagamento() instanceof PagamentoBoleto) {
			PagamentoBoleto pagamentoBoleto = (PagamentoBoleto) pedido.getPagamento();
			pagamentoBoleto.setDataVencimento(this.boletoService.gerarDataVencimento(pedido.getCreatedAt()));
		}
		
		pedido.getItens().forEach(ip -> {
			ip.setDesconto(0.0);
			ip.setPreco(this.produtoService.getById(ip.getProduto().getId()).getPreco());
			ip.setPedido(pedido);
		});
		
		return super.save(pedido);
	}
}
