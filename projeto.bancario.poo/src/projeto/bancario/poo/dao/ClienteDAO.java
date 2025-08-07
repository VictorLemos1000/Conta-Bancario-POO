package projeto.bancario.poo.dao;

import java.sql.Connection;
import java.sql.Date;
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
public class ClienteDAO implements IEndityDAO<Cliente> {

//	private Connection connection;
	private final DataBaseConnectionMySQL DB;
	
	public ClienteDAO() {
		// TODO Auto-generated constructor stub
		this.DB = new DataBaseConnectionMySQL();
	}

	@Override
	public void save(Cliente cliente) throws DataBaseException {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO cliente (cpf, nome, data_nascimento) VALUES (?, ?, ?)";
        
        try (Connection connection = DB.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstm.setString(1, cliente.getCpf());
            pstm.setString(2, cliente.getNome());
            pstm.setDate(3, Date.valueOf(cliente.getDataNascimento().toLocalDate()));
            
            int affectedRows = pstm.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DataBaseException(" Falha ao criar cliente, nenhuma linha afetada.");
            }
            
            try (ResultSet resultSet = pstm.getGeneratedKeys()) {
                if (resultSet.next()) {
                    cliente.setId(resultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Erro ao salvar cliente", e);
        }
	}

	@Override
	public void remove(Cliente cliente) throws DataBaseException {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM cliente WHERE id = ?";
        
        try (Connection connection = DB.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            
            pstm.setLong(1, cliente.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException(" Erro ao deletar cliente.", e);
        }
	}

	@Override
	public void update(Cliente cliente) throws DataBaseException {
		// TODO Auto-generated method stub
		String sql = "UPDATE cliente SET cpf = ?, nome = ?, data_nascimento = ? WHERE id = ?";
        
        try (Connection connection = DB.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            
            pstm.setString(1, cliente.getCpf());
            pstm.setString(2, cliente.getNome());
            pstm.setDate(3, Date.valueOf(cliente.getDataNascimento().toLocalDate()));
            pstm.setLong(4, cliente.getId());
            
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException(" Erro ao atualizar cliente.", e);
        }
	}
	
	@Override
	public Cliente findById(Long id) throws DataBaseException {
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
					cliente.setDataNascimento(resultSet.getDate("data_nascimento").toLocalDate());
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DataBaseException(" Erro ao buscar cliente pelo id.", e);
		}
		
		return cliente;
	}

	@Override
	public List<Cliente> findAll() throws DataBaseException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM clientes";
		List<Cliente> clientes = new ArrayList<>();
		
		try (Connection connection = DB.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql); ResultSet resultSet = pstm.executeQuery())  {
			while (resultSet.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(resultSet.getLong("id"));
				cliente.setCpf(resultSet.getString("cpf"));
				cliente.setNome(resultSet.getString("nome"));
				cliente.setDataNascimento(resultSet.getDate("data_nascimento").toLocalDate());
				clientes.add(cliente);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DataBaseException(" Erro ao listar todos os clientes.", e);
		}
		
		return clientes;
	}
	
	@SuppressWarnings("unused")
	public Cliente findByCpf(String cpf) throws DataBaseException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM clientes WHERE id = ?";
		Cliente cliente = null;
		
		try (Connection connection = DB.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.setString(1, cpf);
			
			try (ResultSet resultSet = pstm.executeQuery()){
				cliente = new Cliente();
				cliente.setId(resultSet.getLong("id"));
				cliente.setCpf(resultSet.getString("cpf"));
				cliente.setNome(resultSet.getString("nome"));
				cliente.setDataNascimento(resultSet.getDate("data_nascimento").toLocalDate());
			}
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DataBaseException(" Erro ao buscar cliente por cpf", e);
		}
		
		return cliente;
	}
}
