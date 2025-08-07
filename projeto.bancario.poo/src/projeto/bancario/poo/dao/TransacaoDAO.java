package projeto.bancario.poo.dao;

import java.math.BigDecimal;
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
import projeto.bancario.poo.model.TipoTransacao;
import projeto.bancario.poo.model.Transacao;

public class TransacaoDAO implements IEndityDAO<Transacao>{

    private final DataBaseConnectionMySQL DB = new DataBaseConnectionMySQL();
	
	@Override
	public void save(Transacao transacao) throws DataBaseException {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO transacoes (data, valor, tipo, numero_conta_origem, numero_conta_destino) VALUES (?, ?, ?, ?, ?);";
   
		try (Connection connection = DB.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
       
			pstm.setDate(1, Date.valueOf(transacao.getData().toLocalDate()));
//			pstm.setBigDecimal(2, transacao.getValor());
//			pstm.setInt(3, transacao.getTipo().getValue());
			pstm.setString(4, transacao.getContaOrigem() != null ? transacao.getContaOrigem().getNumeroConta() : null);
			pstm.setString(5, transacao.getContaDestino() != null ? transacao.getContaDestino().getNumeroConta() : null);
       
			int affectedRows = pstm.executeUpdate();
       
			if (affectedRows == 0) {
				throw new DataBaseException("Falha ao criar transação, nenhuma linha afetada");
			}
       
			try (ResultSet resultSet = pstm.getGeneratedKeys()) {
				if (resultSet.next()) {
					transacao.setIdTransacao(resultSet.getLong(1));
				}
			}
		} catch (SQLException e) {
			throw new DataBaseException("Erro ao salvar transação", e);
		}
    }

	@Override
	public void remove(Transacao transacao) throws DataBaseException {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM transacoes WHERE id_transacao = ?";
        
        	try (Connection connection = DB.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {
        		pstm.setLong(1, transacao.getIdTransacao());
        		pstm.executeUpdate();
        	} catch (SQLException e) {
        		throw new DataBaseException("Erro ao deletar transação", e);
        	}
	}

	@Override
	public void update(Transacao transacao) throws DataBaseException {
		// TODO Auto-generated method stub
		String sql = "UPDATE transacoes SET data = ?, valor = ?, tipo = ?, numero_conta_origem = ?, numero_conta_destino = ? WHERE id_transacao = ?";
   
		try (Connection connection = DB.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {
       
			pstm.setDate(1, Date.valueOf(transacao.getData().toLocalDate()));
//			pstm.setBigDecimal(2, transacao.getValor());
//			pstm.setInt(3, transacao.getTipo().getValue());
			pstm.setString(4, transacao.getContaOrigem() != null ? transacao.getContaOrigem().getNumeroConta() : null);
       		pstm.setString(5, transacao.getContaDestino() != null ? transacao.getContaDestino().getNumeroConta() : null);
       		pstm.setLong(6, transacao.getIdTransacao());
       
       		pstm.executeUpdate();
		} catch (SQLException e) {
			throw new DataBaseException("Erro ao atualizar transação", e);
		}
	}

	@Override
	public Transacao findById(Long id) throws DataBaseException {
		// TODO Auto-generated method stub
		 String sql = "SELECT * FROM transacoes WHERE id_transacao = ?";
	        Transacao transacao = null;
	        
	        try (Connection connection = DB.getConnection();
	             PreparedStatement pstm = connection.prepareStatement(sql)) {
	            
	            pstm.setLong(1, id);
	            
	            try (ResultSet resultSet = pstm.executeQuery()) {
	                if (resultSet.next()) {
	                    transacao = new Transacao();
	                    transacao.setIdTransacao(resultSet.getLong("id_transacao"));
	                    transacao.setData(resultSet.getTimestamp("data").toLocalDateTime());
//	                    transacao.setValor(resultSet.getBigDecimal("valor"));
//	                    transacao.setTipo(TipoTransacao.getEnumFromValue(resultSet.getInt("tipo")));
	                    // As contas (origem/destino) precisariam ser carregadas separadamente
	                }
	            }
	        } catch (SQLException e) {
	            throw new DataBaseException("Erro ao buscar transação por ID", e);
	        }
	        
	        return transacao;
	}

	@Override
	public List<Transacao> findAll() throws DataBaseException {
		// TODO Auto-generated method stub
		List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT * FROM transacoes";
        
        try (Connection connection = DB.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
            
            while (resultSet.next()) {
                Transacao transacao = new Transacao();
                transacao.setIdTransacao(resultSet.getLong("id_transacao"));
                transacao.setData(resultSet.getTimestamp("data").toLocalDateTime());
//                transacao.setValor(resultSet.getBigDecimal("valor"));
//                transacao.setTipo(TipoTransacao.getEnumFromValue(resultSet.getInt("tipo")));
                transacoes.add(transacao);
            }
        } catch (SQLException e) {
            throw new DataBaseException("Erro ao listar todas as transações", e);
        }
        
        return transacoes;
	}

	public List<Transacao> findByConta(String numeroConta) throws DataBaseException {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT * FROM transacoes WHERE numero_conta_origem = ? OR numero_conta_destino = ? ORDER BY data DESC";
        
        try (Connection connection = DB.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            
            pstm.setString(1, numeroConta);
            pstm.setString(2, numeroConta);
            
            try (ResultSet resultSet = pstm.executeQuery()) {
                while (resultSet.next()) {
                    Transacao transacao = new Transacao();
                    transacao.setIdTransacao(resultSet.getLong("id_transacao"));
                    transacao.setData(resultSet.getTimestamp("data").toLocalDateTime());
//                    transacao.setValor(resultSet.getBigDecimal("valor"));
//                    transacao.setTipo(TipoTransacao.getEnumFromValue(resultSet.getInt("tipo")));
                    transacoes.add(transacao);
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Erro ao buscar transações por conta", e);
        }
        
        return transacoes;
    }
}
