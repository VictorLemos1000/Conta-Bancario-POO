package projeto.bancario.poo.facade;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import projeto.bancario.poo.dao.ContaDAO;
import projeto.bancario.poo.exception.DataBaseException;
import projeto.bancario.poo.model.Cliente;
import projeto.bancario.poo.model.Conta;
import projeto.bancario.poo.model.ContaCorrente;
import projeto.bancario.poo.model.ContaPoupanca;

public class ContaFacade {

	private final ContaDAO contaDAO;

    public ContaFacade() {
        this.contaDAO = new ContaDAO();
    }

    // Abre uma nova conta (genérica)
    public void abrirContaCorrente(String numeroConta, Cliente cliente, BigDecimal limiteChequeEspecial, BigDecimal taxaManutencao) 
    	    throws DataBaseException {
    	    
    	    ContaCorrente conta = new ContaCorrente(numeroConta, BigDecimal.ZERO, LocalDate.now(), true, cliente, new ArrayList<>(), limiteChequeEspecial, taxaManutencao);
    	    contaDAO.save(conta);
    	}

    // Abre conta POUPANÇA
    public void abrirContaPoupanca(String numeroConta, Cliente cliente, BigDecimal taxaRendimento) throws DataBaseException {
    	    
    	    ContaPoupanca conta = new ContaPoupanca(numeroConta, BigDecimal.ZERO, taxaRendimento);
    	    conta.setCliente(cliente);     // Associa o cliente
    	    contaDAO.save(conta);
    	}

    // Consulta saldo
    public BigDecimal consultarSaldo(String numeroConta) throws DataBaseException {
        Conta conta = contaDAO.findByNumeroConta(numeroConta);
        return conta.getSaldo();
    }
    
    public Conta buscarContaPorCliente(Long idCliente) throws DataBaseException {
        return contaDAO.findById(idCliente); // Implemente isso no DAO!
    }
    
    public List<Conta> findAll() throws DataBaseException {
        return contaDAO.findAll(); // Assume que ContaDAO já tem esse método
    }
}
