package edu.labs.events.exception;

import java.util.Date;

public class InvalidDateRangeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidDateRangeException(Date startDate, Date endDate) {
		super(String.format("Invalid date range %s and %s", startDate, endDate));
	}
}
