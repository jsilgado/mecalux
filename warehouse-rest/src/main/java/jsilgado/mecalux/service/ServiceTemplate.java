package jsilgado.mecalux.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ServiceTemplate<E, I> {

	E initialize();

	List<E> getAll();

	E getById(UUID id);

	E insert(I i);

	void update(E t);

	void delete(UUID id);
	
	Page<E> search(PageRequest pageRequest);

}
