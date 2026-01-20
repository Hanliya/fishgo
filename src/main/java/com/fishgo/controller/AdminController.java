package com.fishgo.controller;

import com.fishgo.model.User;
import com.fishgo.repo.UserRepository;
import com.fishgo.service.FishService;
import com.fishgo.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final FishService fishService;

    public AdminController(UserService us, UserRepository ur, FishService fs) {
        this.userService = us;
        this.userRepository = ur;
        this.fishService = fs;
    }

    @GetMapping("/fishermen/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> pendingFishermen() {
        return userRepository.findAll().stream()
                .filter(u -> u.getRole().name().equals("FISHERMAN") && Boolean.FALSE.equals(u.getApproved()))
                .toList();
    }

    @PostMapping("/fishermen/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public User approve(@PathVariable Long id) {
        return userService.approveFisherman(id);
    }

    @PostMapping("/fish/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateFishStatus(@PathVariable Long id, @RequestParam String status) {
        fishService.updateStatus(id, status);
        return "OK";
    }
}
