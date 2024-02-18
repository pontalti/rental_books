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
public class CustomerDTO {

	private Long id;
	
	@NotBlank(message = "Name cannot be blank.")
	@NotNull(message = "Name cannot be null.")
	@Size(max = 250, message = "Name cannot exceed 250 characters")
	private String name;
	
	private List<RentalDTO> rentedBooks;
	
}
