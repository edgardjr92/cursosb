package com.edgardjr.cursosb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.edgardjr.cursosb.domain.Categoria;
import com.edgardjr.cursosb.repostories.CategoriaRepository;
import com.edgardjr.cursosb.services.exceptions.DataIntegrityException;
import com.edgardjr.cursosb.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria getById(Integer id) throws ObjectNotFoundException {
		Optional<Categoria> categoria = this.categoriaRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria categoria) {
		return this.categoriaRepository.save(categoria);
	}
	
	public Categoria update(Categoria categoria) {
		this.getById(categoria.getId());
		return this.categoriaRepository.save(categoria);
	}
	
	public void delete(Integer id) throws DataIntegrityException {
		this.getById(id);
		try {
			this.categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover categoria que possui produto");
		}
	}
	
	public List<Categoria> getAll(){
		return this.categoriaRepository.findAll();
	}
	
	public Page<Categoria> getPage(Integer page, Integer size, String order, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), order);
		return this.categoriaRepository.findAll(pageRequest);
	}
}
