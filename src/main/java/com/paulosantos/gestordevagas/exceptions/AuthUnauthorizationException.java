package com.paulosantos.gestordevagas.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AuthUnauthorizationException extends AuthenticationException {
  public AuthUnauthorizationException() {
    super("Usuário ou senha incorretos.");
  }
}
