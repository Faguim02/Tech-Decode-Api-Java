package com.techdecode.blog.service;

import com.techdecode.blog.dto.EmailDto;
import com.techdecode.blog.dto.UserDto;
import com.techdecode.blog.dto.UserSignInDto;
import com.techdecode.blog.infra.security.JwtService;
import com.techdecode.blog.models.UserModel;
import com.techdecode.blog.models.exceptions.BadRequestException;
import com.techdecode.blog.models.exceptions.ConflictException;
import com.techdecode.blog.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    EmailService emailService;

    public String signIn(UserSignInDto userDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(userDto.email(), userDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return jwtService.generateToken((UserModel) auth.getPrincipal());
    }

    public UserDto signUp(UserDto userDto) {
        if (userRepository.findByEmail(userDto.email()) != null) {
            throw new ConflictException("este email de usuario já existe");
        }

        UserModel userModel = new UserModel();

        BeanUtils.copyProperties(userDto, userModel);

        String passwordEncode = new BCryptPasswordEncoder().encode(userDto.password());
        userModel.setPassword(passwordEncode);

        userRepository.save(userModel);

        this.emailService.sendWelcomeMessage(userDto.id(), userDto.email(), userDto.name());

        return new UserDto(userModel.getId(), userModel.getName(), userModel.getEmail(), userModel.getPassword(), userModel.getUserRole());
    }
}
