package projeto.bancario.poo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import projeto.bancario.poo.exception.ContaNaoEncontradaException;

// O tipo genérico T generaliza o tipo que será definido posteriormente.
// No contexto geral o genérics ele é muito utilizados para coleções como List, Set, Map etc.
public class Cliente<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// Atributos necessário para a classe de um determinado cliente
	private long id;
	private String nome;
	private String cpf;
	private String endereco;
	
	private List<Conta<?>> contas = new ArrayList<>();
	
	// Referência de atributo de uma classe generica
	protected T titular;
	
	// Construtor implícito
	public Cliente() {
		
	}
	
	// Construtor explícito
	public Cliente(String nome, String cpf, String endereco) {
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = endereco;
		this.contas = new ArrayList<Conta<?>>();
	}
	
	/*
	 * Caso a conta do usuário seja nula vai adicionado uma a conta ao arrayList de contas
	 */
	public void adicionarCliente(Conta<?> conta) {
		if (conta == null) {
			throw new IllegalArgumentException("\n A sua conta não pode ser nula.");
		}
		
		this.contas.add(conta);
	}
	
	public boolean removerCliente(String numeroConta) {
		return this.contas.removeIf(conta -> conta.getNumeroConta().equals(numeroConta));
	}
	
	@SuppressWarnings("hiding")
	public <T extends Conta<?>> T localizarCliente(String numeroConta, Class<T> tipoConta) throws ContaNaoEncontradaException{
		
		for (Conta<?> conta : contas) {
			if (conta.getNumeroConta().equals(numeroConta)) {
				if (tipoConta.isInstance(conta)) {
					return tipoConta.cast(conta);
				}
				throw new ContaNaoEncontradaException("\n O tipo conta é imcompatível.");
			}
		}
		throw new ContaNaoEncontradaException("\n Sua conta não foi encontrada.");
	}
	
	// Métodos geter e seters dos tributos
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<Conta<?>> getContas() {
		return contas;
	}

	public void setContas(List<Conta<?>> contas) {
		this.contas = contas;
	}
	
	

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public T getTitular() {
		return titular;
	}

	public void setTitular(T titular) {
		this.titular = titular;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/*
	 * O método toString ele evita que o programa exiba uma hash
	 * para um usuário, mas ao invés disso ele aplica extamente o
	 * valor de cada atributo.
	 */
	@Override
	public String toString() {
		return "Cliente [nome=" + nome + ", cpf=" + cpf + ", endereco=" + endereco + ", contas=" + contas + ", titular="
				+ titular + "]";
	}	

	/*
	 * O método hashCode cria um número único baseado no valor do objeto;
	 * ou seja um valor único para o objeto, por exemplo um cpf, mas neste
	 * contexto de classe um número para uma determinada conta.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	/*
	 * O método equals serve para comparar se 2 objetos são iguais,
	 * mas dentro da menmória.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf);
	}
}
