package projeto.bancario.poo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//Imports de exceções
import projeto.bancario.poo.exception.ContaInativaException;
import projeto.bancario.poo.exception.ContaNaoEncontradaException;
import projeto.bancario.poo.exception.OperacaoBancariaException;
import projeto.bancario.poo.exception.QuantiaInvalidaException;

// Imports de classes
import projeto.bancario.poo.model.Cliente;
import projeto.bancario.poo.model.Conta;
import projeto.bancario.poo.model.ContaCorrente;
import projeto.bancario.poo.model.ContaPoupanca;

@SuppressWarnings({ "unused", "rawtypes", "serial" })
public class ContaService extends Conta{

	private List<Conta<?>> contas;
	
	// Método implícito
	public ContaService() {
		// TODO Auto-generated constructor stub
		this.contas = new ArrayList<>();
	}

	public List<Conta<?>> getContas() {
		return contas;
	}

	public void setContas(List<Conta<?>> contas) {
		this.contas = contas;
	}
	
	// Criação de uma conta corrente.
	public ContaCorrente criarContaCorrente(Cliente cliente, String numeroConta, BigDecimal saldoInicial, BigDecimal limiteChequeEspecial, BigDecimal taxaManutencao) {
		// TODO Auto-generated method stub
		ContaCorrente novaContaCorrente = new ContaCorrente(numeroConta, saldoInicial, limiteChequeEspecial, taxaManutencao);
		novaContaCorrente.setCliente(cliente);
		contas.add(novaContaCorrente);
		return novaContaCorrente;
	}
	
	// Criação de uma conta poupança.
	public ContaPoupanca criarContaPoupanca(Cliente<?> cliente, String numeroConta, BigDecimal saldoInicial, BigDecimal taxaRendimento) {
		// TODO Auto-generated method stub
		ContaPoupanca novaContaPoupanca = new ContaPoupanca(numeroConta, saldoInicial, taxaRendimento);
		novaContaPoupanca.setCliente(cliente);
		contas.add(novaContaPoupanca);
		return novaContaPoupanca;
	}
	
	// Método de busca de uma conta
	private Conta<?> encontrarConta(String numeroConta) throws ContaNaoEncontradaException{
		// TODO Auto-generated method stub
		if (contas ==  null || contas.isEmpty()) {
			throw new ContaNaoEncontradaException(" Conta não encontrada no sistema.");
		}
		
		for (Conta<?> conta : contas) {
			if (conta.getNumeroConta().equals(numeroConta)) {
				return conta;
			}
		}
		throw new ContaNaoEncontradaException(" Conta: " + numeroConta + ", não encontrada");
	}
	
	public void depositar(String numeroConta, BigDecimal quantia) throws OperacaoBancariaException, ContaNaoEncontradaException{
		// A classe ContaNãoEncontradaException está evitando que a linha 69 tenha erro de código.
		Conta<?> conta = encontrarConta(numeroConta);
		conta.depositarQuantia(quantia);
	}
	
	public void sacar(String numeroConta, BigDecimal quantia) throws OperacaoBancariaException, ContaNaoEncontradaException{
		// TODO Auto-generated method stub
		Conta<?> conta = encontrarConta(numeroConta);
		conta.sacarQuantia(quantia);
	}
	
	public void transferir(String contaOrigem, String contaDestino, BigDecimal quantia) throws OperacaoBancariaException, ContaNaoEncontradaException {
		// TODO Auto-generated method stub
		Conta<?> origem = encontrarConta(contaOrigem);
		Conta<?> destino = encontrarConta(contaDestino);
		origem.tranferirQuantia(destino, quantia);
	}
	
	public List<Conta<?>> listarTodasAsContas() {
		// TODO Auto-generated method stub
		return new ArrayList<Conta<?>>(contas);
	}
	
	public void aplicarRendimentoContaPoupanca() {
		// TODO Auto-generated method stub
		if (contas == null || contas.isEmpty()) {
			System.out.println(" Nenhuma conta cadastrada para aplicar rendimento.");
		}
		
		LocalDate dataAtual = LocalDate.now();
		int contasProcessadas = 0;
		
		// O forEach percorre todas as contas para realizar a verificação
		for (Conta<?> conta : contas) {
			// A condição verifica se a conta instanciada é uma conta poupança.
			if (conta instanceof ContaPoupanca) {
				// Cast de conta poupança para não gerar erro no atributo conta
				ContaPoupanca poupanca = (ContaPoupanca)conta;
				
				try {
					// Aplicação do rendimento.
					poupanca.aplicarRendimento(dataAtual);
					contasProcessadas++;
				} catch (Exception e) {
					// TODO: handle exception
					System.err.println(" Erro ao aplicar rendimento na conta " + poupanca.getNumeroConta() + ":" + e.getMessage());
				}
			}
		}
		
		// Resultado das contas processadas.
		System.out.println(" Rendimento aplicado em " + contasProcessadas + " conta(s) poupança.");
	}
	
	public void desativarConta(String numeroConta) throws ContaNaoEncontradaException {
		// TODO Auto-generated method stub
		Conta<?> conta = encontrarConta(numeroConta);
		conta.setStatus(false);
	}
	
	public void ativarConta(String numeroConta) throws ContaNaoEncontradaException {
		// TODO Auto-generated method stub
		Conta<?> conta = encontrarConta(numeroConta);
		conta.setStatus(true);
	}
	
	// Métodos inerentes a conta corrente.
	public void usarChequeEspecial(String numeroConta, BigDecimal quantia) throws OperacaoBancariaException, ContaNaoEncontradaException{
		// TODO Auto-generated method stub
		Conta<?> conta = encontrarConta(numeroConta);
		
		if (!(conta instanceof ContaCorrente)) {
			throw new OperacaoBancariaException(" Operação apenas é válida para contas correntes.");
		}
		
		ContaCorrente contaCorrente = (ContaCorrente) conta;
		contaCorrente.usarChequeEspecial(" Uso do cheque especial.").apply(quantia);
	}
	
	public void cobrarTaxaManutencao(String numeroConta) throws OperacaoBancariaException, ContaNaoEncontradaException {
		// TODO Auto-generated method stub
		Conta<?> conta = encontrarConta(numeroConta);
		
		if (!(conta instanceof ContaCorrente)) {
			throw new OperacaoBancariaException(" Operação apenas é válida para contas correntes.");
		}
		
		ContaCorrente contaCorrente = (ContaCorrente) conta;
		contaCorrente.cobrarTaxaDeManutancao(LocalDate.now()).run();
	}
	
	public BigDecimal consultarLimiteChequeEspecial(String numeroConta) throws ContaNaoEncontradaException {
		// TODO Auto-generated method stub
		Conta<?> conta = encontrarConta(numeroConta);
		
		if (!(conta instanceof ContaCorrente)) {
			return ((ContaCorrente) conta).getLimiteChequeEspecial();
		}
		return BigDecimal.ZERO;
	}
}
