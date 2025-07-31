package projeto.bancario.poo.app;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import projeto.bancario.poo.exception.OperacaoBancariaException;
import projeto.bancario.poo.model.Conta;
import projeto.bancario.poo.model.ContaPoupanca;

// Classe onde aplicação será composta
public class Aplicacao {

	public static void main(String[] args) {
		
		 // Teste para conta de origem e destino;
//         Conta contaOrigem = new Conta(new BigDecimal("1000.00"), true, "123", LocalDateTime.now());
//         
//         Conta contaDestino = new Conta(new BigDecimal("500.50"), true, "456", LocalDateTime.now());
//         
//         try {
//			// Ex: Depósito
//        	 contaOrigem.depositarQuantia(new BigDecimal("250.50"));
//        	 System.out.println("Saldo pós deposito: " + contaOrigem.getSaldo());
// 			
//        	 // Ex: Saque
//        	 contaOrigem.sacarQuantia(new BigDecimal("100.25"));
//        	 System.out.println("Saldo pós saque: " + contaOrigem.getSaldo());
// 			
//        	 // Ex: Transferência
//        	 contaOrigem.tranferirQuantia(contaDestino, new BigDecimal("300.00"));
//        	 System.out.println("Saldo origem pós tranferência: " + contaOrigem.getSaldo());
//        	 System.out.println("Saldo destino pós transferência: " + contaDestino.getSaldo());
//        	 
//		} catch (OperacaoBancariaException e) {
//			// TODO: handle exception
//			System.err.println(" Erro na Operação: " + e.getMessage());
//		}
        
        // Teste funcional Conta Poupança;
        // Aplicação de 1000 reais a conta poupança e 0.5% a taxa de rendimento
        ContaPoupanca poupanca = new ContaPoupanca("123", new BigDecimal("1000.00"), new BigDecimal("0.005"));
        
        poupanca.aplicarRendimento(LocalDate.now());
        
        System.out.println(poupanca);
        System.out.println("Rendimento: " + poupanca.getTaxaRendimento());
        System.out.println("Próximo aniversário: " + poupanca.getDataAniversario());
    }
}
