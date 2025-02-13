package edu.labs.events.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime timeStamp;
	private String developerMessage;

}
