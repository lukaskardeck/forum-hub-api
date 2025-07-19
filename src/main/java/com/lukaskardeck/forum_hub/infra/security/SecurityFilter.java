package com.lukaskardeck.forum_hub.infra.security;

import com.lukaskardeck.forum_hub.domain.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository; // Repositório para buscar usuários no banco

    @Autowired
    private TokenService tokenService; // Serviço para validar tokens JWT


    /**
     * Método principal que executa a filtragem de cada requisição
     * @param request Objeto da requisição HTTP
     * @param response Objeto da resposta HTTP
     * @param filterChain Cadeia de filtros do Spring
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Extrai o token do cabeçalho Authorization
        var token = recoverToken(request);

        if (token != null) {
            // 2. Valida o token JWT e obtém o subject (username)
            var subject = tokenService.validateToken(token);

            if (!subject.isEmpty()) {
                // 3. Busca o usuário no banco pelo login (subject do token)
                var user = userRepository.findByLogin(subject);

                // 4. Cria o objeto de autenticação do Spring Security
                var authentication = new UsernamePasswordAuthenticationToken(
                        user, // Principal (usuário autenticado)
                        null, // Credenciais (não necessárias após autenticação)
                        user.getAuthorities() // Lista de autorizações/roles
                );

                // 5. Armazena no contexto de segurança do Spring
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 6. Continua o processamento da requisição pela cadeia de filtros
        filterChain.doFilter(request, response);
    }


    /**
     * Extrai o token JWT do cabeçalho Authorization
     * @param request Requisição HTTP
     * @return Token JWT (sem o prefixo "Bearer") ou null se não existir
     */
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", ""); // Remove o prefixo "Bearer "
    }
}
