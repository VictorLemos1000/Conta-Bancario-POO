package projeto.bancario.poo.model;

import java.math.BigDecimal;

import projeto.bancario.poo.exception.OperacaoBancariaException;

public interface IConta {

	void depositar(BigDecimal quantia) throws OperacaoBancariaException;
	
	void sacar(BigDecimal quantia) throws OperacaoBancariaException;
	
	void transferir(IConta contaDestino, BigDecimal quantia) throws OperacaoBancariaException;
	
	BigDecimal getSaldo();
	
	String getNumero();
	
	boolean isAtiva();
}
