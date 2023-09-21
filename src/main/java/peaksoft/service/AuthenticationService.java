package peaksoft.service;

import peaksoft.dto.dtoAuth.AdminTokenRequest;
import peaksoft.dto.dtoAuth.AuthenticationRequest;
import peaksoft.dto.dtoAuth.AuthenticationResponse;
import peaksoft.dto.dtoAuth.SignInRequest;

public interface AuthenticationService {

    AuthenticationResponse getAdminToken(AdminTokenRequest adminTokenRequest);

    AuthenticationResponse signIn(SignInRequest signInRequest);

}
