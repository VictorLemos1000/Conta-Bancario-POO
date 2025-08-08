package projeto.bancario.poo.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import projeto.bancario.poo.exception.DataBaseException;
import projeto.bancario.poo.facade.ClienteFacade;
import projeto.bancario.poo.facade.ContaFacade;
import projeto.bancario.poo.model.Cliente;

@SuppressWarnings({"serial", "rawtypes"})
public class AbrirContaCorrenteDialog extends JDialog{

	private JTextField txtNumeroConta, txtLimite;
    private JComboBox<Cliente> cmbClientes;
    private JButton btnSalvar;
    
    public AbrirContaCorrenteDialog(JFrame parent) {
        super(parent, "Abrir Conta Corrente", true);
        setSize(400, 250);
        setLayout(new BorderLayout(10, 10));
        
        // Painel de campos
        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 5, 5));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Componentes
        cmbClientes = new JComboBox<>();
        carregarClientes();
        
        txtNumeroConta = new JTextField();
        txtLimite = new JTextField();
        
        // Adicionando componentes ao painel
        panelCampos.add(new JLabel("Cliente:"));
        panelCampos.add(cmbClientes);
        panelCampos.add(new JLabel("Número da Conta:"));
        panelCampos.add(txtNumeroConta);
        panelCampos.add(new JLabel("Limite (R$):"));
        panelCampos.add(txtLimite);
        
        // Botão Salvar
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarContaCorrente());
        
        // Adicionando ao dialog
        add(panelCampos, BorderLayout.CENTER);
        add(btnSalvar, BorderLayout.SOUTH);
    }
    
    private void carregarClientes() {
        try {
            List<Cliente> clientes = new ClienteFacade().listarClientes();
            DefaultComboBoxModel<Cliente> model = new DefaultComboBoxModel<>();
            clientes.forEach(model::addElement);
            cmbClientes.setModel(model);
        } catch (DataBaseException e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar clientes: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void salvarContaCorrente() {
        try {
            // Validações básicas
            if (txtNumeroConta.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("Número da conta é obrigatório");
            }
            
            if (txtLimite.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("Limite é obrigatório");
            }
            
            // Obter valores
            Cliente cliente = (Cliente) cmbClientes.getSelectedItem();
            String numeroConta = txtNumeroConta.getText();
            BigDecimal limite = new BigDecimal(txtLimite.getText());
            BigDecimal taxa = new BigDecimal("10.00"); // Taxa fixa
            
            // Criar conta
            new ContaFacade().abrirContaCorrente(
                numeroConta,
                cliente,
                limite,
                taxa
            );
            
            JOptionPane.showMessageDialog(this,
                "Conta corrente criada com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Dados inválidos",
                JOptionPane.WARNING_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao criar conta: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}