package com.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.BookDTO;
import com.dto.BookTypeDTO;
import com.dto.GenericResponseDTO;
import com.entity.BookEntity;
import com.entity.BookTypeEntity;
import com.records.BookRecord;
import com.repository.BookRepository;
import com.repository.BookTypeRepository;
import com.service.BookService;
import com.util.BeanUtil;

@Service
@Scope("prototype")
public class BookServiceImpl implements BookService{

	private final Log log = LogFactory.getLog(getClass());
	
	private BookRepository bookRepository;
	private BookTypeRepository bookTypeRepository;
	
	public BookServiceImpl(BookRepository repository, BookTypeRepository bookTypeRepository) {
		super();
		this.bookRepository = repository;
		this.bookTypeRepository = bookTypeRepository;
	}

	@Override
	public GenericResponseDTO<List<BookDTO>> findByBookTypeId(long bookTypeId) {
		List<BookDTO> dtos = new ArrayList<>();
		Optional<BookTypeEntity> bookTypeOpt = this.bookTypeRepository.findById(bookTypeId);
		if( bookTypeOpt.isPresent() ) {
			List<BookEntity> books = this.bookRepository.findByBookType(bookTypeOpt.get());
			if( !books.isEmpty() ) {
				extractDTOs(dtos, books);
				return GenericResponseDTO.success(dtos);				
			}
		}else {
			return GenericResponseDTO.empty("Books type not found.");
		}
		return GenericResponseDTO.empty("Books data by type id, not found.");
	}

	@Override
	public GenericResponseDTO<List<BookDTO>> findByTitleLike(String title) {
		List<BookDTO> dtos = new ArrayList<>();
		List<BookEntity> books = this.bookRepository.findByTitleLike(title);
		if(!books.isEmpty()) {
			extractDTOs(dtos, books);
			return GenericResponseDTO.success(dtos);
		}
		return GenericResponseDTO.empty("Books data by title, not found.");
	}

	@Transactional
	@Override
	public GenericResponseDTO<BookDTO> createOrUpdate(final BookRecord bookRecord) {
		try {
			Optional<BookTypeEntity> bookTypeOp = this.bookTypeRepository.findById(bookRecord.bookTypeId());
			BookEntity entity = new BookEntity();
			BeanUtil.copyProperties(bookRecord, entity);
			entity.setBookType(bookTypeOp.get());
			entity.setQuantityAvailable(bookRecord.quantityOwned());
			entity = this.bookRepository.save(entity);
			BookDTO dto = new BookDTO();
			BeanUtil.copyProperties(entity, dto);
			extractBookTypeDTO(entity, dto);
			return GenericResponseDTO.success(dto);
		} catch (Exception e) {
			log.error("Error executing {}", e);
			List<String> message = new ArrayList<String>();
			message.add("Error trying to create book.");
			return GenericResponseDTO.error(message);
		}
	}

	@Override
	public GenericResponseDTO<BookDTO> findById(long id) {
		Optional<BookEntity> bookOp = this.bookRepository.findById(id);
		if( bookOp.isPresent() ) {
			BookDTO dto = new BookDTO();
			BeanUtil.copyProperties(bookOp.get(), dto);
			extractBookTypeDTO(bookOp.get(), dto);
			return GenericResponseDTO.success(dto);
		}
		return GenericResponseDTO.empty("Book data by id, not found.");
	}
	
	@Override
	public GenericResponseDTO<List<BookDTO>> findByDescriptionLike(String description) {
		List<BookDTO> dtos = new ArrayList<>();
		List<BookEntity> books = this.bookRepository.findByDescriptionLike(description);
		if( !books.isEmpty() ) {
			extractDTOs(dtos, books);
			return GenericResponseDTO.success(dtos);
		}
		return GenericResponseDTO.empty("Book data by description, not found.");
	}
	
	@Override
	public GenericResponseDTO<List<BookDTO>> findAllOwned() {
		List<BookDTO> dtos = new ArrayList<>();
		List<BookEntity> bookEntities = this.bookRepository.findAll();
		if( !bookEntities.isEmpty() ) {
			extractDTOs(dtos, bookEntities);
			return GenericResponseDTO.success(dtos);
		}
		return GenericResponseDTO.empty("Books data not found.");
	}
	
	@Override
	public GenericResponseDTO<Page<BookDTO>> findAllOwnedPagination(int offset, int pageSize) {
		Page<BookDTO> bookDtos;
		Page<BookEntity> bookEntities = this.bookRepository.findAll(PageRequest.of(offset, pageSize));
		if( !bookEntities.isEmpty() ) {
			bookDtos = bookEntities.map(i->{
					BookDTO dto = new BookDTO();
					BeanUtil.copyProperties(i, dto);
					extractBookTypeDTO(i, dto);
					return dto;
				}
			);
			return GenericResponseDTO.success(bookDtos);
		}
		return GenericResponseDTO.empty("Books data not found.");
	}
	
	private void extractDTOs(final List<BookDTO> bookDTOs, final List<BookEntity> l) {
		l.stream().forEach(b-> {
				BookDTO dto = new BookDTO();
				BeanUtil.copyProperties(b, dto);
				extractBookTypeDTO(b, dto);
				bookDTOs.add(dto);
		} );
	}

	private void extractBookTypeDTO(final BookEntity entity, final BookDTO dto) {
		BookTypeDTO bookTypeDTO = new BookTypeDTO();
		BeanUtil.copyProperties(entity.getBookType(), bookTypeDTO);
		dto.setBookType(bookTypeDTO);
	}

	@Override
	public GenericResponseDTO<List<BookDTO>> findAllAvailable() {
		List<BookDTO> dtos = new ArrayList<>();
		List<BookEntity> bookEntities = this.bookRepository.findAllAvailable();
		if( !bookEntities.isEmpty() ) {
			extractDTOs(dtos, bookEntities);
			return GenericResponseDTO.success(dtos);
		}
		return GenericResponseDTO.empty("Available books data, not found.");
	}

	@Override
	public GenericResponseDTO<List<BookDTO>> findAllUnavailable() {
		List<BookDTO> dtos = new ArrayList<>();
		List<BookEntity> bookEntities = this.bookRepository.findAllUnavailable();
		if( !bookEntities.isEmpty() ) {
			extractDTOs(dtos, bookEntities);
			return GenericResponseDTO.success(dtos);
		}
		return GenericResponseDTO.empty("Unavailable books data, not found.");
	}

}
