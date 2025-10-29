package dev.wisespirit.mediumclone.controller;

import dev.wisespirit.mediumclone.model.dto.UserCreateDto;
import dev.wisespirit.mediumclone.model.dto.UserDto;
import dev.wisespirit.mediumclone.model.dto.UserUpdateDto;
import dev.wisespirit.mediumclone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @Deprecated(forRemoval=true) will move to AuthController
     */
    @Deprecated(forRemoval = true)
    @PostMapping("/create")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserCreateDto dto){
        return userService.createUser(dto)
                .map(userDto -> new ResponseEntity<UserDto>(userDto, HttpStatus.CREATED))
                .orElse(ResponseEntity.internalServerError().build());
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getALlUser(){
        return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserUpdateDto dto,
                                     @PathVariable Long id){
        if (!userService.existByid(id)){
            return ResponseEntity.notFound().build();
        }
        return userService.updateUser(dto, id)
                .map(userDto -> new ResponseEntity<UserDto>(userDto, HttpStatus.NO_CONTENT))
                .orElse(ResponseEntity.internalServerError().build());
    }

    @PatchMapping("/subscribers/subscribe/{userId}/{subscriptionId}")
    public ResponseEntity<Void> addSubscription(@PathVariable Long userId,
                                                @PathVariable Long subscriptionId){
        if (!userService.existByid(userId)){
            return ResponseEntity.notFound().build();
        }

        if (!userService.existByid(subscriptionId)){
            return ResponseEntity.notFound().build();
        }
        userService.subscribeToPerson(userId,subscriptionId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/subscribers/unsubscribe/{userId}/{subscriptionId}")
    public ResponseEntity<Void> unSubscribe(@PathVariable Long userId,
                                            @PathVariable Long subscriptionId ){
        if (!userService.existByid(userId)){
            return ResponseEntity.notFound().build();
        }
        if (!userService.existByid(subscriptionId)){
            return ResponseEntity.notFound().build();
        }
        userService.unSubscribeToPerson(userId,subscriptionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return userService.getById(id)
                .map(userDto -> new ResponseEntity<UserDto>(userDto, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        if (userService.existByid(userId)) {
            userService.deleteUser(userId);
        }
        return ResponseEntity.notFound().build();
    }


}
