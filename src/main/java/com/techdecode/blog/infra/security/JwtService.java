package com.techdecode.blog.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.techdecode.blog.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {

    @Value("{api.security.token.secret}")
    String tokenSecret;

    //todo create jwt
    public String generateToken(UserModel userModel) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);

            String token = JWT.create()
                    .withExpiresAt(this.generateExpires())
                    .withIssuer("api-techdecode")
                    .withSubject(userModel.getEmail())
                    .sign(algorithm);

            return token;

        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar jwt", e);
        }
    }

    //todo valid jwt
    public String validToken(String token) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);

            return JWT.require(algorithm)
                    .withIssuer("api-techdecode")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException e) {
            throw new RuntimeException("Erro ao verificar token", e);
        }
    }

    private Instant generateExpires() {
        return LocalDateTime.now().plusYears(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
