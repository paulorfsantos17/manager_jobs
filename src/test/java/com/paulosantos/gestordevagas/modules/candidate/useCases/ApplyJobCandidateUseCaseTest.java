package com.paulosantos.gestordevagas.modules.candidate.useCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.paulosantos.gestordevagas.exceptions.CandidateNotFoundException;
import com.paulosantos.gestordevagas.exceptions.JobNotFoundException;
import com.paulosantos.gestordevagas.modules.candidate.Repositories.ApplyJobRepository;
import com.paulosantos.gestordevagas.modules.candidate.Repositories.CandidateRepository;
import com.paulosantos.gestordevagas.modules.candidate.entity.ApplyJobEntity;
import com.paulosantos.gestordevagas.modules.candidate.entity.CandidateEntity;
import com.paulosantos.gestordevagas.modules.company.entities.JobEntity;
import com.paulosantos.gestordevagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

  @InjectMocks
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private JobRepository jobRepository;

  @Mock
  private ApplyJobRepository applyJobRepository;

  @Test
  @DisplayName("should not be able to apply job with candidate not found")
  public void should_not_be_able_to_apply_job_with_candidate_not_found() {

    try {
      applyJobCandidateUseCase.execute(null, null);
    } catch (Exception e) {
      assertThat(e, Matchers.instanceOf(CandidateNotFoundException.class));
    }
  }

  @Test
  @DisplayName("should not be able to apply job with job not found")
  public void should_not_be_able_to_apply_job_with_job_not_found() {
    UUID candidateId = UUID.randomUUID();

    CandidateEntity candidate = new CandidateEntity();
    candidate.setId(candidateId);

    when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

    try {
      applyJobCandidateUseCase.execute(null, candidateId);
    } catch (Exception e) {
      assertThat(e, Matchers.instanceOf(JobNotFoundException.class));
    }
  }

  @Test
  public void should_be_able_to_create_a_new_apply_job() {
    UUID candidateId = UUID.randomUUID();
    UUID jobId = UUID.randomUUID();

    ApplyJobEntity applyJob = ApplyJobEntity.builder()
        .jobId(jobId)
        .candidateId(candidateId)
        .build();

    var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

    when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(new CandidateEntity()));
    when(jobRepository.findById(jobId)).thenReturn(Optional.of(new JobEntity()));
    when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

    var result = applyJobCandidateUseCase.execute(jobId, candidateId);

    assertThat(result, hasProperty("id"));
    assertNotNull(result.getId());

  }

}
