package projeto.bancario.poo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

import projeto.bancario.poo.exception.ContaNaoEncontradaException;
import projeto.bancario.poo.exception.OperacaoBancariaException;
import projeto.bancario.poo.model.Conta;
import projeto.bancario.poo.model.ContaPoupanca;

@SuppressWarnings("rawtypes")
public class ContaPoupancaService implements IContaService {

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
