package com.records;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RentalBookReturnRecord( @NotNull(message = "Rental book id attribute cannot be null.")
								long rentalBookID, 
								
								@NotNull(message = "Quantity attribute cannot be null.")
								@NotEmpty(message = "Quantity attribute cannot be empty.")
								@Min(value = 1, message = "Quantity attribute cannot be less than 1")
								int quantity) {
}