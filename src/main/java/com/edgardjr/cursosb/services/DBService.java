package com.edgardjr.cursosb.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.edgardjr.cursosb.domain.Categoria;
import com.edgardjr.cursosb.domain.Cidade;
import com.edgardjr.cursosb.domain.Cliente;
import com.edgardjr.cursosb.domain.Endereco;
import com.edgardjr.cursosb.domain.Estado;
import com.edgardjr.cursosb.domain.ItemPedido;
import com.edgardjr.cursosb.domain.Pagamento;
import com.edgardjr.cursosb.domain.PagamentoBoleto;
import com.edgardjr.cursosb.domain.PagamentoCartao;
import com.edgardjr.cursosb.domain.Pedido;
import com.edgardjr.cursosb.domain.Produto;
import com.edgardjr.cursosb.domain.enums.EstadoPagamento;
import com.edgardjr.cursosb.domain.enums.Perfil;
import com.edgardjr.cursosb.domain.enums.TipoCliente;
import com.edgardjr.cursosb.repostories.CategoriaRepository;
import com.edgardjr.cursosb.repostories.CidadeRepository;
import com.edgardjr.cursosb.repostories.ClienteRepository;
import com.edgardjr.cursosb.repostories.EnderecoRepository;
import com.edgardjr.cursosb.repostories.EstadoRepository;
import com.edgardjr.cursosb.repostories.ItemPedidoRepository;
import com.edgardjr.cursosb.repostories.PagamentoRepository;
import com.edgardjr.cursosb.repostories.PedidoRepository;
import com.edgardjr.cursosb.repostories.ProdutoRepository;

@Service
public class DBService {
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository ItemPedidoRepository;
	
	public void instantiateTestDatabase() throws ParseException {
		
		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Escritórios");
		Categoria categoria3 = new Categoria(null, "Cama Mesa e Banho");
		Categoria categoria4 = new Categoria(null, "Eletronicos");
		Categoria categoria5 = new Categoria(null, "Jardinagem");
		Categoria categoria6 = new Categoria(null, "Decoração");
		Categoria categoria7 = new Categoria(null, "Perfumaria");
		
		Produto produto1 = new Produto(null, "Computador", 2000.00);
		Produto produto2 = new Produto(null, "Impressora", 800.00);
		Produto produto3 = new Produto(null, "Mouse", 80.00);
		Produto produto4 = new Produto(null, "Mesa de Escritorio", 300.00);
		Produto produto5 = new Produto(null, "Toalha", 50.00);
		Produto produto6 = new Produto(null, "Colcha", 200.00);
		Produto produto7 = new Produto(null, "TV true color", 1200.00);
		Produto produto8 = new Produto(null, "Roçadeira", 800.00);
		Produto produto9 = new Produto(null, "Abajour", 100.00);
		Produto produto10 = new Produto(null, "Pendente", 180.00);
		Produto produto11 = new Produto(null, "Shampoo", 90.0);
		
		categoria1.getProdutos().addAll(Arrays.asList(produto1,produto2,produto3));
		categoria2.getProdutos().addAll(Arrays.asList(produto2, produto4));
		categoria3.getProdutos().addAll(Arrays.asList(produto5, produto6));
		categoria4.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3, produto7));
		categoria5.getProdutos().addAll(Arrays.asList(produto8));
		categoria6.getProdutos().addAll(Arrays.asList(produto9, produto10));
		categoria7.getProdutos().addAll(Arrays.asList(produto11));
		
		produto1.getCategorias().addAll(Arrays.asList(categoria1, categoria4));
		produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2, categoria4));
		produto3.getCategorias().addAll(Arrays.asList(categoria1, categoria4));
		produto4.getCategorias().addAll(Arrays.asList(categoria2));
		produto5.getCategorias().addAll(Arrays.asList(categoria3));
		produto6.getCategorias().addAll(Arrays.asList(categoria3));
		produto7.getCategorias().addAll(Arrays.asList(categoria4));
		produto8.getCategorias().addAll(Arrays.asList(categoria5));
		produto9.getCategorias().addAll(Arrays.asList(categoria6));
		produto10.getCategorias().addAll(Arrays.asList(categoria6));
		produto11.getCategorias().addAll(Arrays.asList(categoria7));	
		
		this.categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2, categoria3, categoria4, categoria5, categoria6, categoria7));
		this.produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5, 
				produto6, produto7, produto8, produto9, produto10, produto11));
		
		Estado estado1 = new Estado(null, "Minas Gerais");
		Estado estado2 = new Estado(null, "São Paulo");
		
		Cidade cidade1 = new Cidade(null, "Uberlandia", estado1);
		Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
		Cidade cidade3 = new Cidade(null, "Campinas", estado2);
		
		estado1.getCidades().addAll(Arrays.asList(cidade1));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));
		
		this.estadoRepository.saveAll(Arrays.asList(estado1, estado2));
		this.cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));
		
		Cliente cliente1 = new Cliente(null, "Maria Silva", "santoos.ed@gmail.com", "12723555003", 
				TipoCliente.PESSOA_FISICA, this.bCrypt.encode("123456"));
		
		Cliente cliente2 = new Cliente(null, "Ana Costa", "ana@gmail.com", "14276203775", 
				TipoCliente.PESSOA_FISICA, this.bCrypt.encode("123456"));
		
		cliente2.addPerfil(Perfil.ADMIN);
		
		cliente1.getTelefones().addAll(Arrays.asList("27112278", "998877868"));
		cliente2.getTelefones().addAll(Arrays.asList("27566789", "998765654"));
		
		Endereco endereco1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "23456789", cliente1, cidade1);
		Endereco endereco2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "45600879", cliente1, cidade2);
		
		Endereco endereco3 = new Endereco(null, "Avenida Floriano", "2000", "Sala 200", "Centro", "45600879", cliente2, cidade2);
		
		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
		cliente1.getEnderecos().addAll(Arrays.asList(endereco3));
		
		this.clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));
		this.enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2, endereco3));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido pedido1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cliente1, endereco1);
		Pedido pedido2 = new Pedido(null, sdf.parse("10/10/2017 10:35"), cliente1, endereco2);
		
		Pagamento pagamento1 = new PagamentoCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pagamento1);
		
		Pagamento pagamento2 = new PagamentoBoleto(null, EstadoPagamento.PENDENTE, pedido2, sdf.parse("20/10/2017 00:00"), null);
		pedido2.setPagamento(pagamento2);
		
		cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		this.pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		this.pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));
		
		ItemPedido item1 = new ItemPedido(pedido1, produto1, 0.00, 1, 2000.00);
		ItemPedido item2 = new ItemPedido(pedido1, produto3, 0.00, 2, 80.00);
		ItemPedido item3 = new ItemPedido(pedido2, produto2, 100.00, 1, 800.00);
		
		pedido1.getItens().addAll(Arrays.asList(item1, item2));
		pedido2.getItens().add(item3);
		
		produto1.getItens().add(item1);
		produto2.getItens().add(item3);
		produto3.getItens().add(item2);
		
		this.ItemPedidoRepository.saveAll(Arrays.asList(item1, item2, item3));
		
	}
}
