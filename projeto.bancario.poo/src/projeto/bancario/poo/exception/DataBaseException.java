package projeto.bancario.poo.exception;

@SuppressWarnings("serial")
public class DataBaseException extends Exception {

	public DataBaseException(String msg) {
		// TODO Auto-generated constructor stub
		super(msg);
	}
	
	public DataBaseException(String msg, Throwable causa) {
		// TODO Auto-generated constructor stub
		super(msg, causa);
	}
}
