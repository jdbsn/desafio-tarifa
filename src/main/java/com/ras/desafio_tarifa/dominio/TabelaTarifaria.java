package com.ras.desafio_tarifa.dominio;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tabela_tarifaria")
@Data
public class TabelaTarifaria {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private LocalDate dataVigencia;

  @Column(nullable = false)
  private boolean ativa;

  @OneToMany(mappedBy = "tabelaTarifaria", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Faixa> faixas = new ArrayList<>();

}
