package projeto.bancario.poo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import projeto.bancario.poo.database.DataBaseConnectionMySQL;
import projeto.bancario.poo.exception.DataBaseException;
import projeto.bancario.poo.model.Cliente;

@SuppressWarnings("rawtypes")
public class ClienteDAO implements GenericDAO<Cliente> {

//	private Connection connection;
	private final DataBaseConnectionMySQL BD;
	private Statement DB;

	public ClienteDAO() {
		// TODO Auto-generated constructor stub
		this.BD = new DataBaseConnectionMySQL();
	}

	@Override
	public void create(Cliente cliente) throws DataBaseException {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO clientes (cpf, nome, endereco) VALUES (?, ?, ?)";
		
		// 
		try (Connection connection = DB.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstm.setString(1, cliente.getCpf());
			pstm.setString(2, cliente.getNome());
			pstm.setString(3, cliente.getEndereco());
			pstm.executeUpdate();
			
			try (ResultSet resultSet = pstm.getGeneratedKeys()) {
				if (resultSet.next()) {
					cliente.setId(resultSet.getLong(1));
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DataBaseException(" Erro ao inserir cliente.", e);
		}
	}

	@Override
	public void update(Cliente cliente) throws DataBaseException {
		// TODO Auto-generated method stub
		String sql = "UPDATE clientes SET cpf = ?, nome = ?, endereco = ? WHERE id = ?";
		
		try (Connection connection = DB.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.setString(1, cliente.getCpf());
			pstm.setString(2, cliente.getNome());
			pstm.setString(3, cliente.getEndereco());
			pstm.setLong(4, cliente.getId());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DataBaseException(" Erro ao atualizar cliente.", e);
		}
	}

	@Override
	public void delete(Long id) throws DataBaseException {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM clientes WHERE id = ?";
		
		try (Connection connection = DB.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.setLong(1, id);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DataBaseException(" Erro ao excluir cliente", e);
		}
	}

	@Override
	public Cliente findByID(Long id) throws DataBaseException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM clientes WHERE id = ?";
		Cliente cliente = null;
		
		try (Connection connection = DB.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.setLong(1, id);
			
			try (ResultSet resultSet = pstm.executeQuery()) {
				if (resultSet.next()) {
					cliente = new Cliente();
					cliente.setId(resultSet.getLong("id"));
					cliente.setCpf(resultSet.getString("cpf"));
					cliente.setNome(resultSet.getString("nome"));
					cliente.setEndereco(resultSet.getString("endereco"));
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DataBaseException(" Erro ao buscar cliente pelo id.", e);
		}
		return null;
	}

	@Override
	public List<Cliente> findListAll() throws DataBaseException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM clientes";
		List<Cliente> clientes = new ArrayList<>();
		
		try (Connection connection = DB.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql); ResultSet resultSet = pstm.executeQuery())  {
			while (resultSet.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(resultSet.getLong("id"));
				cliente.setCpf(resultSet.getString("cpf"));
				cliente.setNome(resultSet.getString("nome"));
				cliente.setEndereco(resultSet.getString("endereco"));
				clientes.add(cliente);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DataBaseException(" Erro ao listar todos os clientes.", e);
		}
		
		return clientes;
	}
	
	@SuppressWarnings("unused")
	private Cliente findByCpf(String cpf) throws DataBaseException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM clientes WHERE id = ?";
		Cliente cliente = null;
		
		try (Connection connection = BD.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.setString(1, cpf);
			
			try (ResultSet resultSet = pstm.executeQuery()){
				cliente = new Cliente();
				cliente.setId(resultSet.getLong("id"));
				cliente.setCpf(resultSet.getString("cpf"));
				cliente.setNome(resultSet.getString("nome"));
				cliente.setEndereco(resultSet.getString("endereco"));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DataBaseException(" Erro ao buscar cliente por cpf", e);
		}
		
		return cliente;
	}
}
