package com.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Min;
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
public class BookDTO {

	private Long id;
	
	@NotBlank(message = "Image URL cannot be blank.")
	@NotNull(message = "ImageURL cannot be null.")
	@Size(max = 4000, message = "Image URL cannot exceed 4000 characters")
	private String imageURL;
	
	@NotBlank(message = "Title cannot be blank.")
	@NotNull(message = "Title cannot be null.")
	@Size(max = 100, message = "Description cannot exceed 100 characters")
    private String title;
	
	@NotBlank(message = "Description cannot be blank.")
	@NotNull(message = "Description cannot be null.")
	@Size(max = 4000, message = "Description cannot exceed 4000 characters")
    private String description;
	
	@NotNull(message = "Quantity owned cannot be null.")
	@Min(value = 0, message = "Quantity should cannot be less than 0")
    private int quantityOwned;
	
    private int quantityAvailable;
    private BookTypeDTO bookType;
    private List<RentalDTO> rentedBooks;
    
}