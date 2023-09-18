package peaksoft.dto.dtoUser;

import lombok.Builder;
import peaksoft.enums.Role;

import java.time.LocalDate;

@Builder
public record UserResponse(Long id,
                           String firstName,
                           String lastName,
                           LocalDate dateOfBirth,
                           String email,
                           String password,
                           String phoneNumber,
                           Role role,
                           int experience) {
    public UserResponse {
    }
}
