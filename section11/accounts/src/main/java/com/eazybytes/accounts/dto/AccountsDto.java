package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Account to hold Account information"
)
public class AccountsDto {

    @Schema(
            description = "Account Number of the customer",
            example = "1234567890"
    )
    @NotEmpty(message = "Account Number should not be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account Number should have 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Account Type of the customer",
            example = "Savings"
    )
    @NotEmpty(message = "Account Type should not be empty")
    private String accountType;

    @Schema(
            description = "Branch Address of the customer",
            example = "123 Main Street, New York"
    )
    @NotEmpty(message = "Branch Address should not be empty")
    private String branchAddress;

    @Schema(description = "Communication Status of Account Holder", example = "true")
    private Boolean communicationSw;
}
