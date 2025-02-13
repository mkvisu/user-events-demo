package edu.labs.user.exceptions;

import java.time.LocalDateTime;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import edu.labs.user.dto.ErrorDetail;

@ControllerAdvice
public class UserExceptionHandler {
	
@ExceptionHandler(UserNotFoundException.class)
public ResponseEntity<?> handleResourceNotFoundException(UserNotFoundException rnfe, HttpServletRequest request) {
	ErrorDetail errorDetail = new ErrorDetail();
	errorDetail.setTimeStamp(LocalDateTime.now());
	errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
	errorDetail.setTitle("Resource Not Found");
	errorDetail.setDetail(rnfe.getMessage());
	errorDetail.setDeveloperMessage(rnfe.getClass().getName());
		return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorDetail> handleValidationException(ValidationException ex) {
		ErrorDetail errorDetails = ErrorDetail.builder().title("Invalid Request").status(HttpStatus.BAD_REQUEST.value())
					.detail(ex.getMessage()).timeStamp(LocalDateTime.now())
					.developerMessage("").build();
		
	    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
}