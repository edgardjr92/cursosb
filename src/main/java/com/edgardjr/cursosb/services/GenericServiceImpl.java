package com.edgardjr.cursosb.services;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.edgardjr.cursosb.domain.GenericDomain;
import com.edgardjr.cursosb.services.exceptions.DataIntegrityException;
import com.edgardjr.cursosb.services.exceptions.InternalServerError;
import com.edgardjr.cursosb.services.exceptions.ObjectNotFoundException;

@Service
public abstract class GenericServiceImpl<E extends GenericDomain<ID>, ID, R> implements GenericService<E, ID> {
	
	@Autowired
	private JpaRepository<E, ID> repository;
	private  Class<E> entityClass;
	
	public GenericServiceImpl(JpaRepository<E, ID> repository, Class<E> entityClass) {
		this.repository = repository;
		this.entityClass = entityClass;
	}
	
	public E getById(ID id) throws ObjectNotFoundException {
		Optional<E> entity = this.repository.findById(id);
		return entity.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + this.entityClass.getSimpleName()));
	}
	
	@Transactional
	public E save (E entity) {
		if (entity.getId() != null) {
			try {
				entity = this.merge(entity, this.getById(entity.getId()));
			} catch (Exception e) {
				throw new InternalServerError("Ocorreu um inesperado ao atualizar. Tente novamente!");
			}
		}
		return this.repository.save(entity);
	}
	
	public List<E> getAll() {
		return this.repository.findAll();
	}
	
	public Page<E> getAll(Integer page, Integer size, String order, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), order);
		return this.repository.findAll(pageRequest);
	}
	
	public void delete(ID id) throws DataIntegrityException {
		E entity = this.getById(id);
		try {
			this.repository.deleteById(entity.getId());
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover entidade com associação.");
		}
	}
	
	private E merge(E source, E target) throws IllegalArgumentException, IllegalAccessException {		
		for (Field fieldSource : source.getClass().getDeclaredFields()) {
			if (!fieldSource.getName().equals("serialVersionUID")) {
				fieldSource.setAccessible(true);
				Object value = fieldSource.get(source);
				if (value != null) {
					for (Field fieldTarget : target.getClass().getDeclaredFields()) {
						if (fieldSource.getName().equals(fieldTarget.getName())) {
							fieldTarget.setAccessible(true);
							fieldTarget.set(target, value);
							break;
						}
					}
				}
			}
		}
		return target;
	}
}
