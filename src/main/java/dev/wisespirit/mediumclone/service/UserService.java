package dev.wisespirit.mediumclone.service;

import dev.wisespirit.mediumclone.model.dto.UserCreateDto;
import dev.wisespirit.mediumclone.model.dto.UserDto;
import dev.wisespirit.mediumclone.model.dto.UserUpdateDto;
import dev.wisespirit.mediumclone.model.entity.User;
import dev.wisespirit.mediumclone.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDto(user.getId(),
                        user.getFullName(),
                        user.getPassword(),
                        user.getBio(),
                        user.getEmail(),
                        user.getFollowingsId()))
                .toList();
    }

    public Optional<UserDto> createUser(UserCreateDto dto) {
        User saved = userRepository
                .save(new User(null,
                        dto.fullName(),
                        dto.password(),
                        dto.bio(),
                        dto.email(),
                        null));
        return Optional.ofNullable(new UserDto(saved.getId(),
                saved.getFullName(),
                saved.getPassword(),
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
                updated.getPassword(),
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


    public boolean existByid(Long id) {
        return userRepository.existsById(id);
    }

    public Optional<UserDto> getById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDto(
                        user.getId(),
                        user.getFullName(),
                        user.getPassword(),
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
