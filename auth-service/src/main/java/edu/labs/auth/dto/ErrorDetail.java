package edu.labs.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ErrorDetail {
	private String title;
	private int status;
	private String detail;
	private long timeStamp;
	private String developerMessage;

}
