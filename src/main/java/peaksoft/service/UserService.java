package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoUser.PaginationResponse;
import peaksoft.dto.dtoUser.UserRequest;
import peaksoft.dto.dtoUser.UserResponse;
import peaksoft.exceptions.BadCredentialException;
import peaksoft.exceptions.BadRequestException;

public interface UserService {

    PaginationResponse getAllUsers(int pageSize,int currentPage);
    SimpleResponse registerToJob (UserRequest userRequest) throws BadCredentialException, BadRequestException;
    SimpleResponse acceptUser(Long restaurantId, Long userId,String word);
    SimpleResponse updateUserById(Long id, UserRequest userRequest);
    SimpleResponse deleteUserById(Long id) throws BadCredentialException;
    UserResponse getUserById(Long id) throws BadCredentialException;
    SimpleResponse saveUser(Long restaurantId,UserRequest userRequest) throws BadCredentialException, BadRequestException;
}
