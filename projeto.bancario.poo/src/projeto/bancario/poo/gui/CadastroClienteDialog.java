package projeto.bancario.poo.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import projeto.bancario.poo.exception.DataBaseException;
import projeto.bancario.poo.facade.ClienteFacade;

@SuppressWarnings("serial")
public class CadastroClienteDialog extends JDialog{

	private JTextField txtNome, txtCpf;
    private JButton btnSalvar;
    
    public CadastroClienteDialog(JFrame parent) {
        super(parent, "Cadastrar Cliente", true);
        setSize(300, 200);
        
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panel.add(txtNome);
        
        panel.add(new JLabel("CPF:"));
        txtCpf = new JTextField();
        panel.add(txtCpf);
        
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            try {
                ClienteFacade facade = new ClienteFacade();
                facade.cadastrarCliente(txtNome.getText(), txtCpf.getText(), LocalDateTime.now());
                JOptionPane.showMessageDialog(this, "Cliente cadastrado!");
                dispose();
            } catch (DataBaseException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        add(panel, BorderLayout.CENTER);
        add(btnSalvar, BorderLayout.SOUTH);
    }
}
