package com.techdecode.blog.service;

import com.techdecode.blog.dto.InfoNewLoginDto;
import com.techdecode.blog.dto.UserDto;
import com.techdecode.blog.dto.UserSignInDto;
import com.techdecode.blog.infra.security.JwtService;
import com.techdecode.blog.models.UserModel;
import com.techdecode.blog.models.exceptions.ConflictException;
import com.techdecode.blog.repository.UserRepository;
import com.techdecode.blog.view.client.dtos.IpInfoDto;
import com.techdecode.blog.view.client.ipinfo.IpInfoConsumer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua_parser.Client;
import ua_parser.Parser;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    IpInfoConsumer ipInfoConsumer;
    @Value("ip.info.token")
    String token;

    public String signIn(UserSignInDto userDto, String userAgent, String ip) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(userDto.email(), userDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        InfoNewLoginDto loginDto = new InfoNewLoginDto(this.getDateTime(), this.pickUpDispositive(userAgent), this.pickUpAddress(ip));
        this.emailService.sendInfoNewLoginDetected(UUID.randomUUID(), userDto.email(), auth.getName(), loginDto);

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

    private String pickUpAddress(String ip) {
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            return "Localmente";
        }
        IpInfoDto ipInfoDto = ipInfoConsumer.showInfoIp(ip, token);
        return String.format("%s do estado %s, no pais %s", ipInfoDto.city(), ipInfoDto.region(), ipInfoDto.country());
    }

    private String pickUpDispositive(String userAgent) {
        Parser parser = new Parser();
        Client client = parser.parse(userAgent);
        return String.format("Dispositivo: %s, Sistema Operacional: %s",
                client.device.family,
                client.os.family);
    }

    private String getDateTime() {
        Date dataAtual = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatador.format(dataAtual);
    }
}
