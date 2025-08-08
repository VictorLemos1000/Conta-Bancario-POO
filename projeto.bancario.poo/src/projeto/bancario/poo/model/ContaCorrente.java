package projeto.bancario.poo.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import projeto.bancario.poo.exception.ContaInativaException;
import projeto.bancario.poo.exception.ContaNaoEncontradaException;
import projeto.bancario.poo.exception.OperacaoBancariaException;
import projeto.bancario.poo.exception.QuantiaInvalidaException;
import projeto.bancario.poo.exception.SaldoInsuficienteException;
import projeto.bancario.poo.service.IContaService;

//  Conta poupança está herdando a classe pai genérica Conta vinculada ao cliente
@SuppressWarnings({ "serial", "rawtypes" })
public class ContaCorrente extends Conta{

	private BigDecimal limiteChequeEspecial;
	private BigDecimal taxaManutencao;
	
	// Método implícito.
	public ContaCorrente() {
		
	}
	
	// Método explícito.
	public ContaCorrente(String numeroConta, BigDecimal saldo, LocalDate dataAbertura, boolean status, Cliente cliente, List<Transacao> transacoes, BigDecimal limiteChequeEspecial, BigDecimal taxaManutencao) {
		super(numeroConta, cliente);
		
		this.limiteChequeEspecial = limiteChequeEspecial.setScale(2, RoundingMode.HALF_EVEN);
		this.taxaManutencao = taxaManutencao.setScale(2, RoundingMode.HALF_EVEN);
	}

	// Geters e Seters
	public BigDecimal getLimiteChequeEspecial() {
		return limiteChequeEspecial;
	}

	public void setLimiteChequeEspecial(BigDecimal limiteChequeEspecial) {
		this.limiteChequeEspecial = limiteChequeEspecial;
	}

	public BigDecimal getTaxaManutencao() {
		return taxaManutencao;
	}

	public void setTaxaManutencao(BigDecimal taxaManutencao) {
		this.taxaManutencao = taxaManutencao;
	}

	//
	public void setCliente(Cliente<?> cliente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		return "ContaCorrente [limiteChequeEspecial=" + limiteChequeEspecial + ", taxaManutencao=" + taxaManutencao
				+ ", getLimiteChequeEspecial()=" + getLimiteChequeEspecial() + ", getTaxaManutencao()="
				+ getTaxaManutencao() + "]";
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
