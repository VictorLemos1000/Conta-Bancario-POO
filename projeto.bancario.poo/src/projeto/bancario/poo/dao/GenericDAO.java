package projeto.bancario.poo.dao;

import java.util.List;

import projeto.bancario.poo.exception.DataBaseException;

public interface GenericDAO<T> {

	public void create(T endity) throws DataBaseException;
	public void update(T endity) throws DataBaseException;
	public void delete(Long id) throws DataBaseException;
	public T findByID(Long id) throws DataBaseException;
	public List<T> findListAll() throws DataBaseException;
}
