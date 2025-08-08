package projeto.bancario.poo.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TransacaoGUI extends JFrame{

	private JButton btnTransferir;
    
    public TransacaoGUI() {
        setTitle("Transações");
        setSize(500, 300);
        
        btnTransferir = new JButton("Realizar Transferência");
        btnTransferir.addActionListener(e -> new TransferenciaDialog(this).setVisible(true));
        
        add(btnTransferir, BorderLayout.CENTER);
    }
}
