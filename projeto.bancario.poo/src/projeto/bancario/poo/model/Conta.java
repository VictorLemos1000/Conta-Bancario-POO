package projeto.bancario.poo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import projeto.bancario.poo.exception.ContaInativaException;
import projeto.bancario.poo.exception.OperacaoBancariaException;
import projeto.bancario.poo.exception.QuantiaInvalidaException;
import projeto.bancario.poo.exception.SaldoInsuficienteException;

public abstract class Conta<T> implements Serializable{

	private static final long serialVersonUID = 1L;
	
	// Atributos essenciais para uma determinada conta
	private BigDecimal saldo; // O uso da classe BigDecimal é muito útil para sistemas financeiros.
	private boolean status;
	private String numeroConta;
	private LocalDateTime dataConta; // LocalDateTime serve para definir a data e a hora que a conta foi criada.
	
	// Referência de atributo de uma classe generica
	protected T cliente;
	
	// Construtor implícito
	public Conta() {
		this.saldo =  BigDecimal.ZERO;
		this.status = true; // Conta ativa por padrão
		this.dataConta = LocalDateTime.now();
	}

	// Construtor explícito
	public Conta(BigDecimal saldo, boolean status, String numeroConta, LocalDateTime dataConta) {
		this.saldo = saldo;
		this.status = status;
		this.numeroConta = numeroConta;
		this.dataConta = dataConta;
	}
	
	/*
	 * O método criado ele vai realizar seguinte ação de validade da 
	 * quantia se é nula, se está zerada ou está inativa para os demais
	 * métodos composto na classe Conta que são depositar, sacar e transferir.
	 */
	public void validacaoOperacional(BigDecimal quantia) throws OperacaoBancariaException {
		if (quantia == null) {
			throw new QuantiaInvalidaException("\n A quantia não pode ser nula.");
		}
		
		if (quantia.compareTo(BigDecimal.ZERO) <= 0) {
			throw new QuantiaInvalidaException("\n A quantia do saldo deve ser maior que R$0,00.");
		}
		
		if (!this.isStatus()) {
			throw new ContaInativaException("\n A conta está inativa.");
		}
	}
	
	// Método de deposito da conta.
	public void depositarQuantia(BigDecimal quantia) throws OperacaoBancariaException{
		validacaoOperacional(quantia);
		
		this.saldo = this.saldo.add(quantia);
	}
	
	// Método para retirada de quntia da conta.
	public void sacarQuantia(BigDecimal quantia) throws OperacaoBancariaException {
		validacaoOperacional(quantia);
		
		if (this.saldo.compareTo(quantia) < 0) {			
			throw new  SaldoInsuficienteException("\n Saque insuficiente para saque.");
		}
		
		this.saldo = saldo.subtract(quantia);
	}

	// Método para tranferência de determinada quantia de uma conta bancaria.
	public void tranferirQuantia(Conta contaDestino, BigDecimal quntia) throws OperacaoBancariaException {
		validacaoOperacional(quntia);
		
		if (!contaDestino.isStatus()) {
			throw new ContaInativaException("\n Sua conta destino está inativa.");
		}
		
		/* O método de saque está referenciando a realização de uma transferencia da
		 * conta de origem.
		 */
		this.sacarQuantia(quntia);
		
		// O método de deposito está realizando uma ação da conta de destino.
		contaDestino.depositarQuantia(quntia);
	}
	
	// Métodos geters e seters.
	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	// Método importante para verificar se a conta está ativa ou inativa.
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

	// Método de instaicação de classe
	public static long getSerialversonuid() {
		return serialVersonUID;
	}

	/*
	 * O método toString ele evita que o programa exiba uma hash
	 * para um usuário, mas ao invés disso ele aplica extamente o
	 * valor de cada atributo.
	 */
	@Override
	public String toString() {
		return "Conta [saldo=" + saldo + ", status=" + status + ", numeroConta=" + numeroConta + ", dataConta="
				+ dataConta + "]";
	}

	/*
	 * O método hashCode cria um número único baseado no valor do objeto;
	 * ou seja um valor único para o objeto, por exemplo um id, mas neste
	 * contexto de classe um número para uma determinada conta.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(numeroConta);
	}

	/*
	 * O método equals serve para comparar se 2 objetos são iguais,
	 * mas dentro da menmória.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		return Objects.equals(numeroConta, other.numeroConta);
	}
}
