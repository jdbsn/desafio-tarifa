package com.ras.desafio_tarifa;

import com.ras.desafio_tarifa.dominio.TabelaTarifaria;
import com.ras.desafio_tarifa.dto.request.TabelaTarifariaRequest;
import com.ras.desafio_tarifa.dto.response.TabelaTarifariaResponse;
import com.ras.desafio_tarifa.repositorio.TabelaMapper;
import com.ras.desafio_tarifa.repositorio.TabelaTarifariaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TabelaTarifariaService {

  private final TabelaTarifariaRepositorio tabelaRepo;

  public TabelaTarifariaResponse criar(TabelaTarifariaRequest tabelaTarifaria) {
    TabelaTarifaria tabela = TabelaMapper.toEntity(tabelaTarifaria);

    tabelaRepo.save(tabela);

    return TabelaMapper.toResponse(tabela);
  }

  public List<TabelaTarifariaResponse> listar() {
    return tabelaRepo.findAll().stream()
        .map(TabelaMapper::toResponse)
        .toList();
  }

  public void excluir(Long id) {
    TabelaTarifaria tabela = tabelaRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Tabela tarifária não encontrada"));

    tabelaRepo.delete(tabela);
  }

}
