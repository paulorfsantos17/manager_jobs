package com.paulosantos.gestordevagas.exceptions;

public class JobNotFoundException extends RuntimeException {
  public JobNotFoundException() {
    super("Vaga não existe.");
  }
}
