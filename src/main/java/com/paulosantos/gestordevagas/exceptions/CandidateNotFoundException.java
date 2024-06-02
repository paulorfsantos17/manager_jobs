package com.paulosantos.gestordevagas.exceptions;

public class CandidateNotFoundException extends RuntimeException {
  public CandidateNotFoundException() {
    super("Candidato Não existe.");
  }
}
