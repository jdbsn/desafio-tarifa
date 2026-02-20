package com.ras.desafio_tarifa.api;

import com.ras.desafio_tarifa.aplicacao.CalculoService;
import com.ras.desafio_tarifa.dto.request.CalculoRequest;
import com.ras.desafio_tarifa.dto.response.CalculoTarifaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculos")
@RequiredArgsConstructor
public class CalculoTarifaController {

  private final CalculoService calculoService;

  @PostMapping
  public ResponseEntity<CalculoTarifaResponse> calcular(@RequestBody CalculoRequest calculoRequest) {
    CalculoTarifaResponse resultado = calculoService.calcular(calculoRequest);

    return ResponseEntity.ok(resultado);
  }

}
