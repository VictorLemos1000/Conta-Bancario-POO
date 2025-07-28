package projeto.bancario.poo.exception;


/*
 * Exceção herda caracteristicas da classe pais caso ela acine
 * algum erro durante a execução do sistema.
 */
public class QuantiaInvalidaException extends OperacaoBancariaException{

	public QuantiaInvalidaException(String massage) {
		super(massage);
	}
}
