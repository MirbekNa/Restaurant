package peaksoft.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import peaksoft.entity.User;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.UserRepository;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String tokenHolder = request.getHeader("Authorization");
        if (tokenHolder!=null && tokenHolder.startsWith("Bearer ")){
            String token = tokenHolder.substring(7);

            try{
                if (StringUtils.hasText(token)){
                    String userName = jwtService.validateToken(token);
                    User user = userRepository.getUserByEmail(userName).orElseThrow(() -> new NotFoundException("User name: " + userName + " is not found"));

                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            null,
                            user.getAuthorities()
                    ));

                }
            }catch (JWTVerificationException e){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid token");
            }
        }
        filterChain.doFilter(request,response);
    }
}
