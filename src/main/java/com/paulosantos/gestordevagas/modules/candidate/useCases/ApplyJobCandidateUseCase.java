package com.paulosantos.gestordevagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.paulosantos.gestordevagas.exceptions.CandidateNotFoundException;
import com.paulosantos.gestordevagas.exceptions.JobNotFoundException;
import com.paulosantos.gestordevagas.modules.candidate.Repositories.ApplyJobRepository;
import com.paulosantos.gestordevagas.modules.candidate.Repositories.CandidateRepository;
import com.paulosantos.gestordevagas.modules.candidate.entity.ApplyJobEntity;
import com.paulosantos.gestordevagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private ApplyJobRepository applyJobRepository;

  public ApplyJobEntity execute(UUID jobId, UUID candidateId) {

    this.candidateRepository.findById(candidateId).orElseThrow(() -> {
      throw new CandidateNotFoundException();
    });

    this.jobRepository.findById(jobId).orElseThrow(() -> {
      throw new JobNotFoundException();
    });

    ApplyJobEntity applyJob = ApplyJobEntity.builder()
        .candidateId(candidateId)
        .jobId(jobId)
        .build();

    return applyJobRepository.save(applyJob);
  }
}
