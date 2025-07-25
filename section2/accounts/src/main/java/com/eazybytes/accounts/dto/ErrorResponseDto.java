package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
public class ErrorResponseDto {

     @Schema(
             description = "API path where the error occurred"
     )
     private String apiPath;
     @Schema(
             description = "HTTP status code of the error"
     )
     private HttpStatus errorCode;
     @Schema(
             description = "Error message of the error"
     )
     private String errorMessage;
     @Schema(
             description = "Timestamp of the error")
     private LocalDateTime errorTime;


}
