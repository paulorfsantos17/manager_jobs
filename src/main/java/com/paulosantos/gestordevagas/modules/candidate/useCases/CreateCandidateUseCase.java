package com.paulosantos.gestordevagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paulosantos.gestordevagas.exceptions.UserFoundException;
import com.paulosantos.gestordevagas.modules.candidate.CandidateEntity;
import com.paulosantos.gestordevagas.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  public CandidateEntity execute(CandidateEntity candidateEntity) {

    this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
        .ifPresent(user -> {
          throw new UserFoundException();
        });

    return this.candidateRepository.save(candidateEntity);

  }

}
