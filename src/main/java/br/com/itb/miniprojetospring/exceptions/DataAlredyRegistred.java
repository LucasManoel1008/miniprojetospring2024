package br.com.itb.miniprojetospring.exceptions;

public class DataAlredyRegistred extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DataAlredyRegistred(String message) {
		super(message);
	}

}
