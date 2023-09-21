package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoUser.PaginationResponse;
import peaksoft.dto.dtoUser.UserRequest;
import peaksoft.dto.dtoUser.UserResponse;
import peaksoft.exceptions.BadCredentialException;
import peaksoft.exceptions.BadRequestException;
import peaksoft.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserAPI {
    private final UserService service;

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER','CHEF')")
    @GetMapping
    PaginationResponse getAllUsers(@RequestParam int pageSize,int currentPage){
        return service.getAllUsers(currentPage,pageSize);
    }

    @PostMapping
    public SimpleResponse registerUser(@RequestBody @Valid UserRequest userRequest) throws BadCredentialException, BadRequestException {
        return service.registerToJob(userRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public  SimpleResponse AcceptOrReject(@RequestParam Long userId,@RequestParam Long restaurantId, @RequestParam String word){
        return service.acceptUser(userId, restaurantId, word);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public SimpleResponse updateUser(@PathVariable Long id,@RequestBody UserRequest userRequest){
        return service.updateUserById(id, userRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER','CHEF')")
    @GetMapping("{id}")
    public UserResponse getUserById(@PathVariable Long id) throws BadCredentialException {
        return service.getUserById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER','CHEF')")
    @DeleteMapping("{id}")
    public SimpleResponse deleteUserById(@PathVariable Long id) throws BadCredentialException {
        return service.deleteUserById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("saveByAdmin")
    public SimpleResponse saveUserByAdmin(@RequestParam Long id,@RequestBody @Valid UserRequest userRequest) throws BadCredentialException, BadRequestException {
        return service.saveUser(id,userRequest);
    }
}
