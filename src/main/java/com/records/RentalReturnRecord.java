package com.records;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RentalReturnRecord(	@NotNull(message = "Rental Order Id attribute cannot be null.")
									long rentalOrderId,
		
									@NotNull(message = "Attribute List of rental books return cannot be null.")
									@NotEmpty(message = "Attribute List of rental books return cannot be empty.") 
									List<RentalBookReturnRecord> rentalBooks) {

}
