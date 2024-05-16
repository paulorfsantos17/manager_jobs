package com.paulosantos.gestordevagas.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.paulosantos.gestordevagas.providers.JWTProviderCandidate;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityCandidateFilter extends OncePerRequestFilter {

  @Autowired
  private JWTProviderCandidate jwtProviderCandidate;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    // SecurityContextHolder.getContext().setAuthentication(null);
    String header = request.getHeader("Authorization");

    if (request.getRequestURI().startsWith("/candidate")) {

      if (header != null) {
        var token = jwtProviderCandidate.validateToken(header);
        if (token == null) {
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
          return;
        }

        request.setAttribute("candidate_id", token.getSubject());
        var roles = token.getClaim("roles").asList(Object.class);

        var grants = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
            .toList();

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(),
            null,
            grants);
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }

    filterChain.doFilter(request, response);
  }

}
