package projeto.bancario.poo.exception;

/*
 * Exceção herda caracteristicas da classe pais caso ela acine
 * algum erro durante a execução do sistema.
 */
public class ContaInativaException extends OperacaoBancariaException{

	public ContaInativaException(String massage) {
		super(massage);
	}
}
