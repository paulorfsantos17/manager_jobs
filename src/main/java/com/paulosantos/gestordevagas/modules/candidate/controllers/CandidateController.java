package com.paulosantos.gestordevagas.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paulosantos.gestordevagas.exceptions.CandidateFoundException;
import com.paulosantos.gestordevagas.exceptions.UserFoundException;
import com.paulosantos.gestordevagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.paulosantos.gestordevagas.modules.candidate.entity.ApplyJobEntity;
import com.paulosantos.gestordevagas.modules.candidate.entity.CandidateEntity;
import com.paulosantos.gestordevagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import com.paulosantos.gestordevagas.modules.candidate.useCases.CreateCandidateUseCase;
import com.paulosantos.gestordevagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import com.paulosantos.gestordevagas.modules.candidate.useCases.ProfileCandidateUseCase;
import com.paulosantos.gestordevagas.modules.company.entities.JobEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do Candidato")
public class CandidateController {

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private ListAllJobsByFilterUseCase allJobsByFilterUseCase;

  @Autowired
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @PostMapping("/")
  @Operation(summary = "Cadastro do Candidato", description = "Essa funcão é responsavel por inserir um novo candidato.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = CandidateEntity.class))
      }),
      @ApiResponse(responseCode = "404", description = "Candidato já existe.")
  })
  public ResponseEntity<Object> createCandidate(@Valid @RequestBody CandidateEntity candidateEntity) {
    try {
      var result = this.createCandidateUseCase.execute(candidateEntity);
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    } catch (CandidateFoundException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @GetMapping("/")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Perfil do Candidato", description = "Essa funcão é responsavel por buscar  as infomações de perfil do candidato")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
      }),
      @ApiResponse(responseCode = "404", description = "Candidato Não existe.")
  })
  @SecurityRequirement(name = "jwt_auth")
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

  @GetMapping("/job")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Listagem de vagas disponível para o candidato", description = "Essa funcão é responsavel por listar todas as vagas disponíveis, baseada no filtro")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
      })
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> findJobByFilter(@RequestParam String filter) {
    try {
      List<JobEntity> jobs = this.allJobsByFilterUseCase.execute(filter);
      return ResponseEntity.ok(jobs);

    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/job/apply")
  @PreAuthorize("hasRole('CANDIDATE')")
  @SecurityRequirement(name = "jwt_auth")
  @Operation(summary = "Inscrição do candidato para uma vaga", description = "Essa funcão é responsavel por realizar a inscrição do candidato a uma vaga.")
  public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID idJob) {
    var idCandidate = request.getAttribute(("candidate_id"));

    try {
      ApplyJobEntity result = this.applyJobCandidateUseCase.execute(idJob, UUID.fromString(idCandidate.toString()));

      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
