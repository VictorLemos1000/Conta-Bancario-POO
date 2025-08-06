package projeto.bancario.poo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.zip.DataFormatException;

import projeto.bancario.poo.database.DataBaseConnectionMySQL;
import projeto.bancario.poo.exception.DataBaseException;
import projeto.bancario.poo.model.Cliente;
import projeto.bancario.poo.model.Conta;
import projeto.bancario.poo.model.ContaCorrente;
import projeto.bancario.poo.model.ContaPoupanca;
import projeto.bancario.poo.model.IConta;
import projeto.bancario.poo.service.IContaService;

public class ContaDAO implements GenericDAO<Conta> {

	private final DataBaseConnectionMySQL DB;
	
	public ContaDAO() {
		// TODO Auto-generated constructor stub
		this.DB = new DataBaseConnectionMySQL();
	}

	@Override
	public void create(Conta conta) throws DataBaseException {
		// TODO Auto-generated method stub
		
	}

	private void createContraCorrente() {
		// TODO Auto-generated method stub

	}
	
	private void createContaPoupanca() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void update(Conta conta) throws DataBaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) throws DataBaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Conta<?> findByID(Long id) throws DataBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Conta> findListAll() throws DataBaseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
