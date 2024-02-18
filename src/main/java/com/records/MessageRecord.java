package com.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record MessageRecord(@NotNull(message = "Text attribute cannot be null.")
							@NotEmpty(message = "Text attribute cannot be empty.") 
							@NotBlank(message = "Text attribute cannot be black.")String text) {

}
