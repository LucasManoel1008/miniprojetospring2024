package br.com.itb.miniprojetospring.Errors;

public class InvalidDataException extends RuntimeException {
	private String type;
	public InvalidDataException(String message, String type) {
		super(message);
		this.type = type;
	}

	public String getType(){
		return type;
	}

	

}
