package com.service;

import java.util.List;

import com.dto.GenericResponseDTO;
import com.dto.RentalDTO;
import com.records.RentalRecord;
import com.records.RentalReturnRecord;

public interface RentalService {

	public GenericResponseDTO<RentalDTO> findbyId(final long id);
	
	public GenericResponseDTO<List<RentalDTO>> findAll();
	
	public GenericResponseDTO<RentalDTO> createRentalOrder(final RentalRecord rentalRecord);
	
	public GenericResponseDTO<RentalDTO> createRentalReturn(final RentalReturnRecord rentalReturnRecord);
}
