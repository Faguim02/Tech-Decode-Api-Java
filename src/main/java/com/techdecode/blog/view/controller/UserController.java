package com.techdecode.blog.view.controller;

import com.techdecode.blog.dto.UserDto;
import com.techdecode.blog.dto.UserSignInDto;
import com.techdecode.blog.models.exceptions.NotFoundException;
import com.techdecode.blog.models.roles.UserRole;
import com.techdecode.blog.service.UserService;
import com.techdecode.blog.view.model.user.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        UserDto userSend = new UserDto(null, body.name(), body.email(), body.password(), UserRole.COMMON);

        UserDto userResDto = this.userService.signUp(userSend);
        SignUpResponse sign = new SignUpResponse(userResDto.id(), userResDto.name(), userResDto.email(), userResDto.password());

        return ResponseEntity.status(HttpStatus.CREATED).body(sign);
    }

    @Operation(summary = "cadastrar conta de administrador", description = "essa rota cria um novo usuario admin ao Techdecode, que é criada somente com a authorização de admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "usuario autorizado"),
            @ApiResponse(responseCode = "409", description = "este email de usuario já existe")
    })
    @PostMapping("signUp/admin")
    ResponseEntity<SignUpResponse> signUpAdmin(@RequestBody SignUpRequest body) {
        UserDto userSend = new UserDto(null, body.name(), body.email(), body.password(), UserRole.ADMIN);

        UserDto userResDto = this.userService.signUp(userSend);
        SignUpResponse sign = new SignUpResponse(userResDto.id(), userResDto.name(), userResDto.email(), userResDto.password());

        return ResponseEntity.status(HttpStatus.CREATED).body(sign);
    }

    @Operation(summary = "todos os usuarios", description = "essa rota retorna os usuarios comuns authenticados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "usuarios encontrados", content = @Content(schema = @Schema(implementation = FindAllResponse.class))),
            @ApiResponse(responseCode = "404", description = "nenhum usuario encontrado", content = @Content(schema = @Schema(implementation = NotFoundException.class)))
    })
    @GetMapping("dashboard/user")
    ResponseEntity<FindAllResponse> findAllUser() {
        List<UserDto> userDtos = this.userService.findAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(new FindAllResponse(userDtos, userDtos.size()));
    }

    @Operation(summary = "todos os administradores", description = "essa rota retorna os administradores do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "admins encontrados", content = @Content(schema = @Schema(implementation = FindAllResponse.class))),
            @ApiResponse(responseCode = "404", description = "nenhum admin encontrado", content = @Content(schema = @Schema(implementation = NotFoundException.class)))
    })
    @GetMapping("dashboard/admin")
    ResponseEntity<FindAllResponse> findAllAdmins() {
        List<UserDto> userDtos = this.userService.findAllAdmins();
        return ResponseEntity.status(HttpStatus.OK).body(new FindAllResponse(userDtos, userDtos.size()));
    }

}
