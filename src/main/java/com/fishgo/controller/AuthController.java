package com.fishgo.controller;

import com.fishgo.config.JwtUtil;
import com.fishgo.dto.AuthRequest;
import com.fishgo.dto.AuthResponse;
import com.fishgo.model.Role;
import com.fishgo.model.User;
import com.fishgo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, UserService userService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestParam String name,
                         @RequestParam String email,
                         @RequestParam String password,
                         @RequestParam Role role,
                         @RequestParam(required = false) String harbourLocation) {
        return userService.register(name, email, password, role, harbourLocation);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        if (auth == null || !auth.isAuthenticated()) {
            throw new BadCredentialsException("Invalid credentials");
        }

        UserDetails ud = (UserDetails) auth.getPrincipal();

        String role = ud.getAuthorities().stream()
                .findFirst()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .orElse(null);

        String token = jwtUtil.generateToken(ud.getUsername(), role);

        return new AuthResponse(token, role);
        
    }
    
}
