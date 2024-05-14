package com.paulosantos.gestordevagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paulosantos.gestordevagas.modules.company.entities.JobEntity;
import com.paulosantos.gestordevagas.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {

  @Autowired
  private JobRepository jobRepository;

  public JobEntity execute(JobEntity jobEntity) {
    return jobRepository.save(jobEntity);
  }
}
