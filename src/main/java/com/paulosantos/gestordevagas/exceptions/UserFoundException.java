package com.paulosantos.gestordevagas.exceptions;

public class UserFoundException extends RuntimeException {
  public UserFoundException() {
    super("Usuário já existe.");
  }
}
