package com.ras.desafio_tarifa.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record TabelaTarifariaRequest(
    String nome,
    LocalDate dataVigencia,
    Boolean ativa,
    List<CategoriaRequest> categorias
) {

  public record FaixaRequest(
      Integer inicio,
      Integer fim,
      BigDecimal valorUnitario
  ) {}

  public record CategoriaRequest(
      String nome,
      List<FaixaRequest> faixas
  ) {}

}
