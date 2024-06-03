package com.paulosantos.gestordevagas.modules.candidate.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paulosantos.gestordevagas.modules.candidate.entity.ApplyJobEntity;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {

}
