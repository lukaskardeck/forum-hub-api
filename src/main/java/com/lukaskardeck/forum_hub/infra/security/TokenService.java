package com.lukaskardeck.forum_hub.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lukaskardeck.forum_hub.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Injeta a chave secreta do arquivo de configuração (application.yml/properties)
    @Value("${api.security.jwt.secret}")
    private String secret;


    /**
     * Gera um novo token JWT para o usuário
     * @param user Objeto do usuário autenticado
     * @return Token JWT assinado
     */
    public String generateToken(User user) {
        try {
            // 1. Define o algoritmo de assinatura (HMAC256 com a chave secreta)
            var algorithm = Algorithm.HMAC256(secret);

            // 2. Constrói o token JWT com:
            return JWT.create()
                    .withIssuer("API Forum.hub")       // Emissor do token
                    .withSubject(user.getLogin())       // Identificador do usuário (subject)
                    .withExpiresAt(expirationDate())    // Data de expiração
                    .sign(algorithm);                   // Assina o token

        } catch (JWTCreationException exception) {
            // 3. Trata erros durante a geração (ex: chave secreta inválida)
            throw new RuntimeException("Erro ao gerar token JWT" + exception);
        }
    }


    /**
     * Valida um token JWT existente
     * @param tokenJWT Token a ser validado
     * @return Login do usuário (subject) se o token for válido
     */
    public String validateToken(String tokenJWT) {
        try {
            // 1. Prepara o algoritmo de verificação (mesmo usado na geração)
            var algorithm = Algorithm.HMAC256(secret);

            // 2. Verifica:
            return JWT.require(algorithm)
                    .withIssuer("API Forum.hub")       // Deve ter o mesmo emissor
                    .build()
                    .verify(tokenJWT)                   // Valida assinatura e expiração
                    .getSubject();                      // Extrai o subject (login)

        } catch (JWTVerificationException exception) {
            // 3. Token inválido (expirado, assinatura incorreta, etc)
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    /**
     * Calcula a data de expiração do token
     * @return Instant representando o momento de expiração
     */
    private Instant expirationDate() {
        var tokenValidityInHours = 2;
        return LocalDateTime.now()
                .plusHours(tokenValidityInHours)        // Adiciona 2 horas ao momento atual
                .toInstant(ZoneOffset.of("-03:00"));    // Considera fuso horário -3 (Brasil)
    }
}
