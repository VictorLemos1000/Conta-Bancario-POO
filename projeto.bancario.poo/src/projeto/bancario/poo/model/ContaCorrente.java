package projeto.bancario.poo.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.function.Function;

import projeto.bancario.poo.exception.SaldoInsuficienteException;

//  Conta poupança está herdando a classe pai genérica Conta vinculada ao cliente
@SuppressWarnings({ "serial", "rawtypes" })
public class ContaCorrente extends Conta{

	private long id;
	private BigDecimal limiteChequeEspecial;
	private BigDecimal taxaDeManutencao;
	private LocalDate ultimaCobrancaDeManutencao;
	
	// Método implícito.
	public ContaCorrente() {
		
	}
	
	// Método explícito.
	@SuppressWarnings("unchecked")
	public ContaCorrente(String numeroConta, BigDecimal saldoInicial, BigDecimal limiteChequeEspecial, BigDecimal taxaDeManutencao) {
		super();
		this.numeroConta = numeroConta;
		saldoInicial = saldoInicial;
		this.limiteChequeEspecial = limiteChequeEspecial.setScale(2, RoundingMode.HALF_EVEN);
		this.taxaDeManutencao = taxaDeManutencao.setScale(2, RoundingMode.HALF_EVEN);
		this.ultimaCobrancaDeManutencao = LocalDate.now();
	}
	
	// Método auxíliar
	private void registroOperacional(String descricao, BigDecimal valor) {
		System.out.printf("\n Operação: %s | Quantia: R$%.2f%n", descricao, valor);
	}
	
	/*
	 * OBS: a classe Function ela é uma interface funcional que representa;
	 * tipos genéricos do "T" para receber argumentos e do tipo "R" para retornar
	 * um resultado de outro tipo.
	 * 
	 * No caso em questão ele está atuando para os atributos
	 * quantiaComTaxaAdministrativa e limiteTotal como classes do tipo BigDecimal.
	 */
	public Function<BigDecimal, BigDecimal> usarChequeEspecial(String operacao) {
		return valor -> {
			// A taxa de 1.03 é equivalente a 3%
			BigDecimal quantiaComTaxaAdministrativa = valor.multiply(BigDecimal.valueOf(1.03));
			BigDecimal limiteTotal = this.getSaldo().add(limiteChequeEspecial);
			
			if (quantiaComTaxaAdministrativa.compareTo(limiteTotal) > 0) {
				throw new SaldoInsuficienteException(String.format("\n Limite de cheque especial excedido. Disponível: R$%.2f", limiteTotal));
			}
			
			this.getSaldo().subtract(quantiaComTaxaAdministrativa);
			registroOperacional(operacao, quantiaComTaxaAdministrativa.negate());
			return this.getSaldo();
		};
	}
	
	public Runnable cobrarTaxaDeManutancao(LocalDate dataAtual) {
		return () -> {
			if (dataAtual.getDayOfMonth() == 5 && !dataAtual.equals(ultimaCobrancaDeManutencao)) {
				
				if (this.getSaldo().compareTo(taxaDeManutencao) >= 0) {
					this.getSaldo().subtract(taxaDeManutencao);
				} else {
					// Uso do cheque para pagar a taxa de 3%
					usarChequeEspecial("\n Taxa de manutenção").apply(taxaDeManutencao);
				}
				registroOperacional("\n Taxa de manutenção", taxaDeManutencao.negate());
				this.ultimaCobrancaDeManutencao = dataAtual;
			}
		};
	}

	// Geters e Seters
	public BigDecimal getLimiteChequeEspecial() {
		return limiteChequeEspecial;
	}

	public void setLimiteChequeEspecial(BigDecimal limiteChequeEspecial) {
		this.limiteChequeEspecial = limiteChequeEspecial;
	}

	public BigDecimal getTaxaDeManutencao() {
		return taxaDeManutencao;
	}

	public void setTaxaDeManutencao(BigDecimal taxaDeManutencao) {
		this.taxaDeManutencao = taxaDeManutencao;
	}

	public LocalDate getUltimaCobrancaDeManutencao() {
		return ultimaCobrancaDeManutencao;
	}

	public void setUltimaCobrancaDeManutencao(LocalDate ultimaCobrancaDeManutencao) {
		this.ultimaCobrancaDeManutencao = ultimaCobrancaDeManutencao;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	//
	public void setCliente(Cliente<?> cliente) {
		// TODO Auto-generated method stub
		
	}
	
	
}
