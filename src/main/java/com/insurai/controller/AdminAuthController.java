package com.insurai.insurai.controller;

import com.insurai.insurai.dto.LoginRequest;
import com.insurai.insurai.dto.LoginResponse;
import com.insurai.insurai.model.Admin;
import com.insurai.insurai.repository.AdminRepository;
import com.insurai.insurai.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/auth")
@CrossOrigin(origins = "*")
public class AdminAuthController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Admin Signup with strong validation (email + phone)
    @PostMapping("/signup")
    public ResponseEntity<?> registerAdmin(@RequestBody Admin admin) {

        if (adminRepository.existsByEmail(admin.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email already exists!");
        }

        if (adminRepository.existsByPhone(admin.getPhone())) {
            return ResponseEntity.badRequest().body("Error: Phone number already exists!");
        }

        // Encode password
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        // Set default role
        admin.setRole("ADMIN");

        Admin savedAdmin = adminRepository.save(admin);

        return ResponseEntity.ok("Admin registered successfully!");
    }

    // ✅ Admin Login with JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Optional<Admin> optionalAdmin = adminRepository.findByEmail(request.getEmail());

        if (optionalAdmin.isEmpty() ||
            !passwordEncoder.matches(request.getPassword(), optionalAdmin.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        Admin admin = optionalAdmin.get();

        // Generate JWT token
        String token = jwtUtil.generateToken(admin.getEmail(), admin.getRole());

        LoginResponse response = new LoginResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getRole(),
                token
        );

        return ResponseEntity.ok(response);
    }
}
