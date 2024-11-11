package com.example.backend.service.user;

import com.example.backend.dto.UserDto;
import com.example.backend.model.UserEntity;
import com.example.backend.service.GenericService;

import java.util.Optional;

public interface UserService extends GenericService<UserEntity> {
    boolean existsByEmail(String email);
}
