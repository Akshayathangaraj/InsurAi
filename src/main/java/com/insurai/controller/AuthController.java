package com.insurai.insurai.controller;

import com.insurai.insurai.model.Employee;
import com.insurai.insurai.repository.EmployeeRepository;
import com.insurai.insurai.service.EmployeeService;
import com.insurai.insurai.dto.EmployeeRequestDTO;
import com.insurai.insurai.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // allow frontend React app to connect
public class AuthController {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Employee emp) {
        Map<String, Object> response = new HashMap<>();

        // --- Required field check ---
        if (emp.getEmail() == null || emp.getEmail().trim().isEmpty()) {
            response.put("message", "Email is required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (emp.getPhone() == null || emp.getPhone().trim().isEmpty()) {
            response.put("message", "Phone number is required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (emp.getPassword() == null || emp.getPassword().trim().isEmpty()) {
            response.put("message", "Password is required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // --- Email format validation ---
        if (!emp.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            response.put("message", "Invalid email format");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // --- Phone number format validation (10 digits) ---
        if (!emp.getPhone().matches("\\d{10}")) {
            response.put("message", "Phone number must be 10 digits");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // --- Check uniqueness ---
        if (employeeRepo.existsByEmail(emp.getEmail())) {
            response.put("message", "Email already registered");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (employeeRepo.existsByPhone(emp.getPhone())) {
            response.put("message", "Phone number already registered");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // --- Encode password ---
        emp.setPassword(passwordEncoder.encode(emp.getPassword()));

        // --- Set defaults ---
        if (emp.getRole() == null || emp.getRole().trim().isEmpty()) {
            emp.setRole("EMPLOYEE");
        }
        if (emp.getEmployeeStatus() == null || emp.getEmployeeStatus().trim().isEmpty()) {
            emp.setEmployeeStatus("Active");
        }
        if (emp.getDateOfJoining() == null) {
            emp.setDateOfJoining(LocalDate.now());
        }

        Employee savedEmp = employeeRepo.save(emp);

        response.put("message", "Signup successful");
        response.put("employee", savedEmp);
        return ResponseEntity.ok(response);
    }

    // ✅ Login endpoint with JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        Employee emp = employeeRepo.findByEmail(email);
        Map<String, Object> response = new HashMap<>();

        if (emp == null || !passwordEncoder.matches(password, emp.getPassword())) {
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Update last login
        emp.setLastLogin(LocalDateTime.now());
        employeeRepo.save(emp);

        // ✅ Generate JWT token
        String token = jwtUtil.generateToken(emp.getEmail(), emp.getRole());

        response.put("message", "Login successful");
        response.put("token", token);
        response.put("user", emp);

        return ResponseEntity.ok(response);
    }

    // ✅ Update profile
    @PutMapping("/updateProfile/{id}")
    public ResponseEntity<?> updateProfile(
            @PathVariable Long id,
            @RequestBody EmployeeRequestDTO dto) {

        Employee employee = employeeRepo.findById(id).orElse(null);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Employee not found"));
        }

        if (dto.getProfilePhotoUrl() != null) {
            employee.setProfilePhotoUrl(dto.getProfilePhotoUrl());
        }
        if (dto.getAddress() != null) {
            employee.setAddress(dto.getAddress());
        }

        Employee saved = employeeRepo.save(employee);
        return ResponseEntity.ok(saved);
    }

    // ✅ Upload profile photo
    @PostMapping("/{id}/uploadProfilePhoto")
    public ResponseEntity<?> uploadProfilePhoto(@PathVariable Long id,
                                                @RequestParam("file") MultipartFile file) {
        try {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());

            String serverUrl = "http://localhost:8080";  // update if deployed
            String fileUrl = serverUrl + "/uploads/" + fileName;

            return ResponseEntity.ok(Map.of("profilePhotoUrl", fileUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file: " + e.getMessage());
        }
    }
}
