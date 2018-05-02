package com.edgardjr.cursosb.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edgardjr.cursosb.domain.Produto;
import com.edgardjr.cursosb.dto.ProdutoDTO;
import com.edgardjr.cursosb.resources.utils.URL;
import com.edgardjr.cursosb.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> getById(@PathVariable Integer id) {
		Produto produto = this.produtoService.getById(id);
		return ResponseEntity.ok().body(produto);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> getByPage(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="size", defaultValue="24") Integer size, 
			@RequestParam(value="order", defaultValue="nome") String order, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		List<Integer> ids = URL.decodeIntList(categorias);
		
		Page<ProdutoDTO> produtos = this.produtoService.search(URL.decodeParam(nome), ids, page, size, order, direction)
				.map(p -> new ProdutoDTO(p));
		
		return ResponseEntity.ok().body(produtos);
	}

}
