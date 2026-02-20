package com.ras.desafio_tarifa.infra;

import com.ras.desafio_tarifa.dominio.Categoria;
import com.ras.desafio_tarifa.dominio.Faixa;
import com.ras.desafio_tarifa.dominio.TabelaTarifaria;
import com.ras.desafio_tarifa.dto.request.TabelaTarifariaRequest;
import com.ras.desafio_tarifa.dto.response.TabelaTarifariaResponse;

import java.util.List;
import java.util.stream.Collectors;

public final class TabelaMapper {

  private TabelaMapper() {}

  public static TabelaTarifaria toEntity(TabelaTarifariaRequest request) {
    TabelaTarifaria tabela = new TabelaTarifaria();

    tabela.setNome(request.nome());
    tabela.setDataVigencia(request.dataVigencia());
    tabela.setAtiva(Boolean.TRUE.equals(request.ativa()));

    List<Faixa> faixas = request.categorias().stream()
        .flatMap(cat -> cat.faixas().stream()
            .map(f -> Faixa.builder()
                .categoria(Categoria.valueOf(cat.nome()))
                .inicio(f.inicio())
                .fim(f.fim())
                .valorUnitario(f.valorUnitario())
                .tabelaTarifaria(tabela)
                .build()
            )
        )
        .toList();

    tabela.setFaixas(faixas);

    return tabela;
  }

  public static TabelaTarifariaResponse toResponse(TabelaTarifaria tabela) {

    return new TabelaTarifariaResponse(
        tabela.getId(),
        tabela.getNome(),
        tabela.getDataVigencia(),
        tabela.isAtiva(),
        tabela.getFaixas().stream()
            .collect(Collectors.groupingBy(Faixa::getCategoria))
            .entrySet().stream()
            .map(entry -> new TabelaTarifariaResponse.CategoriaResponse(
                entry.getKey(),
                entry.getValue().stream()
                    .map(TabelaTarifariaResponse.FaixaResponse::from)
                    .toList()
            ))
            .toList()
    );
  }

}
