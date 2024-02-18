package com.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dto.BookTypeDTO;
import com.dto.GenericResponseDTO;
import com.records.BookTypeRecord;
import com.records.MessageRecord;
import com.service.BookTypeService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/bookType")
public class BookTypeController {
	
	private BookTypeService bookTypeService;
	
	public BookTypeController(BookTypeService bookTypeService) {
		super();
		this.bookTypeService = bookTypeService;
	}

	@GetMapping(value = {"", "/"} )
	public String home() {
		return "Book type Home!!!!! - :)";
	}
	
	@PostMapping(value = { "", "/" }, consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<BookTypeDTO>> createBookType(@RequestBody(required = true) 
																	@NotNull(message = "Book type data cannot be null.") 
																	@NotEmpty(message = "Book type data cannot be empty.") 
																	@Valid BookTypeRecord customer) {
		ResponseEntity<GenericResponseDTO<BookTypeDTO>> response = null;
		var genericResponse = this.bookTypeService.createOrUpdate(customer);
		if( genericResponse.isSuccess() ) {
			response = new ResponseEntity<GenericResponseDTO<BookTypeDTO>>(genericResponse, HttpStatus.OK);			
		}else {
			response = new ResponseEntity<GenericResponseDTO<BookTypeDTO>>(genericResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return response;
	}
	
	@GetMapping(value = { "/all", "/all/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<List<BookTypeDTO>>> findAll() {
		var genericResponse = this.bookTypeService.findAll();
		var response = new ResponseEntity<GenericResponseDTO<List<BookTypeDTO>>>(genericResponse, HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = { "/type", "/type/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<List<BookTypeDTO>>> findbyName(@RequestBody(required = true) 
																		@Valid MessageRecord message) {
		var genericResponse = this.bookTypeService.findByType(message.text());
		var response = new ResponseEntity<GenericResponseDTO<List<BookTypeDTO>>>(genericResponse, HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = { "/description", "/description/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<List<BookTypeDTO>>> findbyDescription(@RequestBody(required = true) 
																									@Valid MessageRecord message) {
		var genericResponse = this.bookTypeService.findByDescription(message.text());
		var response = new ResponseEntity<GenericResponseDTO<List<BookTypeDTO>>>(genericResponse, HttpStatus.OK);
		return response;
	}

	@GetMapping(value = { "/id/{id}", "/id/{id}/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<BookTypeDTO>> findbyId(@PathVariable("id") long id) {
		var genericResponse = this.bookTypeService.findbyId(id);
		var response = new ResponseEntity<GenericResponseDTO<BookTypeDTO>>(genericResponse, HttpStatus.OK);
		return response;
	}
	
}