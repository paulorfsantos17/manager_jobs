package com.paulosantos.gestordevagas.exceptions;

public class CandidateFoundException extends RuntimeException {
  public CandidateFoundException() {
    super("Candidato já existe.");
  }
}
