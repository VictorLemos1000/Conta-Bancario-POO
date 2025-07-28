package projeto.bancario.poo.exception;

import java.io.Serializable;

/**
 * A classe Operação bancaria exception ela funciona como classe pai
 * para as operações de exceção para saldo insuficiente, conta inativa,
 * e quantia inválida.
 * 
 * E a própria exceção para operação bancaria ela representa todas os erros
 * durante tempo de execução da aplicação.
 */
public class OperacaoBancariaException extends RuntimeException implements Serializable{

	private static final long serialVersonUID = 1L;

	public OperacaoBancariaException(String massage) {
		super(massage);
	}
	
}
