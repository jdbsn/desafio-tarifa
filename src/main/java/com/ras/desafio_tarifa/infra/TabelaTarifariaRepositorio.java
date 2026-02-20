package com.ras.desafio_tarifa.infra;

import com.ras.desafio_tarifa.dominio.TabelaTarifaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TabelaTarifariaRepositorio extends JpaRepository<TabelaTarifaria, Long> {

  Optional<TabelaTarifaria> findByAtivaTrue();

}
