package com.paulosantos.gestordevagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paulosantos.gestordevagas.exceptions.UserFoundException;
import com.paulosantos.gestordevagas.modules.candidate.Repositories.CandidateRepository;
import com.paulosantos.gestordevagas.modules.candidate.entity.CandidateEntity;

@Service
public class CreateCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public CandidateEntity execute(CandidateEntity candidateEntity) {

    this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
        .ifPresent(user -> {
          throw new UserFoundException();
        });
    var password = passwordEncoder.encode(candidateEntity.getPassword());
    candidateEntity.setPassword(password);

    return this.candidateRepository.save(candidateEntity);

  }

}
