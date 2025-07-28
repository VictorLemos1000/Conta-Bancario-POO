package projeto.bancario.poo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Cliente implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// Atributos necessário para a classe de um determinado cliente
	private String nome;
	private String cpf;
	
	private ArrayList<Conta> contas = new ArrayList<>();
	
	// Construtor implícito
	public Cliente() {
		
	}
	
	// Construtor explícito
	public Cliente(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
		this.contas = new ArrayList<Conta>();
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

	public ArrayList<Conta> getContas() {
		return contas;
	}

	public void setContas(ArrayList<Conta> contas) {
		this.contas = contas;
	}

	/*
	 * O método toString ele evita que o programa exiba uma hash
	 * para um usuário, mas ao invés disso ele aplica extamente o
	 * valor de cada atributo.
	 */
	@Override
	public String toString() {
		return "Cliente [nome=" + nome + ", cpf=" + cpf + ", contas=" + contas + "]";
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
