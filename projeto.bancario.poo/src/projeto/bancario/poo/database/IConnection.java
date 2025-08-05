package projeto.bancario.poo.database;

import java.sql.Connection;

import projeto.bancario.poo.exception.DataBaseException;

public interface IConnection {

	public Connection getConnection(Connection connection) throws DataBaseException;
	
	public void closeConnection();
	public void criarDB();
}
