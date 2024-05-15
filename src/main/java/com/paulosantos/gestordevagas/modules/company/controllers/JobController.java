package com.paulosantos.gestordevagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paulosantos.gestordevagas.modules.company.dto.CreateJobDTO;
import com.paulosantos.gestordevagas.modules.company.entities.JobEntity;
import com.paulosantos.gestordevagas.modules.company.useCases.CreateJobUseCase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/job")
public class JobController {
  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
    var companyId = request.getAttribute("company_id");

    JobEntity jobEntity  = JobEntity.builder()
        .description(createJobDTO.getDescription())
        .benefits(createJobDTO.getBenefits())
        .level(createJobDTO.getLevel())
        .companyId(UUID.fromString(companyId.toString()))
        .build();

    try {
      JobEntity Job = this.createJobUseCase.execute(jobEntity);
      return ResponseEntity.status(HttpStatus.CREATED).body(Job);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
