package br.com.itb.miniprojetospring.Errors;

public class DataAlredyRegistred extends RuntimeException {
	private String type;

	public DataAlredyRegistred(String message, String type) {
		super(message);
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
