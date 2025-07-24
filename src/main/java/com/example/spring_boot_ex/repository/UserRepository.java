package com.example.spring_boot_ex.repository;

import com.example.spring_boot_ex.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<UserModel> findById(Long id);

    List<UserModel> findAll();

    UserModel save(UserModel user);
}
