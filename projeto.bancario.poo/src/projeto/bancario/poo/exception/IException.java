package projeto.bancario.poo.exception;

public interface IException {

	// Interfaces aplicadas a os métodos da classe conta.
	public Exception OperacaoBancariaException(String massage) throws RuntimeException;
	public OperacaoBancariaException QuantiaInvalidaException(String massage);
	public OperacaoBancariaException SaldoInsuficienteException(String massage);
	public OperacaoBancariaException ContaInativaException(String massage);
	
	// Interface aplicada especialmente para método localizarConta na Classe cliente
	public Exception ContaNaoEncontradaException(String massage) throws Exception;
}
