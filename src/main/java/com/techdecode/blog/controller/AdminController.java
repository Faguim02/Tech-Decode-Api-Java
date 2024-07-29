package com.techdecode.blog.controller;

import com.techdecode.blog.dtos.AdminDto;
import com.techdecode.blog.dtos.RequestSignInDto;
import com.techdecode.blog.dtos.SignInResponseDto;
import com.techdecode.blog.infra.security.TokenService;
import com.techdecode.blog.models.AdminModel;
import com.techdecode.blog.repository.AdminRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    @PostMapping("signIn/")
    ResponseEntity<Object> signIn(@RequestBody @Valid RequestSignInDto adminDto) {
        AdminModel adminModel = this.adminRepository.findByEmail(adminDto.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(adminDto.password(), adminModel.getPassword())) {
            String token = this.tokenService.generateToken(adminModel);

            return ResponseEntity.ok(new SignInResponseDto(adminModel.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("signUp/")
    ResponseEntity<Object> signUp(@RequestBody @Valid AdminDto adminDto) {
        Optional<AdminModel> adminModel = this.adminRepository.findByEmail(adminDto.email());
        if(adminModel.isEmpty()) {
            AdminModel newAdminModel = new AdminModel();
            BeanUtils.copyProperties(adminDto, newAdminModel);
            newAdminModel.setPassword(passwordEncoder.encode(adminDto.password()));
            this.adminRepository.save(newAdminModel);

            String token = this.tokenService.generateToken(newAdminModel);
            return ResponseEntity.ok(new SignInResponseDto(newAdminModel.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
