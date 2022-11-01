package jsilgado.mecalux.service;

import java.util.List;
import java.util.UUID;

public interface ServiceTemplate<E, I> {

	public E initialize();

	public List<E> getAll();

	public E getById(UUID id);

	public E insert(I i);

	public void update(E t);

	public void delete(UUID id);

}
