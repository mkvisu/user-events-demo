package edu.labs.user.dto;

import java.time.LocalDateTime;

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
	private LocalDateTime timeStamp;
	private String developerMessage;

}
