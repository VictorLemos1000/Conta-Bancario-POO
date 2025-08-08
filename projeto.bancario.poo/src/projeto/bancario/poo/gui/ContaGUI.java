package projeto.bancario.poo.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ContaGUI extends JFrame {

	private JComboBox<String> cmbTipoConta;
    private JButton btnAbrirConta;
    
    public ContaGUI() {
        setTitle("Contas");
        setSize(400, 200);
        
        cmbTipoConta = new JComboBox<>(new String[]{"Conta Corrente", "Conta Poupança"});
        btnAbrirConta = new JButton("Abrir Conta");
        
        btnAbrirConta.addActionListener(e -> {
            if (cmbTipoConta.getSelectedIndex() == 0) {
                new AbrirContaCorrenteDialog(this).setVisible(true);
            } else {
                new AbrirContaPoupancaDialog(this).setVisible(true);
            }
        });
        
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(cmbTipoConta);
        panel.add(btnAbrirConta);
        add(panel);
    }
}