package com.paulosantos.gestordevagas.modules.company.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity(name = "job")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Schema(example = "Vaga para Desing")
  private String description;
  
  @Schema(example = "Plano de sáude,GymPass")
  private String benefits;

  @NotBlank(message = "Campo [nível] é obrigatório. ")
  @Schema(example = "SENIOR")
  private String level;

  @ManyToOne()
  @JoinColumn(name = "company_id", insertable = false, updatable = false)
  private CompanyEntity companyEntity;

  @Column(name = "company_id", nullable = false)
  private UUID companyId;

  @CreationTimestamp
  private LocalDateTime createAt;
}
