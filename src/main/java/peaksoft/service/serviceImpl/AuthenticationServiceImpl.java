package peaksoft.service.serviceImpl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.JWTService;
import peaksoft.dto.dtoAuth.AdminTokenRequest;
import peaksoft.dto.dtoAuth.AuthenticationRequest;
import peaksoft.dto.dtoAuth.AuthenticationResponse;
import peaksoft.dto.dtoAuth.SignInRequest;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.AuthenticationService;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RestaurantRepository restaurantRepository;

    @Override
    public AuthenticationResponse getAdminToken(AdminTokenRequest adminTokenRequest) {

        User user = userRepository.getUserByEmail(adminTokenRequest.email()).orElseThrow(() -> new NotFoundException("user with email: " + adminTokenRequest.email() + " is not found"));
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.getUserByEmail(signInRequest.email()).orElseThrow(() -> new NotFoundException("user is not found"));
        if (signInRequest.email().isBlank()){
            throw new BadCredentialsException("email does not exist...");
        }
        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())){
            throw new BadCredentialsException("Incorrect password");
        }
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .role(user.getRole())
                .email(user.getEmail())
                .build();
    }

    @PostConstruct
    public  void init(){
        List<User> allUsers = restaurantRepository.getAllUsers();
        Restaurant restaurant = new Restaurant();
        User user = new User();
        List<User> users = new ArrayList<>();
        users.add(user);
        restaurant.setName("Yammi");
        restaurant.setService(15);
        restaurant.setLocation("Chuy 112");
        restaurant.setRestType("Koreiskiy");
        restaurant.setUsers(users);
        restaurant.setNumberOfEmployees(allUsers.size());
        if(!restaurantRepository.existsByName(restaurant.getName())){
            restaurantRepository.save(restaurant);
        }
        user.setFirstName("Sayan");
        user.setLastName("Super");
        user.setPassword(passwordEncoder.encode("kameha"));
        user.setEmail("Super@gmail.com");
        user.setRole(Role.ADMIN);
        user.setPhoneNumber("+996777777777");
        user.setDateOfBirth(LocalDate.of(2000,12,30));
        user.setExperience(5);
        user.setRestaurant(restaurant);

        if (!userRepository.existsByEmail(user.getEmail())){
            userRepository.save(user);
        }


    }
}
