package br.com.itb.miniprojetospring.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.itb.miniprojetospring.model.InvalidData;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(InvalidDataException.class)
	    public ResponseEntity<InvalidData> handleInvalidDataException(InvalidDataException ex) {
	        InvalidData invalidData = new InvalidData(
	                ex.getMessage(),
	                HttpStatus.BAD_REQUEST.value()
	        );
	        return new ResponseEntity<>(invalidData, HttpStatus.BAD_REQUEST);
	    }
	 
	 @ExceptionHandler(DataAlredyRegistred.class)
	    public ResponseEntity<InvalidData> handleDataAlreadyRegisteredException(DataAlredyRegistred ex) {
	        InvalidData invalidData = new InvalidData(
	                ex.getMessage(),
	                HttpStatus.BAD_REQUEST.value()
	        );
	        return new ResponseEntity<>(invalidData, HttpStatus.BAD_REQUEST);
	    }


}
