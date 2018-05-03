package com.edgardjr.cursosb.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.edgardjr.cursosb.domain.Cliente;
import com.edgardjr.cursosb.domain.Endereco;
import com.edgardjr.cursosb.domain.ItemPedido;
import com.edgardjr.cursosb.domain.Pagamento;

public class PedidoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull(message="Preenchimento Obrigatório")
	private Cliente cliente;
	
	@NotNull(message="Preenchimento Obrigatório")
	private Endereco enderecoEntrega;
	
	@NotNull(message="Preenchimento Obrigatório")
	private Pagamento pagamento;
	
	@NotNull(message="Preenchimento Obrigatório")
	@Size(min=1, message="O pedido deve conter pelo menos 1 item")
	private Set<ItemPedido> itens = new HashSet<>();
	
	public PedidoDTO() {
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Endereco getEnderecoEntrega() {
		return enderecoEntrega;
	}
	public void setEnderecoEntrega(Endereco enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}
	public Pagamento getPagamento() {
		return pagamento;
	}
	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}
	public Set<ItemPedido> getItens() {
		return itens;
	}
	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}	

}
