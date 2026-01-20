package com.fishgo.service;

import com.fishgo.model.Role;
import com.fishgo.model.User;
import com.fishgo.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public User register(String name, String email, String rawPassword, Role role, String harbourLocation) {
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(encoder.encode(rawPassword));
        u.setRole(role);
        u.setHarbourLocation(harbourLocation);
        u.setApproved(role != Role.FISHERMAN);
        return repo.save(u);
    }

    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    public User approveFisherman(Long userId) {
        User u = repo.findById(userId).orElseThrow();
        u.setApproved(true);
        return repo.save(u);
    }
}
