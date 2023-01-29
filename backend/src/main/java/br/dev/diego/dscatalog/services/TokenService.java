package br.dev.diego.dscatalog.services;

import br.dev.diego.dscatalog.controllers.dto.TokenJWTResponse;
import br.dev.diego.dscatalog.entities.User;
import br.dev.diego.dscatalog.services.exceptions.TokenGenerationException;
import br.dev.diego.dscatalog.services.exceptions.TokenVerifyException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Configuration
public class TokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.duration}")
    private Integer jwtDuration;
    @Value("${jwt.issuer}")
    private String issuer;

    public TokenJWTResponse generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return new TokenJWTResponse(JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getPassword())
                    .withClaim("id", user.getId())
                    .withClaim("email", user.getEmail())
                    .withExpiresAt(getExpirationTime())
                    .sign(algorithm));
        } catch (JWTCreationException e) {
            throw new TokenGenerationException("Erro ao gerar token JWT, " + e.getMessage());
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(tokenJWT)
                    .getClaim("email")
                    .asString();
        } catch (JWTVerificationException e) {
            throw new TokenVerifyException("Token JTW inválido ou expirado, " + e.getMessage());
        }
    }

    private Instant getExpirationTime() {
        return LocalDateTime.now().plusHours(jwtDuration).toInstant(ZoneOffset.of("-03:00"));
    }

    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
