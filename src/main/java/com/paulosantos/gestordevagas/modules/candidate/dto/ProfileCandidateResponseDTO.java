package com.paulosantos.gestordevagas.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {
  @Schema(example = "Jose17")
  private String username;
  @Schema(example = "Sobre o Candidato")
  private String description;
  @Schema(example = "Jose@example.com")
  private String email;
  private UUID id;
  @Schema(example = "Jose Maria")
  private String name;

}
