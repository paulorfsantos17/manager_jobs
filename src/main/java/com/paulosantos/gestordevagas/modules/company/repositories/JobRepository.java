package com.paulosantos.gestordevagas.modules.company.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paulosantos.gestordevagas.modules.company.entities.JobEntity;
import java.util.List;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
  List<JobEntity> findByDescriptionContaining(String filter);

}
