package com.paulosantos.gestordevagas.modules.candidate;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

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
  private String name;

  @NotBlank
  @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço.")
  private String username;

  @NotBlank
  @Email(message = "O campo [email] deve conter um e-mail válido.")
  private String email;

  @Length(min = 8, max = 100, message = "O campo [password] deve conter no mínimo 8 caracteres e no máximo 100.")
  private String password;
  private String description;
  private String curriculum;

  @CreationTimestamp
  private LocalDateTime createAt;

}