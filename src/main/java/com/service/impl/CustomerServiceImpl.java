package com.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.BookDTO;
import com.dto.CustomerDTO;
import com.dto.GenericResponseDTO;
import com.dto.RentalBookDTO;
import com.dto.RentalDTO;
import com.entity.CustomerEntity;
import com.enums.RentalStatus;
import com.records.CustomerRecord;
import com.repository.CustomerRepository;
import com.service.CustomerService;
import com.util.BeanUtil;

@Service
@Scope("prototype")
public class CustomerServiceImpl implements CustomerService{

	private final Log log = LogFactory.getLog(getClass());
	
	private CustomerRepository customerRepository;
	
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	@Override
	public GenericResponseDTO<List<CustomerDTO>> findByName(final String name) {
		List<CustomerDTO> dtos = new ArrayList<>();
		List<CustomerEntity> customerEntities = this.customerRepository.findByName(name);
		if( !customerEntities.isEmpty() ) {
			extractDTOs(dtos, customerEntities);
			return GenericResponseDTO.success(dtos);
		}
		return GenericResponseDTO.empty("Customers by name "+ name +", not found.");
	}

	@Override
	public GenericResponseDTO<List<CustomerDTO>> findAllCustomersbyRentalStatus(final RentalStatus status) {
		List<CustomerDTO> dtos = new ArrayList<>();
		List<CustomerEntity> customerEntities = this.customerRepository.findAllCustomersbyRentalStatus(status);
		if( !customerEntities.isEmpty() ) {
			customerEntities.parallelStream().forEach(c-> c.getRentalEntities().size() );
			extractDTOs(dtos, customerEntities);
			return GenericResponseDTO.success(dtos);
		}
		return GenericResponseDTO.empty("Customers by rental status "+ status.name() +", not found.");
	}

	@Override
	public GenericResponseDTO<List<CustomerDTO>> findAll() {
		List<CustomerDTO> dtos = new ArrayList<>();
		List<CustomerEntity> customerEntities = this.customerRepository.findAll();
		if( !customerEntities.isEmpty() ) {
			extractDTOs(dtos, customerEntities);
			return GenericResponseDTO.success(dtos);
		}
		return GenericResponseDTO.empty("Customers not found.");
	}

	@Override
	public GenericResponseDTO<CustomerDTO> findbyId(final long id) {
		Optional<CustomerEntity> customerOp = this.customerRepository.findById(id);
		if( customerOp.isPresent() ) {
			CustomerDTO dto = new CustomerDTO();
			BeanUtil.copyProperties(customerOp.get(), dto);
			return GenericResponseDTO.success(dto);
		}
		return GenericResponseDTO.empty("Customer not found.");
	}

	@Transactional
	@Override
	public GenericResponseDTO<CustomerDTO> createOrUpdate(CustomerRecord customer) {
		try {
			CustomerEntity entity = new CustomerEntity();
			BeanUtil.copyProperties(customer, entity);
			entity = this.customerRepository.save(entity);
			CustomerDTO dto = new CustomerDTO();
			BeanUtil.copyProperties(entity, dto);
			return GenericResponseDTO.success(dto);
		} catch (Exception e) {
			log.error("Error executing {}", e);
			List<String> message = new ArrayList<String>();
			message.add("Error trying to create customer.");
			return GenericResponseDTO.error(message);
		}
	}
	
	private void extractDTOs(final List<CustomerDTO> dtos, final List<CustomerEntity> customerEntities) {
		customerEntities
			.stream()
			.forEach(c->{
					CustomerDTO dto = new CustomerDTO();
					BeanUtil.copyProperties(c, dto);
					
					if( !c.getRentalEntities().isEmpty()) {
						List<RentalDTO> rentalEntities = new ArrayList<>();
						c.getRentalEntities().parallelStream().forEach(r-> {
							RentalDTO rentalDTO = new RentalDTO();
							BeanUtil.copyProperties(r, rentalDTO);
							
							if( !r.getRentalBookEntities().isEmpty() ) {
								List<RentalBookDTO> rentalBookDTOs = new ArrayList<RentalBookDTO>();
								r.getRentalBookEntities().parallelStream().forEach(rentalBookEntity->{
									
									RentalBookDTO rentalBookDTO = new RentalBookDTO();
									BeanUtil.copyProperties(rentalBookEntity, rentalBookDTO);
									
									BookDTO bookDTO = new BookDTO();
									BeanUtil.copyProperties(rentalBookEntity.getBook(), bookDTO);
									
									rentalBookDTO.setBook(bookDTO);
									rentalBookDTOs.add(rentalBookDTO);
									
								});								
								rentalDTO.setRentalBook(rentalBookDTOs);
							}
							rentalEntities.add(rentalDTO);
						});
						dto.setRentedBooks(rentalEntities);
					}
					
					dtos.add(dto);
			});
	}

}
