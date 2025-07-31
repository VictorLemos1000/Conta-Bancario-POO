package projeto.bancario.poo.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class ContaPoupanca extends Conta<Cliente>{

	private BigDecimal taxaRendimento;
	private LocalDate dataAniversario;
	
	public ContaPoupanca(String numeroConta, BigDecimal saldoInicial, BigDecimal taxaRendimento) {
		numeroConta = numeroConta;
		saldoInicial = saldoInicial.setScale(2, RoundingMode.HALF_EVEN);	
		
		/*
		 * O atributo taxa de rendimento está formatado para atribuir 4 casas decimais e
		 * realizando um arredodamento por HALF_EVEN, conhecido como arredondamento bancário,
		 * mas sendo um pouco mais específico ele arredonda para um valor par mais próximo.
		 */
		this.taxaRendimento = taxaRendimento.setScale(4, RoundingMode.HALF_EVEN);
		this.dataAniversario = LocalDate.now();
	}
	
	public void aplicarRendimento(LocalDate dataAtual) {
		if (dataAtual.getDayOfMonth() == dataAniversario.getDayOfMonth() && !dataAtual.equals(dataAniversario)) {
			BigDecimal rendimento = this.getSaldo().multiply(taxaRendimento).setScale(2, RoundingMode.HALF_EVEN);
			
			this.depositarQuantia(rendimento);
			this.dataAniversario = dataAtual;
		}
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
}
