package com.techdecode.blog.controller;

import com.techdecode.blog.dtos.AdminDto;
import com.techdecode.blog.models.AdminModel;
import com.techdecode.blog.repository.AdminRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
public class AdminController {

    @Autowired
    AdminRepository adminRepository;

    @PostMapping("/admin")
    ResponseEntity<Object> signUp(@RequestBody @Valid AdminDto adminDto) {

        AdminModel adminModel = new AdminModel();

        BeanUtils.copyProperties(adminDto, adminModel);

        String passwordCrypt = String.valueOf(Encryptors.text(adminModel.getPassword(), "8"));
        adminModel.setPassword(passwordCrypt);

        return ResponseEntity.status(HttpStatus.CREATED).body(adminRepository.save(adminModel));
    }
}
