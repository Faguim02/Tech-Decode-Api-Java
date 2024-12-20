package com.techdecode.blog.view.controller;

import com.techdecode.blog.dto.UserDto;
import com.techdecode.blog.dto.UserSignInDto;
import com.techdecode.blog.service.UserService;
import com.techdecode.blog.view.model.user.SignInRequest;
import com.techdecode.blog.view.model.user.SignInResponse;
import com.techdecode.blog.view.model.user.SignUpRequest;
import com.techdecode.blog.view.model.user.SignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("i")
    String a(){
        return "oi";
    }

    @PostMapping("signIn")
    ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest body) {
        System.out.println("aa");
        UserSignInDto signInDto = new UserSignInDto(body.email(), body.password());
        System.out.println(signInDto.email());
        String token = this.userService.signIn(signInDto);
        SignInResponse signInResponse = new SignInResponse(token);

        return ResponseEntity.status(HttpStatus.OK).body(signInResponse);
    }

    @PostMapping("signUp")
    ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest body) {
        UserDto userSend = new UserDto(null, body.name(), body.email(), body.password(), body.userRole());

        UserDto userResDto = this.userService.signUp(userSend);
        SignUpResponse sign = new SignUpResponse(userResDto.id(), userResDto.name(), userResDto.email(), userResDto.password());

        return ResponseEntity.status(HttpStatus.CREATED).body(sign);
    }

}
