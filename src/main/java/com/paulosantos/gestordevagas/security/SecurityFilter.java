package com.paulosantos.gestordevagas.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.paulosantos.gestordevagas.providers.JWTProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private JWTProvider jwtProvider;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    String header = request.getHeader("Authorization");

    if (header != null) {
      String subjectToken = jwtProvider.validateToken(header);
      if (subjectToken.isEmpty()) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
        return;
      }
      request.setAttribute("company_id", subjectToken);
      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subjectToken, null,
          Collections.emptyList());
      SecurityContextHolder.getContext().setAuthentication(auth);

    }

    filterChain.doFilter(request, response);
  }

}
