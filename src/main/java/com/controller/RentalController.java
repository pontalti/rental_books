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

import com.dto.GenericResponseDTO;
import com.dto.RentalDTO;
import com.records.RentalRecord;
import com.records.RentalReturnRecord;
import com.service.RentalService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/rental")
public class RentalController {

	private RentalService rentalService;
	
	public RentalController(RentalService rentalService) {
		super();
		this.rentalService = rentalService;
	}
	
	@GetMapping(value = {"", "/"} )
	public String home() {
		return "Rental book Home!!!!! - :)";
	}

	@PostMapping(value = { "order/create", "order/create/" }, consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<RentalDTO>> createRentalOrder(@RequestBody(required = true) 
															@NotNull(message = "Rental data cannot be null.") 
															@NotEmpty(message = "Rental data cannot be empty.") 
															@Valid RentalRecord rentalRecord) {
		ResponseEntity<GenericResponseDTO<RentalDTO>> response = null;
		var genericResponse = this.rentalService.createRentalOrder(rentalRecord);
		if( genericResponse.isSuccess() ) {
			response = new ResponseEntity<GenericResponseDTO<RentalDTO>>(genericResponse, HttpStatus.OK);			
		}else {
			response = new ResponseEntity<GenericResponseDTO<RentalDTO>>(genericResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return response;
	}
	
	@PostMapping(value = { "order/return", "order/return/" }, consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<RentalDTO>> createRentalReturn(@RequestBody(required = true) 
															@NotNull(message = "Rental return data cannot be null.") 
															@NotEmpty(message = "Rental return data cannot be empty.") 
															@Valid RentalReturnRecord rentalReturnRecord) {
		ResponseEntity<GenericResponseDTO<RentalDTO>> response = null;
		var genericResponse = this.rentalService.createRentalReturn(rentalReturnRecord);
		if( genericResponse.isSuccess() ) {
			response = new ResponseEntity<GenericResponseDTO<RentalDTO>>(genericResponse, HttpStatus.OK);			
		}else {
			response = new ResponseEntity<GenericResponseDTO<RentalDTO>>(genericResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return response;
	}
	
	@GetMapping(value = { "/all", "/all/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<List<RentalDTO>>> findAll() {
		var dtos = this.rentalService.findAll();
		var response = new ResponseEntity<GenericResponseDTO<List<RentalDTO>>>(dtos, HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = { "/id/{id}", "/id/{id}/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<RentalDTO>> findbyId(@PathVariable("id") long id) {
		var dto = this.rentalService.findbyId(id);
		var response = new ResponseEntity<GenericResponseDTO<RentalDTO>>(dto, HttpStatus.OK);
		return response;
	}
	
}
