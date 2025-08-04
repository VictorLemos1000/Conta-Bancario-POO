package projeto.bancario.poo.model;

import java.math.BigDecimal;

import projeto.bancario.poo.exception.ContaNaoEncontradaException;
import projeto.bancario.poo.exception.OperacaoBancariaException;

public interface IConta {

	void depositar(BigDecimal quantia) throws OperacaoBancariaException;
	
	void sacar(BigDecimal quantia) throws OperacaoBancariaException;
	
	void transferir(IConta contaDestino, BigDecimal quantia) throws OperacaoBancariaException;
	
	BigDecimal getSaldo();
	
	String getNumero();
	
	boolean isAtiva();
	
	void usarChequeEspecial(String numeroConta, BigDecimal valor) throws OperacaoBancariaException, ContaNaoEncontradaException;
	    
	void cobrarTaxaManutencao(String numeroConta) throws OperacaoBancariaException, ContaNaoEncontradaException;
	    
	BigDecimal consultarLimiteChequeEspecial(String numeroConta) throws ContaNaoEncontradaException;
}
