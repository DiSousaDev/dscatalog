package br.dev.diego.dscatalog.config;

import br.dev.diego.dscatalog.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UserDetailsService usuarioService;

    public SecurityFilter(TokenService tokenService, UserDetailsService usuarioService) {
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();

        if (tokenHeader == null && requestURI.equals("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (tokenHeader != null) {
            setUsuarioLogado(request, response, filterChain, tokenHeader);
        }
        filterChain.doFilter(request, response);
    }

    private void setUsuarioLogado(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String tokenHeader) throws ServletException, IOException {
        String token = tokenHeader.replace("Bearer ", "");
        String subject = tokenService.getSubject(token);
        UserDetails userDetails = usuarioService.loadUserByUsername(subject);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}
