package com.example.backend.service.user;

import com.example.backend.entity.UserEntity;
import com.example.backend.service.GenericService;

public interface UserService extends GenericService<UserEntity> {
    boolean existsByEmail(String email);
}
