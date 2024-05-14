package com.paulosantos.gestordevagas.exceptions;

public class CompanyFoundException extends RuntimeException {
  public CompanyFoundException() {
    super("Empresa já existe.");
  }
}
