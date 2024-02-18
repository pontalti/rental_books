package com.service;

import java.util.List;

import com.dto.BookTypeDTO;
import com.dto.GenericResponseDTO;
import com.records.BookTypeRecord;

public interface BookTypeService {

	public GenericResponseDTO<BookTypeDTO> findbyId(final long id);

	public GenericResponseDTO<List<BookTypeDTO>> findByType(final String type);

	public GenericResponseDTO<List<BookTypeDTO>> findByDescription(final String description);

	public GenericResponseDTO<List<BookTypeDTO>> findAll();

	public GenericResponseDTO<BookTypeDTO> createOrUpdate(final BookTypeRecord bookRecord);

}