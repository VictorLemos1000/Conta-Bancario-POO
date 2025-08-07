package projeto.bancario.poo.service;

import java.math.BigDecimal;

import projeto.bancario.poo.exception.ContaNaoEncontradaException;
import projeto.bancario.poo.exception.OperacaoBancariaException;
import projeto.bancario.poo.model.Conta;

@SuppressWarnings("rawtypes")
public interface IContaService {

	// Operações básicas
	void sacarQuantia(Conta conta, BigDecimal quantia) throws OperacaoBancariaException, ContaNaoEncontradaException;
    void depositarQuantia(Conta conta, BigDecimal quantia) throws OperacaoBancariaException, ContaNaoEncontradaException;
    void transferirQuantia(Conta origem, Conta destino, BigDecimal valor) throws OperacaoBancariaException, ContaNaoEncontradaException;
    BigDecimal consultarSaldo(Conta conta) throws OperacaoBancariaException, ContaNaoEncontradaException;
}
