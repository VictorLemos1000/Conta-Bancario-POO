package projeto.bancario.poo.exception;

public interface IException {

	// Interfaces aplicadas a os métodos da classe conta.
	public RuntimeException OperacaoBancariaException(String massage);
	public OperacaoBancariaException QuantiaInvalidaException(String massage);
	public OperacaoBancariaException SaldoInsuficienteException(String massage);
	public OperacaoBancariaException ContaInativaException(String massage);
}
