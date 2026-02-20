package com.ras.desafio_tarifa.dominio;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "faixa")
@Data
public class Faixa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer inicio;

  private Integer fim;

  private BigDecimal valorUnitario;

  @Enumerated(EnumType.STRING)
  private Categoria categoria;

  @ManyToOne
  @JoinColumn(name = "tabela_tarifaria_id", nullable = false)
  private TabelaTarifaria tabelaTarifaria;

}
