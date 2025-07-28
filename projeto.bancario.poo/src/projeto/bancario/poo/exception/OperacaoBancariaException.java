package projeto.bancario.poo.exception;

/**
 * A classe Operação bancaria exception ela funciona como classe pai
 * para as operações de exceção para saldo insuficiente, conta inativa,
 * e quantia inválida.
 * 
 * E a própria exceção para operação bancaria ela representa todas os erros
 * durante tempo de execução da aplicação.
 */
public class OperacaoBancariaException extends Exception{

	public OperacaoBancariaException(String massage) {
		System.err.println(massage);
	}
}
