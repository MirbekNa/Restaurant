package peaksoft.dto.dtoUser;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import peaksoft.enums.Role;
import peaksoft.validation.email.EmailValidation;
import peaksoft.validation.phoneNumber.PhoneNumberValid;
import peaksoft.validation.passwordAnotation.PasswordValidator;


import java.time.LocalDate;

@Builder
public record UserRequest(
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        @NotNull
        LocalDate dateOfBirth,
       @EmailValidation
        String email,
        @PasswordValidator
        String password,
        @PhoneNumberValid
        String phoneNumber,
        @NotNull
        Role role,
        @NotNull
        int experience) {
    public UserRequest {
    }
}
