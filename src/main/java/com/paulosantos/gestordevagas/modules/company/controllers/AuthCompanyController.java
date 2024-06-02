package com.paulosantos.gestordevagas.modules.company.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paulosantos.gestordevagas.exceptions.AuthUnauthorizationException;
import com.paulosantos.gestordevagas.modules.company.dto.AuthCompanyRequestDTO;
import com.paulosantos.gestordevagas.modules.company.dto.AuthCompanyResponseDTO;
import com.paulosantos.gestordevagas.modules.company.useCases.AuthCompanyUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/company")
public class AuthCompanyController {

  @Autowired
  private AuthCompanyUseCase authCompanyUseCase;

  @PostMapping("/auth")
  @Tag(name = "Compania", description = "Informações do Candidato")
  @Operation(summary = "Login do Compania", description = "Essa funcão é responsavel por autenticar  o compania.")
  public ResponseEntity<Object> create(@RequestBody AuthCompanyRequestDTO authCompanyDTO)
      throws AuthenticationException {
    try {
      AuthCompanyResponseDTO token = this.authCompanyUseCase.execute(authCompanyDTO);

      return ResponseEntity.ok(token);
    } catch (AuthUnauthorizationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

  }

}
