package com.example.spring_boot_ex.repository;

import com.example.spring_boot_ex.model.UserModel;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepositoryImp implements UserRepository {
    private final Map<Long, UserModel> userStore = new HashMap<>();
    public UserRepositoryImp() {
        userStore.put(1L, new UserModel(1L, "Alice", "alice@gmail.com"));
        userStore.put(2L, new UserModel(2L, "Bob","bob@gmail.com"));
        userStore.put(3L, new UserModel(3L, "Dave","dave@gmail.com"));
    }

    @Override
    public Optional<UserModel> findById(Long id) {
        return Optional.ofNullable(userStore.get(id));
    }

    @Override
    public List<UserModel> findAll() {
        return new ArrayList<>(userStore.values());
    }

    @Override
    public UserModel save(UserModel usermodel) {
        userStore.put(usermodel.getId(), usermodel);
        return usermodel;
    }
}
