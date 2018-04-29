package com.edgardjr.cursosb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.edgardjr.cursosb.domain.Categoria;
import com.edgardjr.cursosb.repostories.CategoriaRepository;

@Service
public class CategoriaService extends GenericServiceImpl<Categoria, Integer, CategoriaRepository> {
	
	@Autowired
	public CategoriaService(JpaRepository<Categoria, Integer> repository) {
		super(repository, Categoria.class);
	}
}
