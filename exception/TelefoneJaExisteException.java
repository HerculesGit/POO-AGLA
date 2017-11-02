package exception;

public class TelefoneJaExisteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public TelefoneJaExisteException(String s) {
		super(s);
	}
}
