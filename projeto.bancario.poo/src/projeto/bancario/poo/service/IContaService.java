package projeto.bancario.poo.service;

import java.math.BigDecimal;
import java.util.List;

import projeto.bancario.poo.exception.ContaNaoEncontradaException;
import projeto.bancario.poo.exception.OperacaoBancariaException;
import projeto.bancario.poo.model.Cliente;
import projeto.bancario.poo.model.Conta;
import projeto.bancario.poo.model.ContaCorrente;
import projeto.bancario.poo.model.ContaPoupanca;

public interface IContaService {

    // Operações básicas
    void depositar(String numeroConta, BigDecimal quantia) throws OperacaoBancariaException, ContaNaoEncontradaException;
    
    void sacar(String numeroConta, BigDecimal quantia) throws OperacaoBancariaException, ContaNaoEncontradaException;
    
    void transferir(String contaOrigem, String contaDestino, BigDecimal quantia) throws OperacaoBancariaException, ContaNaoEncontradaException;
    
    // Gerenciamento de contas
    ContaCorrente criarContaCorrente(Cliente cliente, String numeroConta, BigDecimal saldoInicial, BigDecimal limiteChequeEspecial, BigDecimal taxaManutencao);
    
    ContaPoupanca criarContaPoupanca(Cliente<?> cliente, String numeroConta, BigDecimal saldoInicial, BigDecimal taxaRendimento);
    
    void ativarConta(String numeroConta) throws ContaNaoEncontradaException;
    
    void desativarConta(String numeroConta) throws ContaNaoEncontradaException;
    
    // Consultas
    Conta<?> encontrarConta(String numeroConta) throws ContaNaoEncontradaException;
    
    List<Conta<?>> listarTodasAsContas();
    
    // Operações específicas
    void aplicarRendimentoContaPoupanca();
}
