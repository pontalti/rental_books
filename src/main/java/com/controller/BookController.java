package com.controller;

import java.util.List;

import org.springframework.data.domain.Page;
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

import com.dto.BookDTO;
import com.dto.GenericResponseDTO;
import com.records.BookRecord;
import com.records.MessageRecord;
import com.service.BookService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/book")
public class BookController {
	
	private BookService bookService;
	
	public BookController(BookService bookService) {
		super();
		this.bookService = bookService;
	}

	@GetMapping(value = {"", "/"} )
	public String home() {
		return "Book Home!!!!! - :)";
	}
	
	@PostMapping(value = { "", "/" }, consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<BookDTO>> createBook(@RequestBody(required = true) 
																				@NotNull(message = "Book data cannot be null.") 
																				@NotEmpty(message = "Book data cannot be empty.") 
																				@Valid BookRecord book) {
		ResponseEntity<GenericResponseDTO<BookDTO>> response = null;
		var genericResponse = this.bookService.createOrUpdate(book);
		if( genericResponse.isSuccess() ) {
			response = new ResponseEntity<GenericResponseDTO<BookDTO>>(genericResponse, HttpStatus.OK);			
		}else {
			response = new ResponseEntity<GenericResponseDTO<BookDTO>>(genericResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return response;
	}
	
	@GetMapping(value = { "/all/owned", "/all/owned/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<List<BookDTO>>> findAllOwned() {
		var genericResponse = this.bookService.findAllOwned();
		var response = new ResponseEntity<GenericResponseDTO<List<BookDTO>>>(genericResponse, HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = { "/all/owned/pagination/{offset}/{pageSize}", "/all/owned/pagination/{offset}/{pageSize}/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<Page<BookDTO>>> findAllOwnedPagination(@PathVariable int offset, @PathVariable int pageSize) {
		var genericResponse = this.bookService.findAllOwnedPagination(offset, pageSize);
		var response = new ResponseEntity<GenericResponseDTO<Page<BookDTO>>>(genericResponse, HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = { "/all/available", "/all/available/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<List<BookDTO>>> findAllAvailable() {
		var genericResponse = this.bookService.findAllAvailable();
		var response = new ResponseEntity<GenericResponseDTO<List<BookDTO>>>(genericResponse, HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = { "/all/unavailable", "/all/unavailable/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<List<BookDTO>>> findAllUnavailable() {
		var genericResponse = this.bookService.findAllUnavailable();
		var response = new ResponseEntity<GenericResponseDTO<List<BookDTO>>>(genericResponse, HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = { "/description", "/description/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<List<BookDTO>>> findbyDescription(@RequestBody(required = true) 
																								@Valid MessageRecord message ) {
		var genericResponse = this.bookService.findByDescriptionLike(message.text());
		var response = new ResponseEntity<GenericResponseDTO<List<BookDTO>>>(genericResponse, HttpStatus.OK);
		return response;
	}

	@GetMapping(value = { "/id/{id}", "/id/{id}/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<BookDTO>> findbyId(@PathVariable("id") long id) {
		var genericResponse = this.bookService.findById(id);
		var response = new ResponseEntity<GenericResponseDTO<BookDTO>>(genericResponse, HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = { "/title", "/title/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<List<BookDTO>>> findbyTitle(@RequestBody(required = true) 
																						@Valid MessageRecord message ) {
		var genericResponse = this.bookService.findByTitleLike(message.text());
		var response = new ResponseEntity<GenericResponseDTO<List<BookDTO>>>(genericResponse, HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = { "/bookTypeId/{bookTypeId}", "/bookTypeId/{bookTypeId}/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<List<BookDTO>>> findbyBookTypeId(@PathVariable("bookTypeId") long bookTypeId) {
		var genericResponse = this.bookService.findByBookTypeId(bookTypeId);
		var response = new ResponseEntity<GenericResponseDTO<List<BookDTO>>>(genericResponse, HttpStatus.OK);
		return response;
	}
	
}