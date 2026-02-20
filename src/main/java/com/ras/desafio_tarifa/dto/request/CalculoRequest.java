package com.ras.desafio_tarifa.dto.request;

public record CalculoRequest(
    String categoria,
    int consumo
) {
}
