package com.paulosantos.gestordevagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paulosantos.gestordevagas.exceptions.UserFoundException;
import com.paulosantos.gestordevagas.modules.candidate.CandidateEntity;
import com.paulosantos.gestordevagas.modules.candidate.CandidateRepository;
import com.paulosantos.gestordevagas.modules.candidate.dto.ProfileCandidateResponseDTO;

@Service
public class ProfileCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  public ProfileCandidateResponseDTO execute(UUID idCandidate) {
    CandidateEntity candidate = this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
      throw new UserFoundException();
    });

    ProfileCandidateResponseDTO candidateResponseDTO = ProfileCandidateResponseDTO
        .builder()
        .description(candidate.getDescription())
        .email(candidate.getEmail())
        .id(candidate.getId())
        .name(candidate.getName())
        .username(candidate.getUsername())
        .build();

    return candidateResponseDTO;
  }
}
