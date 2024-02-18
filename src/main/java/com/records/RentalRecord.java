package com.records;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RentalRecord (@NotNull(message = "Customer id attribute cannot be null.")
							long customerID,
								
							@NotNull(message = "Attribute List of rental books cannot be null.")
							@NotEmpty(message = "AttributeList of rental books cannot be empty.") 
							List<RentalBookRecord> rentalBooks ) {

}
