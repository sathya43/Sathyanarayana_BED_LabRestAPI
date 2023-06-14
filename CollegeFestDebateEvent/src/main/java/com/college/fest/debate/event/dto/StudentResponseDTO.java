package com.college.fest.debate.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponseDTO {
	
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private String course;
	
	private String country;
}
