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
import projeto.bancario.poo.model.Conta;
import projeto.bancario.poo.model.ContaCorrente;
import projeto.bancario.poo.model.ContaPoupanca;

@SuppressWarnings("rawtypes")
public class ContaDAO implements IEndityDAO<Conta> {

	private final DataBaseConnectionMySQL DB;
	
	public ContaDAO() {
		// TODO Auto-generated constructor stub
		this.DB = new DataBaseConnectionMySQL();
		
	}

	@Override
	public void save(Conta conta) throws DataBaseException {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO conta (numero_conta, saldo, data_abertura, status, cliente_id, tipo_conta) VALUES (?, ?, ?, ?, ?, ?);";
		
		try (Connection connection = DB.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			connection.setAutoCommit(false);
			
			pstm.setString(1, conta.getNumeroConta());
			pstm.setBigDecimal(2, conta.getSaldo());
			pstm.setBoolean(3, conta.isStatus());
			pstm.setLong(4, (long) conta.getCliente().get(0));
			pstm.setObject(5, conta.getDataConta());
			/*
			 * Se a conta corrente dor a instânciada for corrente 
			 * é o tipo vai ser 1(CREDITO); caso contrário será 2(DEBITO).
			 */
			pstm.setInt(6, (conta instanceof ContaCorrente) ? 1 : 2);
			
			// Se nenhuma linha linha for chamada do atributo no pstm vai emitir uma menssagem de erro do banco de dados
			int linhasAfetadas = pstm.executeUpdate();
			
			if (linhasAfetadas == 0) {
				throw new DataBaseException(" Falha ao criar conta, nenhuma linha afetada.");
			}
			
			try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					long contaId = generatedKeys.getLong(1);
					
					if (conta instanceof ContaCorrente) {
						saveContaCorrente(connection, (ContaCorrente) conta, contaId);
					} else if (conta instanceof ContaPoupanca) {
						saveContaPoupanca(connection, (ContaPoupanca) conta, contaId);
					}
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DataBaseException(" Erro ao salvar conta", e);
		}
	}
	
	private void saveContaCorrente(Connection connection, ContaCorrente	contaCoreente, long contaId) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO contas_correntes (conta_id, limite_cheque_especial, taxa_manutencao) VALUES (?, ?, ?);";
		
		try (PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.setLong(1, contaId);
			pstm.setBigDecimal(2, contaCoreente.getLimiteChequeEspecial());
			pstm.setBigDecimal(3, contaCoreente.getTaxaManutencao());
			pstm.executeUpdate();
		}
	}
	
	private void saveContaPoupanca(Connection connection, ContaPoupanca contaPoupanca, long contaId) throws SQLException{
		// TODO Auto-generated method stub
		String sql = "INSERT INTO contas_correntes (conta_id, taxa_rendimento, data_aniversario) VALUES (?, ?, ?);";
		
		try (PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.setLong(1, contaId);
			pstm.setBigDecimal(2, contaPoupanca.getTaxaRendimento());
			pstm.setObject(3, contaPoupanca.getDataAniversario());
			pstm.executeUpdate();
		}
	}

	@Override
	public void remove(Conta conta) throws DataBaseException {
		// TODO Auto-generated method stub
        String sql = "DELETE FROM conta WHERE numero_conta = ?";
        
        try (Connection connection = DB.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            
            pstm.setString(1, conta.getNumeroConta());
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Erro ao deletar conta", e);
        }
	}

	@Override
	public void update(Conta conta) throws DataBaseException {
		// TODO Auto-generated method stub
        String sql = "UPDATE conta SET saldo = ?, status = ? WHERE numero_conta = ?";
        
        try (Connection connection = DB.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            
            pstm.setBigDecimal(1, conta.getSaldo());
            pstm.setBoolean(2, conta.isStatus());
            pstm.setString(3, conta.getNumeroConta());
            
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException("Erro ao atualizar conta", e);
        }
	}

	@Override
	public Conta findById(Long id) throws DataBaseException {
		// TODO Auto-generated method stub
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new DataBaseException(" Erro ao buscar conta por id.", e);
		}
		return null;
	}

	@Override
	public List<Conta> findAll() throws DataBaseException {
		// TODO Auto-generated method stub
        List<Conta> contas = new ArrayList<>();
        String sql = "SELECT * FROM conta";
        
        try (Connection connection = DB.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
            
            while (resultSet.next()) {
                // Implementação para criar o tipo de conta específico
                // contas.add(...);
            }
        } catch (SQLException e) {
            throw new DataBaseException("Erro ao listar todas as contas", e);
        }
        return contas;
    }
	
	   public Conta findByNumeroConta(String numeroConta) throws DataBaseException {
	        String sql = "SELECT * FROM conta WHERE numero_conta = ?";
	        
	        try (Connection connection = DB.getConnection();
	             PreparedStatement pstm = connection.prepareStatement(sql)) {
	            
	            pstm.setString(1, numeroConta);
	            ResultSet resultSet = pstm.executeQuery();
	            
	            if (resultSet.next()) {
	                // Implementação para criar o tipo de conta específico
	                return null;
	            }
	            return null;
	        } catch (SQLException e) {
	            throw new DataBaseException("Erro ao buscar conta por número", e);
	        }
	    }}