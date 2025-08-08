package projeto.bancario.poo.facade;

import java.time.LocalDateTime;
import java.util.List;

import projeto.bancario.poo.dao.ClienteDAO;
import projeto.bancario.poo.exception.DataBaseException;
import projeto.bancario.poo.model.Cliente;

@SuppressWarnings("rawtypes")
public class ClienteFacade {

	private final ClienteDAO clienteDAO;

    public ClienteFacade() {
        this.clienteDAO = new ClienteDAO();
    }

    // Cadastra um novo cliente
	public void cadastrarCliente(String nome, String cpf, LocalDateTime dataNascimento) throws DataBaseException {
        Cliente cliente = new Cliente(nome, cpf, dataNascimento);
        clienteDAO.save(cliente);
    }

    // Busca cliente por CPF
    public Cliente buscarClientePorCpf(String cpf) throws DataBaseException {
        return clienteDAO.findByCpf(cpf);
    }

    // Lista todos os clientes
    public List<Cliente> listarClientes() throws DataBaseException {
        return clienteDAO.findAll();
    }
    
    public void atualizarCliente(Cliente cliente) throws DataBaseException {
        clienteDAO.update(cliente);
    }
    
}
