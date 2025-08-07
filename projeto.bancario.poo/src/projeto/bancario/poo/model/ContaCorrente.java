package projeto.bancario.poo.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

//  Conta poupança está herdando a classe pai genérica Conta vinculada ao cliente
@SuppressWarnings({ "serial", "rawtypes" })
public abstract class ContaCorrente extends Conta{

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
	
	
}
