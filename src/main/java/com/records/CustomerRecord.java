package com.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerRecord(
								@NotBlank(message = "Name cannot be blank.") 
								@NotNull(message = "Name cannot be null.") 
								@Size(max = 250, message = "Name cannot exceed 250 characters") 
								String name) {
}
