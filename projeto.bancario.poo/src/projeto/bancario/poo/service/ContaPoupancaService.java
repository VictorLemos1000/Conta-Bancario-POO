package projeto.bancario.poo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

import projeto.bancario.poo.exception.ContaInativaException;
import projeto.bancario.poo.exception.ContaNaoEncontradaException;
import projeto.bancario.poo.exception.OperacaoBancariaException;
import projeto.bancario.poo.exception.QuantiaInvalidaException;
import projeto.bancario.poo.exception.SaldoInsuficienteException;
import projeto.bancario.poo.model.Conta;
import projeto.bancario.poo.model.ContaCorrente;
import projeto.bancario.poo.model.ContaPoupanca;

@SuppressWarnings("rawtypes")
public class ContaPoupancaService implements IContaService{

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

	public void aplicarRendimentos(ContaPoupanca conta) {
        if (!conta.isStatus()) {
        	return;
        }
        
        LocalDate hoje = LocalDate.now();
        
        if (hoje.isAfter(conta.getDataAniversario())) {
            Period periodo = Period.between(conta.getDataAniversario(), hoje);
            int meses = periodo.getYears() * 12 + periodo.getMonths();
            
            if (meses > 0) {
            	// Taxa de  0.5% ao mês ou seja de 5% ao ano.
                BigDecimal taxaMensal = new BigDecimal("0.005");
                BigDecimal saldo = conta.getSaldo();
                
                for (int i = 0; i < meses; i++) {
                    BigDecimal rendimento = saldo.multiply(taxaMensal).setScale(2, RoundingMode.HALF_EVEN);
                    saldo = saldo.add(rendimento);
                }
                
                conta.setSaldo(saldo);
                conta.setDataAniversario(hoje);
            }
        }
    }
}
