package com.edgardjr.cursosb.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edgardjr.cursosb.domain.Categoria;
import com.edgardjr.cursosb.services.CategoriaService;
import com.edgardjr.cursosb.services.exceptions.ObjectNotFoundException;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) throws ObjectNotFoundException {
		Categoria cateogira = this.categoriaService.getById(id);
		
		return ResponseEntity.ok().body(cateogira);
	}

}
