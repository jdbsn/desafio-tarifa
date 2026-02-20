package com.ras.desafio_tarifa.aplicacao;

import com.ras.desafio_tarifa.dominio.TabelaTarifaria;
import com.ras.desafio_tarifa.dto.request.TabelaTarifariaRequest;
import com.ras.desafio_tarifa.dto.response.TabelaTarifariaResponse;
import com.ras.desafio_tarifa.infra.TabelaMapper;
import com.ras.desafio_tarifa.infra.TabelaTarifariaRepositorio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TabelaTarifariaService {

  private static final String MSG_SEM_FAIXAS = "A categoria %s deve possuir pelo menos uma faixa.";

  private static final String MSG_INICIO_INVALIDO = "A primeira faixa da categoria %s deve iniciar em 0.";

  private static final String MSG_FAIXA_INTERVALO_INVALIDO =
      "Intervalo da faixa na categoria %s inválido: valor inicial deve ser menor que o valor final.";

  private static final String MSG_FAIXAS_DESCONTINUIDADE =
      "As faixas da categoria %s devem ser contínuas e não podem possuir sobreposição.";

  private static final String MSG_COBERTURA_MAXIMA =
      "As faixas da categoria %s devem cobrir o consumo máximo permitido.";

  private final TabelaTarifariaRepositorio tabelaRepo;

  @Transactional
  public TabelaTarifariaResponse criar(TabelaTarifariaRequest tabelaTarifaria) {
    tabelaTarifaria.categorias().forEach(categoria ->
        validarFaixas(categoria.faixas(), categoria.nome()));

    if (Boolean.TRUE.equals(tabelaTarifaria.ativa())) {
      tabelaRepo.findByAtivaTrue().ifPresent(t -> t.setAtiva(false));
    }

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

      private void validarFaixas(List<TabelaTarifariaRequest.FaixaRequest> faixas, String categoria) {

        if (faixas == null || faixas.isEmpty()) {
          throw new IllegalArgumentException(String.format(MSG_SEM_FAIXAS, categoria));
        }

        List<TabelaTarifariaRequest.FaixaRequest> ordenadas =
            faixas.stream()
                .sorted(Comparator.comparing(TabelaTarifariaRequest.FaixaRequest::inicio))
                .toList();

        if (ordenadas.getFirst().inicio() != 0) {
          throw new IllegalArgumentException(String.format(MSG_INICIO_INVALIDO, categoria));
        }

        for (int i = 0; i < ordenadas.size(); i++) {
          var atual = ordenadas.get(i);

          if (atual.inicio() >= atual.fim()) {
            throw new IllegalArgumentException(String.format(MSG_FAIXA_INTERVALO_INVALIDO, categoria));
          }

          if (i > 0) {
            var anterior = ordenadas.get(i - 1);

            if (atual.inicio() != anterior.fim() + 1) {
              throw new IllegalArgumentException(String.format(MSG_FAIXAS_DESCONTINUIDADE, categoria));
            }
          }
        }

        if (ordenadas.getLast().fim() < 99999) {
          throw new IllegalArgumentException(String.format(MSG_COBERTURA_MAXIMA, categoria));
        }
      }

}
