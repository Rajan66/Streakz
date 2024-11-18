package com.example.backend.service.authentication;


import com.example.backend.dto.AuthenticationResponse;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.dto.UserDto;
import com.example.backend.enums.Role;
import com.example.backend.mapper.Mapper;
import com.example.backend.entity.UserEntity;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final UserService userService;
    private final Mapper<UserEntity, UserDto> userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<AuthenticationResponse> register(RegisterRequest registerRequest) {
        if (userService.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // HTTP 409 Conflict
                    .body(new AuthenticationResponse("User account with this email already exists."));
        }
        UserEntity u = UserEntity.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(bCryptPasswordEncoder.encode(registerRequest.getPassword()))
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .role(registerRequest.getRole() != null ? registerRequest.getRole() : Role.USER)
                .phone(registerRequest.getPhone())
                .build();
        userRepository.save(u);
        log.info("User entity: {}", u);
        UserDto userDto = userMapper.mapTo(u);
        log.info("User dto: {}", userDto);
        String token = jwtService.generateToken(u);
        Date issuedAt = jwtService.getIssuedDate(token);
        Date expirationDate = jwtService.getExpirationDate(token);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .user(userDto)
                .token(token)
                .issuedDate(issuedAt)
                .expirationDate(expirationDate)
                .build();

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<AuthenticationResponse> login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            /* the authentication manager intercepts the response in-case of invalid credentials,
              so I opted for a try-catch block
             */
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse("Invalid credentials"));
        }
        Optional<UserEntity> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new AuthenticationResponse("User not found"));
        }
        UserEntity user = userOptional.get();
        UserDto userDto = userMapper.mapTo(user);
        String token = jwtService.generateToken(user);
        Date issuedAt = jwtService.getIssuedDate(token);
        Date expirationDate = jwtService.getExpirationDate(token);

        AuthenticationResponse response = AuthenticationResponse.builder()
                .user(userDto)
                .token(token)
                .issuedDate(issuedAt)
                .expirationDate(expirationDate)
                .build();

        return ResponseEntity.ok(response);
    }
}

