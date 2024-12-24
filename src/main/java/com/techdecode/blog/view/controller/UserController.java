package com.techdecode.blog.view.controller;

import com.techdecode.blog.dto.UserDto;
import com.techdecode.blog.dto.UserSignInDto;
import com.techdecode.blog.service.UserService;
import com.techdecode.blog.view.client.dtos.IpInfoDto;
import com.techdecode.blog.view.client.ipinfo.IpInfoConsumer;
import com.techdecode.blog.view.model.user.SignInRequest;
import com.techdecode.blog.view.model.user.SignInResponse;
import com.techdecode.blog.view.model.user.SignUpRequest;
import com.techdecode.blog.view.model.user.SignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "User", description = "rotas de authenticação e criação de usuario")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "logar usuario", description = "essa rota faz a autorização do usuario e retorna um token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "usuario autorizado"),
            @ApiResponse(responseCode = "403", description = "não autorizado")
    })
    @PostMapping("signIn")
    ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest body, @RequestHeader("User-Agent") String userAgent, HttpServletRequest request) {

        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        UserSignInDto signInDto = new UserSignInDto(body.email(), body.password());
        String token = this.userService.signIn(signInDto, userAgent, ipAddress);
        SignInResponse signInResponse = new SignInResponse(token);

        return ResponseEntity.status(HttpStatus.OK).body(signInResponse);
    }

    @Operation(summary = "cadastrar conta", description = "essa rota cria um novo usuario ao Techdecode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "usuario autorizado"),
            @ApiResponse(responseCode = "409", description = "este email de usuario já existe")
    })
    @PostMapping("signUp")
    ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest body) {
        UserDto userSend = new UserDto(null, body.name(), body.email(), body.password(), body.userRole());

        UserDto userResDto = this.userService.signUp(userSend);
        SignUpResponse sign = new SignUpResponse(userResDto.id(), userResDto.name(), userResDto.email(), userResDto.password());

        return ResponseEntity.status(HttpStatus.CREATED).body(sign);
    }

}
