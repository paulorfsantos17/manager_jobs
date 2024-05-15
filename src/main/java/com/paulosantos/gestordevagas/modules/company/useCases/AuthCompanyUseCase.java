package com.paulosantos.gestordevagas.modules.company.useCases;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.paulosantos.gestordevagas.modules.company.dto.AuthCompanyDTO;
import com.paulosantos.gestordevagas.modules.company.entities.CompanyEntity;
import com.paulosantos.gestordevagas.modules.company.repositories.CompanyRepository;

import java.time.Instant;
import java.time.Duration;

@Service
public class AuthCompanyUseCase {

  @Value("${security.token.secret}")
  private String secretKey;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncode;

  public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
    CompanyEntity company = this.companyRepository.findByUsername(authCompanyDTO.getUsername())
        .orElseThrow(() -> {
          throw new RuntimeException("Usuário ou username incorreto.");
        });

    boolean passwordMatches = this.passwordEncode.matches(authCompanyDTO.getPassword(), company.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    String token = JWT.create().withIssuer("javagas")
        .withSubject(company.getId().toString())
        .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
        .sign(algorithm);

    return token;
  }

}
