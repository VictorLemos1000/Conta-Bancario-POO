package projeto.bancario.poo.service;

import java.math.BigDecimal;

import projeto.bancario.poo.exception.ContaNaoEncontradaException;
import projeto.bancario.poo.exception.OperacaoBancariaException;
import projeto.bancario.poo.model.Conta;

@SuppressWarnings("rawtypes")
public interface IContaService {

	// Operações básicas
    BigDecimal consultarSaldo(Conta conta) throws OperacaoBancariaException, ContaNaoEncontradaException;
}
