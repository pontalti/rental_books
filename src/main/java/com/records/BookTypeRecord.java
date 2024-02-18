package com.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookTypeRecord(
		
								@NotBlank(message = "Type cannot be blank.")
								@NotNull(message = "Type cannot be null.")
								@Size(max = 50, message = "Type cannot exceed 50 characters")
								String type,
								
								@NotBlank(message = "Description cannot be blank.")
								@NotNull(message = "Description cannot be null.")
								@Size(max = 1000, message = "Description cannot exceed 1000 characters")
								String description
		
							) {

}
