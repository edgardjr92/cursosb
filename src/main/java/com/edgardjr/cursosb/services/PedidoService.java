package com.edgardjr.cursosb.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edgardjr.cursosb.domain.Cliente;
import com.edgardjr.cursosb.domain.PagamentoBoleto;
import com.edgardjr.cursosb.domain.Pedido;
import com.edgardjr.cursosb.domain.enums.EstadoPagamento;
import com.edgardjr.cursosb.repostories.PedidoRepository;
import com.edgardjr.cursosb.security.UserSS;
import com.edgardjr.cursosb.services.exceptions.AuthorizationException;

@Service
public class PedidoService extends GenericServiceImpl<Pedido, Integer, PedidoRepository> {
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public PedidoService(JpaRepository<Pedido, Integer> repository) {
		super(repository, Pedido.class);
	}
	
	@Override
	@Transactional
	public Pedido save(Pedido pedido) {
		pedido.setCreatedAt(new Date());
		pedido.setCliente(this.clienteService.getById(pedido.getCliente().getId()));
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if(pedido.getPagamento() instanceof PagamentoBoleto) {
			PagamentoBoleto pagamentoBoleto = (PagamentoBoleto) pedido.getPagamento();
			pagamentoBoleto.setDataVencimento(this.boletoService.gerarDataVencimento(pedido.getCreatedAt()));
		}
		
		pedido.getItens().forEach(ip -> {
			ip.setDesconto(0.0);
			ip.setProduto(this.produtoService.getById(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		});
		
		Pedido pedidoNovo = super.save(pedido);
		this.emailService.sendOrderConfirmationHtmlEmail(pedidoNovo);
		
		return pedidoNovo;
	}
	
	public Page<Pedido> getByCliente(Integer page, Integer size, String order, String direction) {
		Optional<UserSS> userSS = UserService.authenticated();
		
		if (!userSS.isPresent()) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), order);
		Cliente cliente = this.clienteService.getById(userSS.get().getId());
		
		return ((PedidoRepository) this.getRepository()).findByCliente(cliente, pageRequest);
	}
}
