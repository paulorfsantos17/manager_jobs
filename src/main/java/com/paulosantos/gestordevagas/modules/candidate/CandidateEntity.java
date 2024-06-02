package com.paulosantos.gestordevagas.modules.candidate;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidate")
public class CandidateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank
  @Schema(example = "José de Souza", requiredMode = RequiredMode.REQUIRED, description = "Nome do Candidato.")
  private String name;

  @NotBlank
  @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço.")
  @Schema(example = "danieldesouza", requiredMode = RequiredMode.REQUIRED, description = "Username do Candidato.")
  private String username;

  @NotBlank
  @Email(message = "O campo [email] deve conter um e-mail válido.")
  @Schema(example = "daniel@example.com", requiredMode = RequiredMode.REQUIRED, description = "Email do Candidato.")
  private String email;

  @Length(min = 8, max = 100, message = "O campo [password] deve conter no mínimo 8 caracteres e no máximo 100.")
  @Schema(example = "admin@1234", minLength = 8, maxLength = 100, requiredMode = RequiredMode.REQUIRED, description = "Senha do Candidato.")
  private String password;
  @Schema(example = "Desenvolvedor Java", requiredMode = RequiredMode.REQUIRED, description = "Breve descrição do Candidato.")
  private String description;
  private String curriculum;

  @CreationTimestamp
  private LocalDateTime createAt;

}