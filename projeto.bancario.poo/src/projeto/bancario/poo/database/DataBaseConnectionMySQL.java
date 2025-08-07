package projeto.bancario.poo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import projeto.bancario.poo.exception.DataBaseException;
import projeto.bancario.poo.util.ConfigLoader;

@SuppressWarnings("unused")
public class DataBaseConnectionMySQL implements IConnection{

	private Connection connection;
	private final String USERNAME;
	private final String PASSWORD;
	private final String ADDRESS;
	private final String PORT;
	private final String DATABASE;
	
	@SuppressWarnings("static-access")
	public DataBaseConnectionMySQL() {
		// TODO Auto-generated constructor stub
		ConfigLoader configurationLoad = new ConfigLoader();
		configurationLoad.load("configure.properties");
		
		this.USERNAME = configurationLoad.getValor("DB_USER");
		this.PASSWORD = configurationLoad.getValor("DB_PASSWORD");
		this.ADDRESS = configurationLoad.getValor("DB_ADDRESS");
		this.PORT = configurationLoad.getValor("DB_PORT");
		this.DATABASE = configurationLoad.getValor("DB_SCHEMA");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(" Driver JDBC MySQL não encontrado", e);
		}
	}
	
	// getConnection explícito
	@Override
	public Connection getConnection(Connection connection) throws DataBaseException {
		// TODO Auto-generated method stub
		try {
			if (connection == null || connection.isClosed()) {
				String url = "jdbc:mysql://" + ADDRESS + ":" + PORT + "/" + DATABASE + "?useSSL=true" + "&requireSSL=true" + "&verifyServerCertificate=false" + "&serverTimezone=UTC";
				
				return DriverManager.getConnection(url, USERNAME, PASSWORD);
			}
			return connection;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DataBaseException(" Erro ao estabelecer a conexão com o banco de dados: ", e);
		}
	}
	
	// getConnection implícito
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return connection;

	}
	
	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				connection = null;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println(" Não foi possível encerrar a conexão: " + e.getMessage());
		}
	}
	
	private String getSchemaSQL() {
		return """
            -- Tabela clientes
            CREATE TABLE IF NOT EXISTS clientes (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                cpf VARCHAR(11) UNIQUE NOT NULL,
                nome VARCHAR(100) NOT NULL,
                data_nascimento DATE NOT NULL
            );
            
            -- Tabela contas
            CREATE TABLE IF NOT EXISTS contas (
                numero_conta VARCHAR(20) PRIMARY KEY,
                saldo DECIMAL(15,2) NOT NULL DEFAULT 0,
                data_abertura DATE NOT NULL,
                status BOOLEAN NOT NULL DEFAULT TRUE,
                id_cliente BIGINT NOT NULL,
                tipo_conta TINYINT NOT NULL COMMENT '1-Corrente, 2-Poupança',
                limite_cheque_especial DECIMAL(15,2),
                taxa_manutencao DECIMAL(5,4),
                taxa_rendimento DECIMAL(5,4),
                data_aniversario DATE,
                FOREIGN KEY (id_cliente) REFERENCES clientes(id)
            );
            
            -- Tabela transacoes
            CREATE TABLE IF NOT EXISTS transacoes (
                id_transacao BIGINT AUTO_INCREMENT PRIMARY KEY,
                data DATETIME NOT NULL,
                valor DECIMAL(15,2) NOT NULL,
                tipo TINYINT NOT NULL COMMENT '1-Crédito, 2-Débito, 3-Transf Crédito, 4-Transf Débito',
                numero_conta_origem VARCHAR(20),
                numero_conta_destino VARCHAR(20),
                FOREIGN KEY (numero_conta_origem) REFERENCES contas(numero_conta),
                FOREIGN KEY (numero_conta_destino) REFERENCES contas(numero_conta)
            );
            
            -- Índices para melhorar performance
            CREATE INDEX idx_conta_cliente ON contas(id_cliente);
            CREATE INDEX idx_transacao_conta_origem ON transacoes(numero_conta_origem);
            CREATE INDEX idx_transacao_conta_destino ON transacoes(numero_conta_destino);
            CREATE INDEX idx_transacao_data ON transacoes(data);
            """;
	}
	
	// Método para criação de um banco de dados automático.
	@Override
    public void criarDB() {
        String urlSemDataBase = "jdbc:mysql://" + ADDRESS + ":" + PORT + "/";
        String sql = getSchemaSQL();
        
        try (Connection conn = DriverManager.getConnection(urlSemDataBase, USERNAME, PASSWORD); 
             Statement stmt = conn.createStatement()) {
            
            // Criar database se não existir
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DATABASE);
            stmt.executeUpdate("USE " + DATABASE);
            
            // Executar comandos SQL para criar tabelas
            for (String comando : sql.split(";")) {
                if (!comando.trim().isEmpty()) {
                    stmt.execute(comando);
                }
            }
            
            System.out.println("Estrutura do banco de dados criada com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar banco ou tabelas: ");
            e.printStackTrace();
        }
    }
    

}
