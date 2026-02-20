package com.ras.desafio_tarifa.api;

import com.ras.desafio_tarifa.dto.response.ErroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErroResponse> talentosGaranhunsExcecao(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroResponse(ex.getMessage()));
  }

}
