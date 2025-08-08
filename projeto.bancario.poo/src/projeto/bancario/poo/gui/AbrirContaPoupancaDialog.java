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
public class AbrirContaPoupancaDialog extends JDialog{

	private JTextField txtNumeroConta, txtTaxaRendimento;
	private JComboBox<Cliente> cmbClientes;
    private JButton btnSalvar;
    
    public AbrirContaPoupancaDialog(JFrame parent) {
        super(parent, "Abrir Conta Poupança", true);
        setSize(400, 250);
        setLayout(new BorderLayout(10, 10));
        
        // Painel de campos
        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 5, 5));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Componentes
        cmbClientes = new JComboBox<>();
        carregarClientes();
        
        txtNumeroConta = new JTextField();
        txtTaxaRendimento = new JTextField();
        
        // Adicionando componentes ao painel
        panelCampos.add(new JLabel("Cliente:"));
        panelCampos.add(cmbClientes);
        panelCampos.add(new JLabel("Número da Conta:"));
        panelCampos.add(txtNumeroConta);
        panelCampos.add(new JLabel("Taxa de Rendimento (%):"));
        panelCampos.add(txtTaxaRendimento);
        
        // Botão Salvar
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarContaPoupanca());
        
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
    
    private void salvarContaPoupanca() {
        try {
            // Validações básicas
            if (txtNumeroConta.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("Número da conta é obrigatório");
            }
            
            if (txtTaxaRendimento.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("Taxa de rendimento é obrigatória");
            }
            
            // Obter valores
            Cliente cliente = (Cliente) cmbClientes.getSelectedItem();
            String numeroConta = txtNumeroConta.getText();
            BigDecimal taxaRendimento = new BigDecimal(txtTaxaRendimento.getText());
            
            // Criar conta (saldo inicia com ZERO automaticamente)
            new ContaFacade().abrirContaPoupanca(
                numeroConta,
                cliente,
                taxaRendimento
            );
            
            JOptionPane.showMessageDialog(this,
                "Conta poupança criada com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "A taxa de rendimento deve ser um valor numérico",
                "Dados inválidos",
                JOptionPane.WARNING_MESSAGE);
                
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
