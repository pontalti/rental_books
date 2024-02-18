package com.records;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookRecord(
							@NotBlank(message = "Image URL cannot be blank.")
							@NotNull(message = "ImageURL cannot be null.")
							@Size(max = 4000, message = "Image URL cannot exceed 4000 characters")
							String imageURL,
						    
							@NotBlank(message = "Title cannot be blank.")
							@NotNull(message = "Title cannot be null.")
							@Size(max = 100, message = "Description cannot exceed 100 characters")
							String title,
						    
							@NotBlank(message = "Description cannot be blank.")
							@NotNull(message = "Description cannot be null.")
							@Size(max = 4000, message = "Description cannot exceed 4000 characters")
							String description,
						    
							@NotNull(message = "Quantity owned cannot be null.")
							@Min(value = 1, message = "Quantity should cannot be less than 1")
						    int quantityOwned,
							
							@NotNull(message = "Book type Id cannot be null.")
							long bookTypeId
) {

}
