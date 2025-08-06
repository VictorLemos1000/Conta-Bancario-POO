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
	private String USERNAME;
	private String PASSWORD;
	private String ADDRESS;
	private String PORT;
	private String DATABASE;
	
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
	
	@Override
	public Connection getConnection(Connection connection) throws DataBaseException {
		// TODO Auto-generated method stub
		try {
			if (connection == null || connection.isClosed()) {
				String url = "jdbc:mysql://%s:%s/%s?useSSL=false&serverTimeZone=UTC".formatted(ADDRESS, PORT, DATABASE);
				
				return DriverManager.getConnection(url, USERNAME, PASSWORD);
			}
			return connection;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new DataBaseException(" Erro ao estabelecer a conexão com o banco de dados: ", e);
		}
	}
	
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		String urlSemDataBase = "jdbc:mysql://%:%s/".formatted(ADDRESS, PORT);
		
		try(Connection connection = DriverManager.getConnection(urlSemDataBase, USERNAME, PASSWORD); Statement stmt = connection.createStatement()) {
			
			String sql = """
	                CREATE DATABASE IF NOT EXISTS %s;
	                USE %s;

	                CREATE TABLE IF NOT EXISTS clientes (
	                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	                    cpf VARCHAR(14) UNIQUE NOT NULL,
	                    nome VARCHAR(255) NOT NULL,
	                    endereco VARCHAR(255),
	                    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP
	                );

	                CREATE TABLE IF NOT EXISTS contas (
	                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	                    numero VARCHAR(20) UNIQUE NOT NULL,
	                    saldo DECIMAL(15,2) NOT NULL DEFAULT 0,
	                    tipo ENUM('CORRENTE', 'POUPANCA') NOT NULL,
	                    cliente_id BIGINT NOT NULL,
	                    data_abertura DATETIME DEFAULT CURRENT_TIMESTAMP,
	                    ativa BOOLEAN DEFAULT TRUE,
	                    CONSTRAINT fk_conta_cliente
	                        FOREIGN KEY (cliente_id)
	                        REFERENCES clientes(id)
	                );

	                CREATE TABLE IF NOT EXISTS transacoes (
	                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	                    tipo ENUM('DEPOSITO', 'SAQUE', 'TRANSFERENCIA', 'RENDIMENTO', 'TARIFA') NOT NULL,
	                    valor DECIMAL(15,2) NOT NULL,
	                    conta_origem BIGINT,
	                    conta_destino BIGINT,
	                    data_transacao DATETIME DEFAULT CURRENT_TIMESTAMP,
	                    descricao VARCHAR(255),
	                    CONSTRAINT fk_transacao_conta_origem
	                        FOREIGN KEY (conta_origem)
	                        REFERENCES contas(id),
	                    CONSTRAINT fk_transacao_conta_destino
	                        FOREIGN KEY (conta_destino)
	                        REFERENCES contas(id)
	                );

	                CREATE TABLE IF NOT EXISTS contas_correntes (
	                    conta_id BIGINT PRIMARY KEY,
	                    limite_cheque_especial DECIMAL(15,2) NOT NULL DEFAULT 0,
	                    taxa_manutencao DECIMAL(15,2) NOT NULL,
	                    ultima_cobranca DATE,
	                    CONSTRAINT fk_conta_corrente
	                        FOREIGN KEY (conta_id)
	                        REFERENCES contas(id)
	                );

	                CREATE TABLE IF NOT EXISTS contas_poupancas (
	                    conta_id BIGINT PRIMARY KEY,
	                    taxa_rendimento DECIMAL(5,4) NOT NULL,
	                    data_aniversario DATE NOT NULL,
	                    CONSTRAINT fk_conta_poupanca
	                        FOREIGN KEY (conta_id)
	                        REFERENCES contas(id)
	                );
	                """.formatted(DATABASE, DATABASE);
			
			for (String comando : sql.split(";")) {
				if (!comando.trim().isEmpty()) {
					stmt.execute(comando);
				}
			}
			
			System.out.println(" Estrutura do banco de dados criada com sucesso.");
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println(" Erro ao criar banco ou tabelas: ");
			e.printStackTrace();
		}
	}

}
