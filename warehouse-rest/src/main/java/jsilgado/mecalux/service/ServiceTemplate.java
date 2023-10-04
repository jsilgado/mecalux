package jsilgado.mecalux.service;

import java.util.List;
import java.util.UUID;

public interface ServiceTemplate<E, I> {

	E initialize();

	List<E> getAll();

	E getById(UUID id);

	E insert(I i);

	void update(E t);

	void delete(UUID id);

}
