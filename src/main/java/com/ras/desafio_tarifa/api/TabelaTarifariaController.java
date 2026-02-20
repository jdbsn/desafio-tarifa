package com.ras.desafio_tarifa.api;

import com.ras.desafio_tarifa.dto.request.TabelaTarifariaRequest;
import com.ras.desafio_tarifa.TabelaTarifariaService;
import com.ras.desafio_tarifa.dto.response.TabelaTarifariaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tabelas-tarifarias")
@RequiredArgsConstructor
public class TabelaTarifariaController {

  private final TabelaTarifariaService tarifariaService;

  @PostMapping
  public ResponseEntity<TabelaTarifariaResponse> criar(@RequestBody TabelaTarifariaRequest tabela) {
    TabelaTarifariaResponse salva = tarifariaService.criar(tabela);

    return ResponseEntity.status(HttpStatus.CREATED).body(salva);
  }

  @GetMapping
  public ResponseEntity<List<TabelaTarifariaResponse>> listar() {
    return ResponseEntity.status(HttpStatus.OK).body(tarifariaService.listar());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluir(@PathVariable Long id) {
    tarifariaService.excluir(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
