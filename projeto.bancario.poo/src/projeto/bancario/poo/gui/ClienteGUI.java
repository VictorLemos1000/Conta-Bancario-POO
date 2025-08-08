package projeto.bancario.poo.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class ClienteGUI extends JFrame{

	private JTable tabelaClientes;
    private JButton btnCadastrar;
    
    public ClienteGUI() {
        setTitle("Clientes");
        setSize(600, 400);
        
        // Tabela com dados dos clientes (usando ClienteFacade)
        tabelaClientes = new JTable(new ClienteTableModel(null));
        btnCadastrar = new JButton("Cadastrar Cliente");
        
        btnCadastrar.addActionListener(e -> {
            CadastroClienteDialog dialog = new CadastroClienteDialog(this);
            dialog.setVisible(true);
        });
        
        add(new JScrollPane(tabelaClientes), BorderLayout.CENTER);
        add(btnCadastrar, BorderLayout.SOUTH);
    }
}
