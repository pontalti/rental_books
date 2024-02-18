package com.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.BookTypeDTO;
import com.dto.GenericResponseDTO;
import com.entity.BookTypeEntity;
import com.records.BookTypeRecord;
import com.repository.BookTypeRepository;
import com.service.BookTypeService;
import com.util.BeanUtil;

@Service
@Scope("prototype")
public class BookTypeServiceImpl implements BookTypeService {

	private final Log log = LogFactory.getLog(getClass());
	
	private BookTypeRepository bookTypeRepository;
	
	public BookTypeServiceImpl(BookTypeRepository bookTypeRepository) {
		super();
		this.bookTypeRepository = bookTypeRepository;
	}

	@Override
	public GenericResponseDTO<List<BookTypeDTO>> findByType(final String type) {
		List<BookTypeDTO> dtos = new ArrayList<>();
		List<BookTypeEntity> bookTypeEntities = this.bookTypeRepository.findByType(type);
		if( !bookTypeEntities.isEmpty() ) {
			extractDTOs(dtos, bookTypeEntities);
			return GenericResponseDTO.success(dtos);
		}
		return GenericResponseDTO.empty("Book type data by type, not found.");
	}

	@Override
	public GenericResponseDTO<List<BookTypeDTO>> findByDescription(final String description) {
		List<BookTypeDTO> dtos = new ArrayList<>();
		List<BookTypeEntity> bookTypeEntities = this.bookTypeRepository.findByDescription(description);
		if( !bookTypeEntities.isEmpty() ) {
			extractDTOs(dtos, bookTypeEntities);
			return GenericResponseDTO.success(dtos);
		}
		return GenericResponseDTO.empty("Book type data by description, not found.");
	}

	@Override
	public GenericResponseDTO<List<BookTypeDTO>> findAll() {
		List<BookTypeDTO> dtos = new ArrayList<>();
		List<BookTypeEntity> bookTypeEntities = this.bookTypeRepository.findAll();
		if( !bookTypeEntities.isEmpty() ) {
			extractDTOs(dtos, bookTypeEntities);
			return GenericResponseDTO.success(dtos);
		}
		return GenericResponseDTO.empty("Book type data, not found.");
	}
	
	@Transactional
	@Override
	public GenericResponseDTO<BookTypeDTO> createOrUpdate(final BookTypeRecord bookRecord) {
		try {
			BookTypeEntity entity = new BookTypeEntity();
			BeanUtil.copyProperties(bookRecord, entity);
			entity = this.bookTypeRepository.save(entity);
			BookTypeDTO dto = new BookTypeDTO();
			BeanUtil.copyProperties(entity, dto);
			return GenericResponseDTO.success(dto);
		} catch (Exception e) {
			log.error("Error executing {}", e);
			List<String> message = new ArrayList<String>();
			message.add("Error trying to create book type.");
			return GenericResponseDTO.error(message);
		}
	}
	
	@Override
	public GenericResponseDTO<BookTypeDTO> findbyId(long id) {
		Optional<BookTypeEntity> bookTypeOp = this.bookTypeRepository.findById(id);
		if( bookTypeOp.isPresent() ) {
			BookTypeDTO dto = new BookTypeDTO();
			BeanUtil.copyProperties(bookTypeOp.get(), dto);
			return GenericResponseDTO.success(dto);
		}
		return GenericResponseDTO.empty("Book type data, not found.");
	}
	
	private void extractDTOs(final List<BookTypeDTO> dtos, final List<BookTypeEntity> typeEntities) {
		typeEntities
			.stream()
			.forEach(t->{

					BookTypeDTO dto = new BookTypeDTO();
					BeanUtil.copyProperties(t, dto);
					dtos.add(dto);

			});
	}

}
