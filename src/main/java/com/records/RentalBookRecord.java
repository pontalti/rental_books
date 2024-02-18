package com.records;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RentalBookRecord( @NotNull(message = "Customer id attribute cannot be null.")
								long bookID, 
								
								@NotNull(message = "Quantity attribute cannot be null.")
								@NotEmpty(message = "Quantity attribute cannot be empty.")
								@Min(value = 1, message = "Quantity attribute cannot be less than 1")
								int quantity) {
}