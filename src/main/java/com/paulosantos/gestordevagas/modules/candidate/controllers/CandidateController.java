package com.paulosantos.gestordevagas.modules.candidate.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paulosantos.gestordevagas.exceptions.UserFoundException;
import com.paulosantos.gestordevagas.modules.candidate.CandidateEntity;
import com.paulosantos.gestordevagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.paulosantos.gestordevagas.modules.candidate.useCases.CreateCandidateUseCase;
import com.paulosantos.gestordevagas.modules.candidate.useCases.ProfileCandidateUseCase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> createCandidate(@Valid @RequestBody CandidateEntity candidateEntity) {
    try {
      var result = this.createCandidateUseCase.execute(candidateEntity);
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @GetMapping("/")
  @PreAuthorize("hasRole('CANDIDATE')")
  public ResponseEntity<Object> getCandidate(HttpServletRequest request) throws UserFoundException {
    var candidateId = request.getAttribute("candidate_id");

    try {
      ProfileCandidateResponseDTO candidateResponseDTO = this.profileCandidateUseCase
          .execute(UUID.fromString(candidateId.toString()));
      return ResponseEntity.status(HttpStatus.OK).body(candidateResponseDTO);
    } catch (UserFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

  }

}
