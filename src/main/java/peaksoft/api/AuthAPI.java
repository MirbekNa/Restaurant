package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.dtoAuth.AdminTokenRequest;
import peaksoft.dto.dtoAuth.AuthenticationResponse;
import peaksoft.dto.dtoAuth.SignInRequest;
import peaksoft.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthAPI {
    private final AuthenticationService authenticationService;

    @GetMapping
   public AuthenticationResponse getAdminToken(AdminTokenRequest adminTokenRequest){
       return authenticationService.getAdminToken(adminTokenRequest);
   }

   @PostMapping("/signIn")
   public AuthenticationResponse signIn(@RequestBody SignInRequest signInRequest){
        return authenticationService.signIn(signInRequest);
   }
}
