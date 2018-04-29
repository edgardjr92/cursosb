package com.edgardjr.cursosb.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.edgardjr.cursosb.services.exceptions.DataIntegrityException;
import com.edgardjr.cursosb.services.exceptions.ObjectNotFoundException;

public interface GenericService<E, ID> {
	E getById(ID id) throws ObjectNotFoundException;
	E save(E entity);
	List<E> getAll();
	Page<E> getAll(Integer page, Integer size, String order, String direction);
	void delete(ID id) throws DataIntegrityException;
}
