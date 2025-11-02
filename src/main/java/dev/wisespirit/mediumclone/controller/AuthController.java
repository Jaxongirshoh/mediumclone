package dev.wisespirit.mediumclone.controller;

import dev.wisespirit.mediumclone.model.auth.GenerateTokenRequest;
import dev.wisespirit.mediumclone.model.auth.TokenRefreshRequest;
import dev.wisespirit.mediumclone.model.auth.TokenResponse;
import dev.wisespirit.mediumclone.model.dto.UserCreateDto;
import dev.wisespirit.mediumclone.model.dto.UserDto;
import dev.wisespirit.mediumclone.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserCreateDto userCreateDto){
        UserDto userDto = userService.createUser(userCreateDto).get();
        return new ResponseEntity<>(userDto,HttpStatus.CREATED);
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> token(@Valid @RequestBody GenerateTokenRequest generateToken){
        TokenResponse tokenResponse = userService.generateAccessToken(generateToken);
        return new ResponseEntity<>(tokenResponse,HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody TokenRefreshRequest refreshRequest){
        TokenResponse tokenResponse = userService.refreshToken(refreshRequest);
        return new ResponseEntity<>(tokenResponse,HttpStatus.OK);
    }



}
