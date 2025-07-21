package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account details"
)
public class CustomerDto {


    @Schema(
            description = "Name of the customer",
            example = "Hicham Azeroual"

    )
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 5,max = 30, message = "Name should have at least 2 characters")
    private String name;

    @Schema(
            description = "Email of the customer",
            example = "hicham.azeroual@in.com"
    )
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(
            description = "Mobile Number of the customer",
            example = "1234567890"
    )
    @NotEmpty(message = "Mobile Number should not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number should have 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Account Details of the customer"
    )
    private AccountsDto accountsDto;
}
