package projeto.bancario.poo.gui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import projeto.bancario.poo.exception.DataBaseException;
import projeto.bancario.poo.facade.ClienteFacade;
import projeto.bancario.poo.model.Cliente;

@SuppressWarnings("rawtypes")
public class ClienteTableModel implements TableModel {

	private List<Cliente> clientes;
    private final String[] colunas = {"ID", "Nome", "CPF", "Data Nascimento"};
    private List<TableModelListener> listeners = new ArrayList<>();

    public ClienteTableModel(List<Cliente> clientes) {
        this.clientes = clientes != null ? clientes : new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return clientes.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:  // ID
                return Long.class;
            case 1:  // Nome
                return String.class;
            case 2:  // CPF
                return String.class;
            case 3:  // Data Nascimento
                return String.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; // Tabela somente leitura
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente cliente = clientes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return cliente.getId();
            case 1:
                return cliente.getNome();
            case 2:
                return cliente.getCpf();
            case 3:
                return cliente.getDataNascimento() != null ? 
                       cliente.getDataNascimento().toString() : "";
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex >= clientes.size() || columnIndex >= colunas.length) {
            return;
        }

        Cliente cliente = clientes.get(rowIndex);
        ClienteFacade facade = new ClienteFacade();

        try {
            switch (columnIndex) {
                case 1: // Nome
                    String novoNome = (String) aValue;
                    if (novoNome == null || novoNome.trim().isEmpty()) {
                        throw new IllegalArgumentException("Nome não pode ser vazio");
                    }
                    cliente.setNome(novoNome);
                    break;
                    
                case 2: // CPF
                    String novoCpf = (String) aValue;
                    if (!validarCPF(novoCpf)) {
                        throw new IllegalArgumentException("CPF inválido");
                    }
                    cliente.setCpf(novoCpf);
                    break;
                    
                case 3: // Data Nascimento
                    // Assumindo que aValue é uma String no formato "yyyy-MM-dd"
                    LocalDateTime novaData = LocalDateTime.parse((String) aValue);
                    cliente.setDataNascimento(novaData);
                    break;
                    
                default:
                    return; // ID não é editável
            }

            // Atualiza no banco de dados
            facade.atualizarCliente(cliente);
            
            // Notifica os listeners da mudança
            fireTableRowsUpdated(rowIndex, rowIndex);
            
        } catch (DataBaseException e) {
            JOptionPane.showMessageDialog(null,
                "Erro ao atualizar cliente: " + e.getMessage(),
                "Erro no Banco de Dados",
                JOptionPane.ERROR_MESSAGE);
            // Reverte a alteração na lista local
            try {
				atualizarDados(facade.listarClientes());
			} catch (DataBaseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null,
                e.getMessage(),
                "Dado Inválido",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método auxiliar para validar CPF
    private boolean validarCPF(String cpf) {
        // Implementação básica - pode ser aprimorada
        return cpf != null && cpf.matches("\\d{11}");
    }

    // Método para notificar listeners
    private void fireTableRowsUpdated(int firstRow, int lastRow) {
        for (TableModelListener listener : listeners) {
            listener.tableChanged(new TableModelEvent(this, firstRow, lastRow));
        }
    }


    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    // Método para atualizar os dados
    public void atualizarDados(List<Cliente> novosClientes) {
        this.clientes = novosClientes;
        // Notifica os listeners que os dados mudaram
        listeners.forEach(l -> l.tableChanged(null));
    }
}
