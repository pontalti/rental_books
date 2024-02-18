package com.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(Include.NON_NULL)
public class BookTypeDTO {
	
	private Long id;
	
	@NotBlank(message = "Type cannot be blank.")
	@NotNull(message = "Type cannot be null.")
	@Size(max = 50, message = "Type cannot exceed 50 characters")
	private String type;
    
	@NotBlank(message = "Description cannot be blank.")
	@NotNull(message = "Description cannot be null.")
	@Size(max = 1000, message = "Description cannot exceed 1000 characters")
	private String description;
	
    private List<BookDTO> books;
    
}