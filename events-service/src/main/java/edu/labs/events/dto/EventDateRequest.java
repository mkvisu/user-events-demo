package edu.labs.events.dto;

import java.util.Date;

import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EventDateRequest {
	
	@Past
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	
	@Past
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date endDate;
}
