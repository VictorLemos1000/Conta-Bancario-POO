package projeto.bancario.poo.app;

import java.sql.Connection;

import javax.swing.SwingUtilities;

import projeto.bancario.poo.database.DataBaseConnectionMySQL;
import projeto.bancario.poo.gui.MenuPrincipal;

// Classe onde aplicação será composta
@SuppressWarnings("unused")
public class Aplicacao {

	public static void main(String[] args) {
		
		DataBaseConnectionMySQL database = new DataBaseConnectionMySQL();
		
		Connection connection = database.getConnection();
		
		SwingUtilities.invokeLater(() -> {
            new MenuPrincipal();
        });
    }
}