package com.service;

import java.util.List;

import com.dto.CustomerDTO;
import com.dto.GenericResponseDTO;
import com.enums.RentalStatus;
import com.records.CustomerRecord;

public interface CustomerService {

	public GenericResponseDTO<CustomerDTO> findbyId(final long id);

	public GenericResponseDTO<List<CustomerDTO>> findByName(final String name);
	
	public GenericResponseDTO<List<CustomerDTO>> findAll();

	public GenericResponseDTO<List<CustomerDTO>> findAllCustomersbyRentalStatus(final RentalStatus status);
	
	public GenericResponseDTO<CustomerDTO> createOrUpdate(final CustomerRecord customer);
    
}
