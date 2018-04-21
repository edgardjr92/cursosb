package com.edgardjr.cursosb.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edgardjr.cursosb.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Categoria> testar() {
		
		Categoria categoria1 = new Categoria(1,"Informatica");
		Categoria categoria2 = new Categoria(2,"Escritorio");
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		categorias.add(categoria1);
		categorias.add(categoria2);
		
		return categorias;
	}

}
