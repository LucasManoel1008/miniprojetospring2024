package br.com.itb.miniprojetospring.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.itb.miniprojetospring.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(InvalidDataException.class)
	    public ResponseEntity<ErrorResponse> handleInvalidDataException(InvalidDataException ex) {
	        ErrorResponse errorResponse = new ErrorResponse(
	                ex.getMessage(),
	                HttpStatus.BAD_REQUEST.value()
	        );
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	    }

}
