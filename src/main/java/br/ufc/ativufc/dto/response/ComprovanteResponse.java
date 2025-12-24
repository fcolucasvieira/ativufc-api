package br.ufc.ativufc.dto.response;

import java.time.LocalDateTime;

public record ComprovanteResponse(Long id,
                                  String nomeOriginal,
                                  String contentType,
                                  Long tamanho,
                                  String caminho,
                                  LocalDateTime uploadAt) {
}
