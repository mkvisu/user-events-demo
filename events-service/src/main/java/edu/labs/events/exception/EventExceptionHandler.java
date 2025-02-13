package edu.labs.events.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import edu.labs.events.dto.ErrorDetail;



@ControllerAdvice
public class EventExceptionHandler {
	
@ExceptionHandler(EventNotFoundException.class)
public ResponseEntity<?> handleResourceNotFoundException(EventNotFoundException rnfe, HttpServletRequest request) {
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
	
	@ExceptionHandler(InvalidDateRangeException.class)
	public ResponseEntity<ErrorDetail> handle(InvalidDateRangeException exception){
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(LocalDateTime.now());
		errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
		errorDetail.setTitle("Date Range");
		errorDetail.setDetail(exception.getMessage());
		errorDetail.setDeveloperMessage(exception.getClass().getName());
			return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
	}
}