package projeto.bancario.poo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

public class Cliente implements Serializable{

	private String cpf;
	private String nome;
	
	private ArrayList<Cliente> clientes = new ArrayList<>();
	
	public Cliente(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
	}
	
	public void acicionarCliente(Cliente conta) {
		if (this.clientes.contains(conta)) {
			clientes.add(conta);
		} else {

		}
	}
	
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
	}

	@Override
	public String toString() {
		return "Cliente [cpf=" + this.cpf + ", nome=" + this.nome + ", clientes=" + this.clientes + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(this.cpf, other.cpf);
	}
	
}
