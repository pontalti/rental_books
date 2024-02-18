package com.dto;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class GenericResponseDTO<T> {
	private boolean success;
	private List<String> message;
	private T data;

	public static <T> GenericResponseDTO<T> empty(final String message) {
		return GenericResponseDTO
				.<T>builder()
				.message(Arrays.asList(message))
				.data(null)
				.success(true)
				.build();
	}

	public static <T> GenericResponseDTO<T> success(final T data) {
		return GenericResponseDTO
					.<T>builder()
					.data(data)
					.success(true)
					.build();
	}

	public static <T> GenericResponseDTO<T> error(final List<String> errors) {
		return GenericResponseDTO
					.<T>builder()
					.message(errors)
					.success(false)
					.build();
	}

}
