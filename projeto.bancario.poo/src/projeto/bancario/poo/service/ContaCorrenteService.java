package projeto.bancario.poo.service;

import java.math.BigDecimal;

import projeto.bancario.poo.exception.ContaNaoEncontradaException;
import projeto.bancario.poo.exception.OperacaoBancariaException;
import projeto.bancario.poo.model.Conta;
import projeto.bancario.poo.model.ContaCorrente;

@SuppressWarnings("rawtypes")
public class ContaCorrenteService implements IContaService {
	
	@Override
	public BigDecimal consultarSaldo(Conta conta) throws OperacaoBancariaException, ContaNaoEncontradaException {
		// TODO Auto-generated method stub
        return conta.getSaldo();
	}

	public void cobrarTaxaManutencao(ContaCorrente conta) {
        if (conta.isStatus()) {
            BigDecimal taxa = conta.getTaxaManutencao();
            conta.setSaldo(conta.getSaldo().subtract(taxa));
        }
    }
}
