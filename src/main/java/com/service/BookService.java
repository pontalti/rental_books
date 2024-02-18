package com.service;

import java.util.List;

import com.dto.BookDTO;
import com.dto.GenericResponseDTO;
import com.records.BookRecord;

public interface BookService {
	
	public GenericResponseDTO<List<BookDTO>> findByDescriptionLike(final String description);

	public GenericResponseDTO<BookDTO> createOrUpdate(final BookRecord bookType);
	
	public GenericResponseDTO<BookDTO> findById(final long id);
	
	public GenericResponseDTO<List<BookDTO>> findByBookTypeId(final long bookTypeId);
	
    public GenericResponseDTO<List<BookDTO>> findByTitleLike(final String title);
    
    public GenericResponseDTO<List<BookDTO>> findAllOwned();

    public GenericResponseDTO<List<BookDTO>> findAllAvailable();
    
    public GenericResponseDTO<List<BookDTO>> findAllUnavailable();
    
}
