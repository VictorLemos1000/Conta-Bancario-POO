package projeto.bancario.poo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	// Método para criação de um banco de dados automático.
	@Override
	public void criarDB() {
	    String urlSemDataBase = "jdbc:mysql://%s:%s/".formatted(ADDRESS, PORT);
	    
	    try(Connection connection = DriverManager.getConnection(urlSemDataBase, USERNAME, PASSWORD); 
	        Statement stmt = connection.createStatement()) {
	        
	        String sql = """
	                -- Criação do banco de dados
	                CREATE DATABASE IF NOT EXISTS %s;
	                
	                USE %s;
	                
	                -- Tabela clientes
	                CREATE TABLE IF NOT EXISTS clientes (
	                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	                    cpf VARCHAR(11) UNIQUE NOT NULL,
	                    nome VARCHAR(100) NOT NULL,
	                    data_nascimento DATE NOT NULL,
	                    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP
	                );
	                
	                -- Tabela contas (modelo único para ambos os tipos)
	                CREATE TABLE IF NOT EXISTS contas (
	                    numero_conta BIGINT PRIMARY KEY,
	                    saldo DECIMAL(15,2) NOT NULL DEFAULT 0.00,
	                    data_abertura DATE NOT NULL,
	                    status BOOLEAN NOT NULL DEFAULT TRUE,
	                    id_cliente BIGINT NOT NULL,
	                    tipo_conta TINYINT NOT NULL COMMENT '1-CORRENTE, 2-POUPANCA',
	                    limite_cheque_especial DECIMAL(15,2) NULL,
	                    taxa_manutencao DECIMAL(5,4) NULL,
	                    taxa_rendimento DECIMAL(5,4) NULL,
	                    data_aniversario DATE NULL,
	                    CONSTRAINT fk_conta_cliente FOREIGN KEY (id_cliente) REFERENCES clientes(id),
	                    CONSTRAINT chk_tipo_conta CHECK (tipo_conta IN (1, 2))
	                );
	                
	                -- Tabela transacoes
	                CREATE TABLE IF NOT EXISTS transacoes (
	                    id_transacao BIGINT AUTO_INCREMENT PRIMARY KEY,
	                    data DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	                    valor DECIMAL(15,2) NOT NULL,
	                    tipo TINYINT NOT NULL COMMENT '1-CREDITO, 2-DEBITO, 3-TRANSACAO_CREDITO, 4-TRANSACAO_DEBITO',
	                    numero_conta_origem BIGINT NULL,
	                    numero_conta_destino BIGINT NULL,
	                    descricao VARCHAR(255) NULL,
	                    CONSTRAINT fk_transacao_conta_origem FOREIGN KEY (numero_conta_origem) REFERENCES contas(numero_conta),
	                    CONSTRAINT fk_transacao_conta_destino FOREIGN KEY (numero_conta_destino) REFERENCES contas(numero_conta),
	                    CONSTRAINT chk_tipo_transacao CHECK (tipo IN (1, 2, 3, 4))
	                );
	                
	                -- Criação de índices para melhor performance
	                CREATE INDEX IF NOT EXISTS idx_cliente_cpf ON clientes(cpf);
	                CREATE INDEX IF NOT EXISTS idx_conta_cliente ON contas(id_cliente);
	                CREATE INDEX IF NOT EXISTS idx_transacao_contas ON transacoes(numero_conta_origem, numero_conta_destino);
	                CREATE INDEX IF NOT EXISTS idx_transacao_data ON transacoes(data);
	                """.formatted(DATABASE, DATABASE);
	        
	        // Executa cada comando SQL separadamente
	        for (String comando : sql.split(";")) {
	            if (!comando.trim().isEmpty()) {
	                stmt.execute(comando);
	            }
	        }
	        
	        System.out.println("Estrutura do banco de dados criada com sucesso.");
	    } catch (SQLException e) {
	        System.err.println("Erro ao criar banco ou tabelas: " + e.getMessage());
	        e.printStackTrace();
	        throw new RuntimeException("Falha na criação do banco de dados", e);
	    }
	}
}
