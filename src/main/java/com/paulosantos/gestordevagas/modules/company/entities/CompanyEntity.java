package com.paulosantos.gestordevagas.modules.company.entities;

import lombok.Data;

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

@Data
@Entity(name = "company")
public class CompanyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank(message = "O campo [nome] não pode ser vazio.")
  private String name;

  @NotBlank
  @Email(message = "O campo [email] deve conter um e-mail válido.")
  private String email;

  @NotBlank
  @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço.")
  private String username;

  @Length(min = 8, max = 100, message = "O campo [password] deve conter no mínimo 8 caracteres e no máximo 100.")
  private String password;

  private String website;
  private String description;

  @CreationTimestamp
  private LocalDateTime createAt;
}
