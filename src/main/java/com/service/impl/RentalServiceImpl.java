package com.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.BookDTO;
import com.dto.CustomerDTO;
import com.dto.GenericResponseDTO;
import com.dto.RentalBookDTO;
import com.dto.RentalDTO;
import com.entity.BookEntity;
import com.entity.CustomerEntity;
import com.entity.RentalBookEntity;
import com.entity.RentalEntity;
import com.enums.RentalStatus;
import com.records.RentalBookRecord;
import com.records.RentalBookReturnRecord;
import com.records.RentalRecord;
import com.records.RentalReturnRecord;
import com.repository.BookRepository;
import com.repository.CustomerRepository;
import com.repository.RentalRepository;
import com.service.RentalService;
import com.util.BeanUtil;

@Service
@Scope("prototype")
public class RentalServiceImpl implements RentalService{

	private RentalRepository rentalRepository;
	private CustomerRepository customerRepository;
	private BookRepository bookRepository;
	
	public RentalServiceImpl(RentalRepository rentalRepository, CustomerRepository customerRepository,
								BookRepository bookRepository) {
		super();
		this.rentalRepository = rentalRepository;
		this.customerRepository = customerRepository;
		this.bookRepository = bookRepository;
	}
	
	@Transactional
	@Override
	public GenericResponseDTO<RentalDTO> createRentalOrder(final RentalRecord rentalRecord) {
		RentalEntity rentalEntity = new RentalEntity();
		rentalEntity.setStatus(RentalStatus.OPENED);
		RentalDTO rentalDTO = null;
		Optional<CustomerEntity> customerOp = this.customerRepository.findById(rentalRecord.customerID());
		Boolean error = Boolean.FALSE;
		List<String> message = new ArrayList<String>();
		if( customerOp.isPresent() ) {
			rentalEntity.setCustomer(customerOp.get());
		}else {
			error = Boolean.TRUE;
			message.add("Customer not found.");
		}
		
		Optional<RentalBookRecord> rentalBookRecordOp = rentalRecord.rentalBooks()
																	.stream()
																	.filter(rentalBookRecord -> rentalBookRecord.quantity() < 1)
																	.findAny();
		if (rentalBookRecordOp.isPresent()) {
			error = Boolean.TRUE;
			message.add("Quantity by book cannot be less than 1, please check your return order.");
		}
		
		if( !error ) {
			List<RentalBookEntity> rentalBookEntities = new ArrayList<>();
			if( !rentalRecord.rentalBooks().isEmpty() ) {
				for( RentalBookRecord rentalBookRecord : rentalRecord.rentalBooks() ){
					RentalBookEntity rentalBookEntity = new RentalBookEntity();
					Optional<BookEntity> bookOp =  this.bookRepository.findById(rentalBookRecord.bookID());
					if( bookOp.isPresent() ) {
						rentalBookEntity.setBook(bookOp.get());
						if(bookOp.get().getQuantityAvailable() >= rentalBookRecord.quantity() ) {
							rentalBookEntity.setQuantity(rentalBookRecord.quantity());
							int quantity = rentalBookEntity.getBook().getQuantityAvailable();
							quantity-=rentalBookEntity.getQuantity();
							rentalBookEntity.getBook().setQuantityAvailable(quantity);
							rentalBookEntities.add(rentalBookEntity);
							rentalBookEntity.setRental(rentalEntity);
						}else {
							error = Boolean.TRUE;
							message.add("Book id "+ rentalBookRecord.bookID() +" is not available for rent.");
						}
					}else {
						error = Boolean.TRUE;
						message.add("Book id "+ rentalBookRecord.bookID() +" not found.");
					}
				}
				if( !error ) {
					rentalEntity.setRentalBookEntities(rentalBookEntities);
	
					rentalEntity = this.rentalRepository.save(rentalEntity);
					rentalDTO = extractDTO(rentalEntity);
				}
			}else {
				error = Boolean.TRUE;
				message.add("Rental Books data not found.");
			}
		}
		if( error ) {
			return GenericResponseDTO.error(message);
		}
		return GenericResponseDTO.success(rentalDTO);
	}
	
