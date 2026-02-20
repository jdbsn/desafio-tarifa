package com.ras.desafio_tarifa.dto.response;

import com.ras.desafio_tarifa.dominio.Categoria;
import com.ras.desafio_tarifa.dominio.Faixa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record TabelaTarifariaResponse(
    Long id,
    String nome,
    LocalDate dataVigencia,
    Boolean ativa,
    List<CategoriaResponse> categorias
) {

  public record CategoriaResponse(
      Categoria nome,
      List<FaixaResponse> faixas
  ) {}

  public record FaixaResponse(
      Integer inicio,
      Integer fim,
      BigDecimal valorUnitario
  ) {

    public static FaixaResponse from(Faixa faixa) {
      return new FaixaResponse(
          faixa.getInicio(),
          faixa.getFim(),
          faixa.getValorUnitario()
      );
    }

  }
}
