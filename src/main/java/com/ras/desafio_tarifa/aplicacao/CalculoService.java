package com.ras.desafio_tarifa.aplicacao;

import com.ras.desafio_tarifa.dominio.Categoria;
import com.ras.desafio_tarifa.dominio.Faixa;
import com.ras.desafio_tarifa.dominio.TabelaTarifaria;
import com.ras.desafio_tarifa.dto.request.CalculoRequest;
import com.ras.desafio_tarifa.dto.response.CalculoTarifaResponse;
import com.ras.desafio_tarifa.infra.TabelaTarifariaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculoService {

  public final TabelaTarifariaRepositorio tabelaRepo;

  public CalculoTarifaResponse calcular(CalculoRequest calculoRequest) {

    TabelaTarifaria tabela = tabelaRepo
        .findByAtivaTrue()
        .orElseThrow(() -> new IllegalArgumentException("Tabela não encontrada"));

    Categoria categoria = Categoria.valueOf(calculoRequest.categoria());

    List<Faixa> faixas = tabela.getFaixas().stream()
        .filter(f -> f.getCategoria() == categoria)
        .sorted(Comparator.comparing(Faixa::getInicio))
        .toList();

    int restante = calculoRequest.consumo();
    BigDecimal valorTotal = BigDecimal.ZERO;

    List<CalculoTarifaResponse.DetalhamentoResponse> detalhamento = new ArrayList<>();

    for (Faixa faixa : faixas) {
      if (restante <= 0) break;

      int capacidadeDaFaixa = faixa.getInicio() == 0
          ? faixa.getFim() : faixa.getFim() - faixa.getInicio() + 1;

      int m3Cobrados = Math.min(restante, capacidadeDaFaixa);

      BigDecimal subtotal = faixa.getValorUnitario()
          .multiply(BigDecimal.valueOf(m3Cobrados));

      valorTotal = valorTotal.add(subtotal);

      detalhamento.add(
          new CalculoTarifaResponse.DetalhamentoResponse(
              new CalculoTarifaResponse.FaixaResponse(faixa.getInicio(), faixa.getFim()),
              m3Cobrados, faixa.getValorUnitario(), subtotal
          )
      );

      restante -= m3Cobrados;
    }

    return new CalculoTarifaResponse(calculoRequest.categoria(),
        calculoRequest.consumo(), valorTotal, detalhamento);
  }
}
