package com.paulosantos.gestordevagas.modules.candidate.useCases;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.paulosantos.gestordevagas.exceptions.AuthUnauthorizationException;
import com.paulosantos.gestordevagas.modules.candidate.CandidateEntity;
import com.paulosantos.gestordevagas.modules.candidate.CandidateRepository;
import com.paulosantos.gestordevagas.modules.candidate.dto.AuthCandidateRequestDTO;
import com.paulosantos.gestordevagas.modules.candidate.dto.AuthCandidateResponseDTO;

import java.time.Instant;
import java.time.Duration;
import java.util.Arrays;

@Service
public class AuthCandidateUseCase {

  @Value("${security.token.secret.cadidate}")
  private String secretKey;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncode;

  public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO)
      throws AuthenticationException {
    CandidateEntity candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
        .orElseThrow(() -> {
          throw new AuthUnauthorizationException();
        });

    var passwordMatches = this.passwordEncode.matches(authCandidateRequestDTO.password(),
        candidate.getPassword());

    System.out.println(passwordMatches);

    if (!passwordMatches) {
      throw new AuthUnauthorizationException();
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    var expiresIn = Instant.now().plus(Duration.ofMinutes(10));

    String token = JWT.create().withIssuer("javagas")
        .withSubject(candidate.getId().toString())
        .withClaim("roles", Arrays.asList("candidate"))
        .withExpiresAt(expiresIn)
        .sign(algorithm);

    AuthCandidateResponseDTO authCandidateResponse = AuthCandidateResponseDTO.builder()
        .access_token(token)
        .expires_in(expiresIn.toEpochMilli())
        .build();

    return authCandidateResponse;
  }

}
