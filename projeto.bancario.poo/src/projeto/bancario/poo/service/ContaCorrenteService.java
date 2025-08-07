package projeto.bancario.poo.service;

import java.math.BigDecimal;

import projeto.bancario.poo.exception.ContaInativaException;
import projeto.bancario.poo.exception.ContaNaoEncontradaException;
import projeto.bancario.poo.exception.OperacaoBancariaException;
import projeto.bancario.poo.exception.QuantiaInvalidaException;
import projeto.bancario.poo.exception.SaldoInsuficienteException;
import projeto.bancario.poo.model.Conta;
import projeto.bancario.poo.model.ContaCorrente;

@SuppressWarnings("rawtypes")
public class ContaCorrenteService  implements IContaService {

	@Override
	public void sacarQuantia(Conta conta, BigDecimal quantia) throws OperacaoBancariaException, ContaNaoEncontradaException {
		// TODO Auto-generated method stub
		if (!conta.isStatus()) {
            throw new ContaInativaException("Operação não permitida: conta inativa");
        }
        if (quantia.compareTo(BigDecimal.ZERO) <= 0) {
            throw new QuantiaInvalidaException("Valor de saque deve ser positivo");
        }
        
        BigDecimal saldoDisponivel = conta.getSaldo().add(((ContaCorrente) conta).getLimiteChequeEspecial());
        
        if (quantia.compareTo(saldoDisponivel) > 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o saque");
        }
        
        conta.setSaldo(conta.getSaldo().subtract(quantia));
	}

	@Override
	public void depositarQuantia(Conta conta, BigDecimal quantia) throws OperacaoBancariaException, ContaNaoEncontradaException {
		// TODO Auto-generated method stub
		if (!conta.isStatus()) {
            throw new ContaInativaException("Operação não permitida: conta inativa");
        }
        if (quantia.compareTo(BigDecimal.ZERO) <= 0) {
            throw new QuantiaInvalidaException("Valor de depósito deve ser positivo");
        }
        
        conta.setSaldo(conta.getSaldo().add(quantia));
	}

	@Override
	public void transferirQuantia(Conta origem, Conta destino, BigDecimal valor) throws OperacaoBancariaException, ContaNaoEncontradaException {
		// TODO Auto-generated method stub
		if (!origem.isStatus()) {
            throw new ContaInativaException("Conta origem inativa");
        }
        if (destino != null && !destino.isStatus()) {
            throw new ContaInativaException("Conta destino inativa");
        }
        
        this.sacarQuantia(destino, valor);
        ((ContaCorrenteService) destino.getService()).depositarQuantia(destino, valor);
	}

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
