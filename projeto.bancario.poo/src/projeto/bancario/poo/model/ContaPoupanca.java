package projeto.bancario.poo.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import projeto.bancario.poo.exception.ContaInativaException;
import projeto.bancario.poo.exception.ContaNaoEncontradaException;
import projeto.bancario.poo.exception.OperacaoBancariaException;
import projeto.bancario.poo.exception.QuantiaInvalidaException;
import projeto.bancario.poo.exception.SaldoInsuficienteException;
import projeto.bancario.poo.service.IContaService;

@SuppressWarnings({ "rawtypes", "serial" })
public class ContaPoupanca extends Conta {

	private BigDecimal taxaRendimento;
	private LocalDate dataAniversario;
	
	public ContaPoupanca(String numeroConta, BigDecimal saldoInicial, BigDecimal taxaRendimento) {
		saldoInicial = saldoInicial.setScale(2, RoundingMode.HALF_EVEN);	
		
		/*
		 * O atributo taxa de rendimento está formatado para atribuir 4 casas decimais e
		 * realizando um arredodamento por HALF_EVEN, conhecido como arredondamento bancário,
		 * mas sendo um pouco mais específico ele arredonda para um valor par mais próximo.
		 */
		this.taxaRendimento = taxaRendimento.setScale(4, RoundingMode.HALF_EVEN);
		this.dataAniversario = LocalDate.now();
	}

	public LocalDate getDataAniversario() {
		return dataAniversario;
	}

	public void setDataAniversario(LocalDate dataAniversario) {
		this.dataAniversario = dataAniversario;
	}

	public BigDecimal getTaxaRendimento() {
		return taxaRendimento;
	}

	public void setTaxaRendimento(BigDecimal taxaRendimento) {
		this.taxaRendimento = taxaRendimento;
	}

	public void setCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sacarQuantia(Conta conta, BigDecimal quantia) throws OperacaoBancariaException, ContaNaoEncontradaException {
		// TODO Auto-generated method stub
		
		if (!conta.isStatus()) {
            throw new ContaInativaException(" Operação não permitida: conta inativa.");
        }
		
        if (quantia.compareTo(BigDecimal.ZERO) <= 0) {
            throw new QuantiaInvalidaException(" Valor de saque deve ser positivo.");
        }
        
        BigDecimal saldoDisponivel = conta.getSaldo().add(((ContaCorrente) conta).getLimiteChequeEspecial());
        
        if (quantia.compareTo(saldoDisponivel) > 0) {
            throw new SaldoInsuficienteException(" Saldo insuficiente para realizar o saque.");
        }
        
        conta.setSaldo(conta.getSaldo().subtract(quantia));
	}

	@Override
	public void depositarQuantia(Conta conta, BigDecimal quantia) throws OperacaoBancariaException, ContaNaoEncontradaException {
		// TODO Auto-generated method stub
		if (!conta.isStatus()) {
            throw new ContaInativaException(" Operação não permitida: conta inativa.");
        }
        if (quantia.compareTo(BigDecimal.ZERO) <= 0) {
            throw new QuantiaInvalidaException(" Valor de depósito deve ser positivo.");
        }
        
        conta.setSaldo(conta.getSaldo().add(quantia));
	}

	@Override
	public void transferirQuantia(Conta origem, Conta destino, BigDecimal quantia) throws OperacaoBancariaException, ContaNaoEncontradaException {
		// TODO Auto-generated method stub
		if (!origem.isStatus()) {
            throw new ContaInativaException(" Conta de origem inativa.");
        }
        if (destino != null && !destino.isStatus()) {
            throw new ContaInativaException(" Conta de destino inativa.");
        }
        
        this.sacarQuantia(destino, quantia);
        
        if (destino.getService() instanceof IContaService) {
            ((ContaCorrente) destino.getService()).depositarQuantia(destino, quantia);
        }
	}
}
