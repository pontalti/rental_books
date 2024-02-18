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

import com.dto.CustomerDTO;
import com.dto.GenericResponseDTO;
import com.enums.RentalStatus;
import com.records.CustomerRecord;
import com.records.MessageRecord;
import com.service.CustomerService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	private CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}

	@GetMapping(value = { "", "/" })
	public String home() {
		return "Customer Home!!!!! - :)";
	}

	@PostMapping(value = { "", "/" }, consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<CustomerDTO>> createCustomer(@RequestBody(required = true) 
																	@NotNull(message = "Customer data cannot be null.") 
																	@NotEmpty(message = "Customer data cannot be empty.") 
																	@Valid CustomerRecord customer) {
		ResponseEntity<GenericResponseDTO<CustomerDTO>> response = null;
		var genericResponse = this.customerService.createOrUpdate(customer);
		if( genericResponse.isSuccess() ) {
			response = new ResponseEntity<GenericResponseDTO<CustomerDTO>>(genericResponse, HttpStatus.OK);			
		}else {
			response = new ResponseEntity<GenericResponseDTO<CustomerDTO>>(genericResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return response;
	}

	@GetMapping(value = { "/all", "/all/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<List<CustomerDTO>>> findAll() {
		var genericResponse = this.customerService.findAll();
		var	response = new ResponseEntity<GenericResponseDTO<List<CustomerDTO>>>(genericResponse, HttpStatus.OK);
		return response;
	}

	@GetMapping(value = { "/name", "/name/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<List<CustomerDTO>>> findbyName(@RequestBody(required = true) 
																							@Valid MessageRecord message ) {
			var genericResponse = this.customerService.findByName(message.text());
			var response = new ResponseEntity<GenericResponseDTO<List<CustomerDTO>>>(genericResponse, HttpStatus.OK);
			return response;
	}

	@GetMapping(value = { "/id/{id}", "/id/{id}/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<CustomerDTO>> findbyId(@PathVariable("id") long id) {
		var genericResponse = this.customerService.findbyId(id);
		var response = new ResponseEntity<GenericResponseDTO<CustomerDTO>>(genericResponse, HttpStatus.OK);
		return response;
	}

	@GetMapping(value = { "/rental/status/{status}", "/rental/status/{status}/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<GenericResponseDTO<List<CustomerDTO>>> findbyRentalStatus(@PathVariable("status") RentalStatus status) {
		var dtos = this.customerService.findAllCustomersbyRentalStatus(status);
		var response = new ResponseEntity<GenericResponseDTO<List<CustomerDTO>>>(dtos, HttpStatus.OK);
		return response;
	}

}
