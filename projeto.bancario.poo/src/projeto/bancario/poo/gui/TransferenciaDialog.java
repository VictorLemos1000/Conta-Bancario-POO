package projeto.bancario.poo.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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
import projeto.bancario.poo.facade.BancarioFacade;
import projeto.bancario.poo.facade.ContaFacade;
import projeto.bancario.poo.model.Cliente;
import projeto.bancario.poo.model.Conta;

@SuppressWarnings({"serial", "rawtypes"})
public class TransferenciaDialog extends JDialog{

	private final JComboBox<Conta> cmbOrigem = new JComboBox<>();
    private final JComboBox<Conta> cmbDestino = new JComboBox<>();
    private final JTextField txtValor = new JTextField(10);

    public TransferenciaDialog(JFrame parent) {
        super(parent, "Transferência Bancária", true);
        setupUI();
        carregarContas();
    }

    private void setupUI() {
        setSize(400, 250);
        setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        formPanel.add(new JLabel("Conta Origem:"));
        formPanel.add(cmbOrigem);
        formPanel.add(new JLabel("Conta Destino:"));
        formPanel.add(cmbDestino);
        formPanel.add(new JLabel("Valor (R$):"));
        formPanel.add(txtValor);
        
        JButton btnTransferir = new JButton("Transferir");
        btnTransferir.addActionListener(this::realizarTransferencia);
        
        add(formPanel, BorderLayout.CENTER);
        add(btnTransferir, BorderLayout.SOUTH);
    }

    private void carregarContas() {
        try {
            ContaFacade facade = new ContaFacade();
            List<Conta> contas = facade.findAll();
            
            // Criar modelos independentes
            DefaultComboBoxModel<Conta> modelOrigem = new DefaultComboBoxModel<>();
            DefaultComboBoxModel<Conta> modelDestino = new DefaultComboBoxModel<>();
            
            // Preencher ambos os modelos com as mesmas contas
            for (Conta conta : contas) {
                modelOrigem.addElement(conta);
                modelDestino.addElement(conta);
            }
            
            // Aplicar os modelos
            cmbOrigem.setModel(modelOrigem);
            cmbDestino.setModel(modelDestino);
            
        } catch (DataBaseException e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar contas: " + e.getMessage(),
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarTransferencia(ActionEvent evt) {
        try {
            validarCampos();
            
            Conta origem = (Conta) cmbOrigem.getSelectedItem();
            Conta destino = (Conta) cmbDestino.getSelectedItem();
            BigDecimal valor = new BigDecimal(txtValor.getText());
            
            new BancarioFacade().transferir(
                getCpfDaConta(origem),
                getCpfDaConta(destino),
                valor
            );
            
            JOptionPane.showMessageDialog(this, "Transferência realizada com sucesso!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro: " + e.getMessage(),
                "Erro na Transferência",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getCpfDaConta(Conta conta) throws Exception {
        Objects.requireNonNull(conta, "Conta não pode ser nula");
        
        // Implementação para relação 1:1
        Cliente cliente = (Cliente) conta.getCliente();
        if (cliente == null) {
            throw new Exception("Conta não possui cliente associado");
        }
        
        return cliente.getCpf();
        
        /* 
        // Implementação para relação 1:N
        List<Cliente> clientes = conta.getCliente();
        if (clientes == null || clientes.isEmpty()) {
            throw new Exception("Conta não possui clientes associados");
        }
        return clientes.get(0).getCpf();
        */
    }

    private void validarCampos() throws Exception {
        if (cmbOrigem.getSelectedItem() == null || 
            cmbDestino.getSelectedItem() == null) {
            throw new Exception("Selecione contas de origem e destino");
        }
        
        if (txtValor.getText().trim().isEmpty()) {
            throw new Exception("Informe o valor da transferência");
        }
        
        try {
            BigDecimal valor = new BigDecimal(txtValor.getText());
            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                throw new Exception("Valor deve ser positivo");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Valor inválido");
        }
    }
}
