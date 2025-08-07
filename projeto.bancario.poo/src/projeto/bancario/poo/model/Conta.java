package projeto.bancario.poo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import projeto.bancario.poo.exception.ContaInativaException;
import projeto.bancario.poo.exception.OperacaoBancariaException;
import projeto.bancario.poo.exception.QuantiaInvalidaException;
import projeto.bancario.poo.exception.SaldoInsuficienteException;

/*
 * A classe conta ela está utilizando a propriedade generics que
 * permite classes, interfaces, métodos que trabalham de forma parametrizada.
 * 
 * Utilizar esta propriedade indica evitar erros de CastClassExcetion
 * e reutiliza códigos de uma única classe ou método que funcionam com
 * diversas tipagens.
 * 
 * Após mais uma vez pesquisar entendi para que realmente serve generics apenas
 * para flexiblidade de tipos variados.
 * 
 * A classe pai Conta após ter se tornado abstrata ela não pode de forma alguma
 * instanciada apenas é uma base para classes filhas que neste cenário são elas
 * classes ContaPoupanca e ContaCorrente.
 * 
 * Outro topico importante salientar a classe pai Conta també conhecida como Superclasse;
 */
@SuppressWarnings({ "unused", "serial", "rawtypes" })
public abstract class Conta<T extends Cliente> implements Serializable{

	private static final long serialVersonUID = 1L;
	
	// Atributos essenciais para uma determinada conta
	/*
	 * Os dois seguintes atributos de saldo e numeroConta estão
	 * com modificadores de acesso protected devido a seus tratamento
	 * de 2 classes que vão herdar suas propriedades para manipulação da
	 * regra de negócio.
	 * 
	 * Especificação da herança este 2 atributos vão ser tratados nas
	 * classes ContaPoupanca e COntaCorrente
	 */
	private String numeroConta; // Atributo de identificação da classe.
	private BigDecimal saldo; // O uso da classe BigDecimal é muito útil para sistemas financeiros.
	
	private LocalDateTime dataConta; // LocalDateTime serve para definir a data e a hora que a conta foi criada.
	private boolean status;

	// Atributo de associação ao cliente.
	private Cliente cliente;
	
	// Lista de transações.
	private List<Transacao> transacoes;
	
	// Referência de atributo de uma classe generica
//	protected T cliente;
	
	// Construtor implícito
	public Conta() {
//		this.saldo =  BigDecimal.ZERO;
//		this.status = true; // Conta ativa por padrão
//		this.dataConta = LocalDateTime.now();
	}

	// Construtor explícito
	public Conta(String numeroConta, Cliente cliente) {
		if (numeroConta == null ||  numeroConta.isBlank()) {
			throw new IllegalArgumentException(" O número da conta não pode estar vázio.");
		}
		
		if (cliente == null) {
			throw new IllegalArgumentException(" Conta de estar vinculada a um cliente");
		}
		
		this.numeroConta = numeroConta;
		this.saldo = BigDecimal.ZERO;
		this.dataConta = LocalDateTime.now();
		this.status = true;
		this.cliente = cliente; 
		this.transacoes = new ArrayList<>();
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
	public abstract void depositarQuantia(BigDecimal quantia) throws OperacaoBancariaException;
	
	// Método para retirada de quntia da conta.
	public abstract void sacarQuantia(BigDecimal quantia) throws OperacaoBancariaException;

	// Método para tranferência de determinada quantia de uma conta bancaria.
	public abstract void tranferirQuantia(Conta contaDestino, BigDecimal quntia) throws OperacaoBancariaException;
	
	// Métodos geters e seters servem para atribuir e retornar valores a seus respectivos atributos.
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

	@SuppressWarnings("unchecked")
	public List<Cliente> getCliente() {
		return (List<Cliente>) cliente;
	}

	public void setCliente(List<Cliente> cliente) {
		this.cliente = (Cliente) cliente;
	}

	public List<Transacao> getTransacoes() {
		return transacoes;
	}

	public void setTransacoes(List<Transacao> transacoes) {
		this.transacoes = transacoes;
	}

	/*
	 * O método toString ele evita que o programa exiba uma hash
	 * para um usuário, mas ao invés disso ele aplica extamente o
	 * valor de cada atributo.
	 */
	@Override
	public String toString() {
		return "Conta [numeroConta=" + numeroConta + ", saldo=" + saldo + ", dataConta=" + dataConta + ", status="
				+ status + ", cliente=" + cliente + ", transacoes=" + transacoes + "]";
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

	public Object getService() {
		// TODO Auto-generated method stub
		return null;
	}
}
