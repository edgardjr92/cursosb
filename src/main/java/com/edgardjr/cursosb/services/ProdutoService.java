package com.edgardjr.cursosb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.edgardjr.cursosb.domain.Categoria;
import com.edgardjr.cursosb.domain.Produto;
import com.edgardjr.cursosb.repostories.CategoriaRepository;
import com.edgardjr.cursosb.repostories.ProdutoRepository;

@Service
public class ProdutoService extends GenericServiceImpl<Produto, Integer, ProdutoRepository> {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public ProdutoService(JpaRepository<Produto, Integer> repository) {
		super(repository, Produto.class);
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer size, String order, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), order);
		List<Categoria> categorias = this.categoriaRepository.findAllById(ids);
		return ((ProdutoRepository) this.getRepository()).search(nome, categorias, pageRequest);
	}
}
