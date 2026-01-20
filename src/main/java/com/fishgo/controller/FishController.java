package com.fishgo.controller;

import com.fishgo.model.Fish;
import com.fishgo.model.User;
import com.fishgo.repo.UserRepository;
import com.fishgo.service.FishService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fish")
@CrossOrigin(origins = "http://localhost:3000")
public class FishController {

    private final FishService fishService;
    private final UserRepository userRepository;

    public FishController(FishService fs, UserRepository ur) {
        this.fishService = fs;
        this.userRepository = ur;
    }

    @GetMapping("/available")
    public List<Fish> listAvailable() {
        return fishService.listAvailable();
    }

    @PostMapping("/add")
    public Fish addFish(@AuthenticationPrincipal UserDetails ud, @RequestBody Fish fish) {
        User vendor = userRepository.findByEmail(ud.getUsername()).orElseThrow();
        if (!Boolean.TRUE.equals(vendor.getApproved())) throw new RuntimeException("Vendor not approved");
        return fishService.addFish(fish, vendor);
    }

    @GetMapping("/my")
    public List<Fish> myFish(@AuthenticationPrincipal UserDetails ud) {
        User vendor = userRepository.findByEmail(ud.getUsername()).orElseThrow();
        return fishService.listByVendor(vendor);
    }
}
