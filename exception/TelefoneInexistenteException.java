package exception;

public class TelefoneInexistenteException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public TelefoneInexistenteException(String s) {
		super(s);
	}
}
