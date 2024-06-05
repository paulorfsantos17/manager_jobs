package com.paulosantos.gestordevagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paulosantos.gestordevagas.exceptions.CompanyNotFoundException;
import com.paulosantos.gestordevagas.modules.company.dto.CreateJobDTO;
import com.paulosantos.gestordevagas.modules.company.entities.JobEntity;
import com.paulosantos.gestordevagas.modules.company.useCases.CreateJobUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
@Tag(name = "Vagas", description = "Informações do Vagas")
public class JobController {
  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping("/")
  @PreAuthorize("hasRole('COMPANY')")
  @Operation(summary = "Cadastro de vagas", description = "Essa funcão é responsavel por Cadastrar  as vagas dentro da empresa")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = JobEntity.class))
      })
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
    var companyId = request.getAttribute("company_id");

    JobEntity jobEntity = JobEntity.builder()
        .description(createJobDTO.getDescription())
        .benefits(createJobDTO.getBenefits())
        .level(createJobDTO.getLevel())
        .companyId(UUID.fromString(companyId.toString()))
        .build();

    try {
      JobEntity Job = this.createJobUseCase.execute(jobEntity);
      return ResponseEntity.status(HttpStatus.CREATED).body(Job);
    } catch (CompanyNotFoundException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
