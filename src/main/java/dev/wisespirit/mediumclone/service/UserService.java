package dev.wisespirit.mediumclone.service;

import dev.wisespirit.mediumclone.model.auth.GenerateTokenRequest;
import dev.wisespirit.mediumclone.model.auth.TokenRefreshRequest;
import dev.wisespirit.mediumclone.model.auth.TokenResponse;
import dev.wisespirit.mediumclone.model.dto.UserCreateDto;
import dev.wisespirit.mediumclone.model.dto.UserDto;
import dev.wisespirit.mediumclone.model.dto.UserUpdateDto;
import dev.wisespirit.mediumclone.model.entity.User;
import dev.wisespirit.mediumclone.model.enums.JwtTokenType;
import dev.wisespirit.mediumclone.repository.UserRepository;
import dev.wisespirit.mediumclone.security.JwtUtil;
import dev.wisespirit.mediumclone.security.UserSession;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserSession userSession;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserSession userSession,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userSession = userSession;
        this.jwtUtil = jwtUtil;
    }

    public TokenResponse generateAccessToken(GenerateTokenRequest dto){
        String email = dto.email();
        String password = dto.password();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(email,password);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        Map<String, Object> accessClaims = Map.<String, Object>of("token", JwtTokenType.ACCESS);
        Map<String, Object> refreshClaims = Map.<String, Object>of("token", JwtTokenType.REFRESH);
        String accessToken = jwtUtil.generateAccessToken(email, accessClaims);
        String refreshToken = jwtUtil.generateRefreshToken(email, refreshClaims);
        return new TokenResponse(accessToken,refreshToken);
    }

    public TokenResponse refreshToken(TokenRefreshRequest refreshRequest){
        String token = refreshRequest.token();
        if (!jwtUtil.isValid(token)) {
            throw new BadCredentialsException("refresh token is invalid");
        }
        Claims claims = jwtUtil.getClaims(token);
        if (claims.get("token")==null||claims.get("token").equals("REFRESH")){
            throw new BadCredentialsException("refresh token is invalid");
        }
        Map<String, Object> accessClaims = Map.<String, Object>of("token", JwtTokenType.ACCESS);
        String username = claims.get("sub", String.class);
        String accessToken = jwtUtil.generateAccessToken(username, accessClaims);
        return new TokenResponse(accessToken,token);
    }


    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDto(user.getId(),
                        user.getFullName(),
                        user.getBio(),
                        user.getEmail(),
                        user.getFollowingsId()))
                .toList();
    }

    public Optional<UserDto> createUser(UserCreateDto dto) {
        User saved = userRepository
                .save(new User(null,
                        dto.fullName(),
                        passwordEncoder.encode(dto.password()),
                        null,
                        dto.email(),
                        null));
        return Optional.ofNullable(new UserDto(saved.getId(),
                saved.getFullName(),
                saved.getBio(),
                saved.getEmail(),
                saved.getFollowingsId()));
    }

    public Optional<UserDto> updateUser(UserUpdateDto dto, Long id) {
        User user = userRepository.findById(id).get();
        User updated = userRepository
                .save(new User(user.getId(),
                        isBlank(dto.fullName())? dto.fullName() : user.getFullName(),
                        isBlank(dto.password()) ? dto.password() : user.getPassword(),
                        isBlank(dto.bio())? dto.bio() : user.getBio(),
                        isBlank(dto.email()) ? dto.email() : user.getEmail(),
                        user.getFollowingsId()));
        return Optional.ofNullable(new UserDto(updated.getId(),
                updated.getFullName(),
                updated.getBio(),
                updated.getEmail(),
                updated.getFollowingsId()));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void subscribeToPerson(Long userId, Long subscriptionId) {
        User user = userRepository.findById(userId).get();
        if (user.getFollowingsId() != null) {
            user.getFollowingsId().add(subscriptionId);
        }
        List<Long> list = new ArrayList<>();
        list.add(subscriptionId);
        user.setFollowingsId(list);
        userRepository.save(user);
    }

    public void unSubscribeToPerson(Long userId, Long unSubscriptionId) {
        User user = userRepository.findById(userId).get();
        if (user.getFollowingsId()!=null) {
            user.getFollowingsId().remove(unSubscriptionId);
        }
        userRepository.save(user);
    }


    public boolean existById(Long id) {
        return userRepository.existsById(id);
    }

    public Optional<UserDto> getById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDto(
                        user.getId(),
                        user.getFullName(),
                        user.getBio(),
                        user.getEmail(),
                        user.getFollowingsId()
                ))
                .or(()->Optional.empty());
    }

    private boolean isBlank(String str){
        return str!=null&& !str.isBlank();
    }

    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }
}
