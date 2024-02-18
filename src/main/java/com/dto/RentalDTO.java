package com.dto;

import java.util.List;

import com.enums.RentalStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class RentalDTO {

	@JsonProperty("rentalOrderId")
	private Long id;
	private CustomerDTO customer;
	private List<RentalBookDTO> rentalBook;
    private RentalStatus status;
}
