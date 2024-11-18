package com.example.backend.service.user;

import com.example.backend.entity.UserEntity;
import com.example.backend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity findOne(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public boolean exists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.isPresent();
    }
}
