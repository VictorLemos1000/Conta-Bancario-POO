package projeto.bancario.poo.app;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import projeto.bancario.poo.database.DataBaseConnectionMySQL;
import projeto.bancario.poo.gui.MenuPrincipal;

// Classe onde aplicação será composta
@SuppressWarnings("unused")
public class Aplicacao {

	public static void main(String[] args) {
		
		DataBaseConnectionMySQL database = new DataBaseConnectionMySQL();
		
		Connection connection = database.getConnection();
		
		try {
            SwingUtilities.invokeLater(() -> {
                new MenuPrincipal(); // Remove o parâmetro connection
            });
        } catch (Exception e) {
            System.err.println("Falha ao conectar ao banco: " + e.getMessage());
            JOptionPane.showMessageDialog(null, 
                "Não foi possível conectar ao banco de dados:\n" + e.getMessage(),
                "Erro de Conexão", 
                JOptionPane.ERROR_MESSAGE);
        }
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
		    try {
		        if (connection != null && !connection.isClosed()) {
		            connection.close();
		        }
		    } catch (SQLException e) {
		        System.err.println("Erro ao fechar conexão: " + e.getMessage());
		    }
		}));
    }
}