	@Transactional
	@Override
	public GenericResponseDTO<RentalDTO> createRentalReturn(final RentalReturnRecord rentalReturnRecord) {
		RentalDTO rentalDTO = null;
		Optional<RentalEntity> rentalEntityOp = this.rentalRepository.findById(rentalReturnRecord.rentalOrderId());
		Boolean error = Boolean.FALSE;
		List<String> message = new ArrayList<String>();
		
		Optional<RentalBookReturnRecord> rentalBookReturnRecordOp = rentalReturnRecord.rentalBooks()
																	.stream()
																	.filter(rentalBookRecord -> rentalBookRecord.quantity() < 0)
																	.findAny();
		if (rentalBookReturnRecordOp.isPresent()) {
			error = Boolean.TRUE;
			message.add("Quantity by book cannot be less than 0, please check your order.");
		}
		
		if (!error) {
			if (rentalEntityOp.isPresent()) {
				RentalEntity rentalEntity = rentalEntityOp.get();
				if (rentalEntity.getStatus().equals(RentalStatus.OPENED)) {
					for (RentalBookReturnRecord rentalBookReturnRecord : rentalReturnRecord.rentalBooks()) {
						for (RentalBookEntity rentalBookEntity : rentalEntity.getRentalBookEntities()) {
							if (rentalBookEntity.getId() == rentalBookReturnRecord.rentalBookID()) {
								if (rentalBookEntity.getBook().getQuantityAvailable() >= 0) {
									int quantityAvailable = rentalBookEntity.getBook().getQuantityAvailable();
									quantityAvailable += rentalBookReturnRecord.quantity();

									int rentalBookQuantity = rentalBookEntity.getQuantity();
									rentalBookQuantity -= rentalBookReturnRecord.quantity();

									if (quantityAvailable >= 0) {
										rentalBookEntity.getBook().setQuantityAvailable(quantityAvailable);
									}
									if (rentalBookQuantity >= 0) {
										rentalBookEntity.setQuantity(rentalBookQuantity);
									}
								}
							}
						}
					}
					Optional<RentalBookEntity> rentalBookEntityOp = rentalEntity.getRentalBookEntities().stream()
							.filter(rentalBookEntity -> rentalBookEntity.getQuantity() > 0).findAny();
					if (!rentalBookEntityOp.isPresent()) {
						rentalEntity.setStatus(RentalStatus.CLOSED);
					}
					rentalEntity = this.rentalRepository.save(rentalEntity);
					rentalDTO = extractDTO(rentalEntity);
				} else {
					error = Boolean.TRUE;
					message.add("Rental order is already closed.");
				}
			} else {
				error = Boolean.TRUE;
				message.add("Rental order not found.");
			} 
		}
		if( error ) {
			return GenericResponseDTO.error(message);
		}
		return GenericResponseDTO.success(rentalDTO);
	}

	@Override
	public GenericResponseDTO<List<RentalDTO>> findAll() {
		List<RentalDTO> dtos = new ArrayList<>();
		List<RentalEntity> rentalEntities = this.rentalRepository.findAll();
		if( !rentalEntities.isEmpty() ) {
			extractDTOs(dtos, rentalEntities);
			return GenericResponseDTO.success(dtos);
		}
		return GenericResponseDTO.empty("Rental data not found.");
	}

	@Override
	public GenericResponseDTO<RentalDTO> findbyId(final long id) {
		Optional<RentalEntity> rentalOp = this.rentalRepository.findById(id);
		if( rentalOp.isPresent() ) {
			RentalDTO dto = extractDTO(rentalOp.get());
			return GenericResponseDTO.success(dto);
		}
		return GenericResponseDTO.empty("Customer not found.");
	}
	
	private void extractDTOs(final List<RentalDTO> dtos, final List<RentalEntity> rentalEntities) {
		rentalEntities
			.stream()
			.forEach(entity->{
				RentalDTO dto = extractDTO(entity);
				dtos.add(dto);
			});
	}
	
	private RentalDTO extractDTO(final RentalEntity entity) {
		RentalDTO dto = new RentalDTO();
		List<RentalBookDTO> rentalBookDTOs = new ArrayList<>();
		BeanUtil.copyProperties(entity, dto);
		
		entity.getRentalBookEntities().stream().forEach(rentalBookEntities->{
			RentalBookDTO rentalBookDTO = new RentalBookDTO();
			BeanUtil.copyProperties(rentalBookEntities, rentalBookDTO);
			
			BookDTO bookDTO = new BookDTO();
			BeanUtil.copyProperties(rentalBookEntities.getBook(), bookDTO);
			rentalBookDTO.setBook(bookDTO);
			
			rentalBookDTOs.add(rentalBookDTO);
		});
		dto.setRentalBook(rentalBookDTOs);					
		
		CustomerDTO customerDTO = new CustomerDTO();
		BeanUtil.copyProperties(entity.getCustomer(), customerDTO);
		return dto;
	}

}
