package com.paulosantos.gestordevagas.modules.candidate.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paulosantos.gestordevagas.modules.company.entities.JobEntity;
import com.paulosantos.gestordevagas.modules.company.repositories.JobRepository;

@Service
public class ListAllJobsByFilterUseCase {
  @Autowired
  private JobRepository jobRepository;

  public List<JobEntity> execute(String filter) {
    return this.jobRepository.findByDescriptionContaining(filter);
  }
}
