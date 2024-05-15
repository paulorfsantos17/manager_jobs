package com.paulosantos.gestordevagas.exceptions;

public class CompanyNotFoundException extends RuntimeException {
  public CompanyNotFoundException() {
    super("Empresa Não existe");
  }
}
