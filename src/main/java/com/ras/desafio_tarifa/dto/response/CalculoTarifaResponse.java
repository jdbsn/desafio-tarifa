package com.ras.desafio_tarifa.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record CalculoTarifaResponse(
    String categoria,
    int consumoTotal,
    BigDecimal valorTotal,
    List<DetalhamentoResponse> detalhamento
) {

  public record DetalhamentoResponse(
      FaixaResponse faixa,
      int m3Cobrados,
      BigDecimal valorUnitario,
      BigDecimal subtotal
  ) {}

  public record FaixaResponse(
      int inicio,
      int fim
  ) {}

}

