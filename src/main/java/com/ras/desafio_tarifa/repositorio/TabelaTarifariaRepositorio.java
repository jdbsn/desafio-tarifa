package com.ras.desafio_tarifa.repositorio;

import com.ras.desafio_tarifa.dominio.TabelaTarifaria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TabelaTarifariaRepositorio extends JpaRepository<TabelaTarifaria, Long> {
}
