package projeto.bancario.poo.model;

import java.math.BigDecimal;

import projeto.bancario.poo.exception.ContaNaoEncontradaException;
import projeto.bancario.poo.exception.OperacaoBancariaException;

public interface IConta {

	BigDecimal getSaldo();
	
	String getNumero();
	
	boolean isAtiva();
	
	void usarChequeEspecial(String numeroConta, BigDecimal valor) throws OperacaoBancariaException, ContaNaoEncontradaException;
	    
	void cobrarTaxaManutencao(String numeroConta) throws OperacaoBancariaException, ContaNaoEncontradaException;
	    
	BigDecimal consultarLimiteChequeEspecial(String numeroConta) throws ContaNaoEncontradaException;
}
