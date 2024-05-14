package com.paulosantos.gestordevagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paulosantos.gestordevagas.modules.company.entities.CompanyEntity;
import com.paulosantos.gestordevagas.modules.company.useCases.CreateCompanyUseCase;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/company")
public class CompanyController {

  @Autowired
  private CreateCompanyUseCase createCompanyUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity) {
    try {
      var company = this.createCompanyUseCase.execute(companyEntity);
      return ResponseEntity.status(HttpStatus.CREATED).body(company);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

  }
}
