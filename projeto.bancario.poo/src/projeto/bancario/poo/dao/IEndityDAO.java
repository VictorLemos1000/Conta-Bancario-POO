package projeto.bancario.poo.dao;

import java.util.List;

import projeto.bancario.poo.exception.DataBaseException;

// Uso de uma inteface genérica.
public interface IEndityDAO<T> {

	void save(T t) throws DataBaseException;
	void remove(T t) throws DataBaseException;
	void update(T t) throws DataBaseException;
	T findById(Long id) throws DataBaseException;
	List<T> findAll() throws DataBaseException;
}
