package br.com.itb.miniprojetospring.model;

import java.time.LocalDateTime;

public class InvalidData {
	private String message;
	private int status;
	private String type;
	private LocalDateTime timestamp;
	
	// Constructor
	
	public InvalidData(String message, int status, String type) {
		super();
		this.message = message;
		this.status = status;
		this.type = type;
		this.timestamp = LocalDateTime.now();
	}
	
	// Getters and Setters
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
