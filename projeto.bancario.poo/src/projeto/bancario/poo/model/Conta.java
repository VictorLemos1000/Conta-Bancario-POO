package projeto.bancario.poo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Conta implements Serializable{

	// Atributos que compoẽ o cenário realizado
	private BigDecimal saldo;
	private boolean status;
	private String numeroConta;
	private LocalDateTime dataConta;
	
	// Método construtor explícito para 
	public Conta(BigDecimal saldo, boolean status, String numeroConta, LocalDateTime dataConta) {
		this.saldo = saldo;
		this.status = true;
		this.numeroConta = numeroConta;
		this.dataConta = dataConta;
	}

	public void sacarQuantia(BigDecimal quantia) {
		if (this.status && this.saldo.compareTo(quantia) >= 1) {
			this.saldo = saldo.subtract(quantia);
		} else {
			throw new IllegalArgumentException(" Saldo insuficiente para realização de saque.");
		}
	}
	
	public void despositarQuantia(BigDecimal quantia) {
		if (this.status && this.saldo.compareTo(quantia) <= 0) {
			this.saldo = saldo.add(quantia);
		} else {
			throw new IllegalArgumentException(" Saldo insuficiente para realização de deposito.");
		}
	}
	
	public void transferirQuantia(BigDecimal quantia) {
		if (this.status) {
			
		} else {

		}
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public LocalDateTime getDataConta() {
		return dataConta;
	}

	public void setDataConta(LocalDateTime dataConta) {
		this.dataConta = dataConta;
	}

	@Override
	public String toString() {
		return "Conta [saldo=" + this.saldo + ", status=" + this.status + ", numeroConta=" + this.numeroConta + ", dataConta="
				+ this.dataConta + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.numeroConta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		return Objects.equals(this.numeroConta, other.numeroConta);
	}
	
	
	
}
