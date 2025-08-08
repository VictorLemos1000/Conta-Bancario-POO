package projeto.bancario.poo.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuPrincipal extends JFrame {

	private JButton btnClientes, btnContas, btnTransacoes;
    
    public MenuPrincipal() {
        setTitle("Sistema Bancário");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        btnClientes = new JButton("Gerenciar Clientes");
        btnContas = new JButton("Gerenciar Contas");
        btnTransacoes = new JButton("Gerenciar Transações");
        
        btnClientes.addActionListener(e -> new ClienteGUI().setVisible(true));
        btnContas.addActionListener(e -> new ContaGUI().setVisible(true));
        btnTransacoes.addActionListener(e -> new TransacaoGUI().setVisible(true));
        
        panel.add(btnClientes);
        panel.add(btnContas);
        panel.add(btnTransacoes);
        add(panel);
    }
}
