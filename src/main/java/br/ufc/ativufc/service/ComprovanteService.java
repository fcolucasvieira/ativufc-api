package br.ufc.ativufc.service;

import br.ufc.ativufc.dto.response.ComprovanteResponse;
import br.ufc.ativufc.exception.NotFoundException;
import br.ufc.ativufc.exception.OperationNotAllowedException;
import br.ufc.ativufc.model.Comprovante;
import br.ufc.ativufc.model.Solicitacao;
import br.ufc.ativufc.repository.ComprovanteRepository;
import br.ufc.ativufc.repository.SolicitacaoRepository;
import br.ufc.ativufc.utils.validation.ComprovanteValidation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComprovanteService {

    @Value("${app.uploads.dir}")
    private String uploadDir;

    private final ComprovanteRepository comprovanteRepository;
    private final SolicitacaoRepository solicitacaoRepository;

    public ComprovanteService(ComprovanteRepository comprovanteRepository,
                              SolicitacaoRepository solicitacaoRepository) {
        this.comprovanteRepository = comprovanteRepository;
        this.solicitacaoRepository = solicitacaoRepository;
    }

    @Transactional
    public ComprovanteResponse upload(Long solicitacaoId, MultipartFile file) {
        Solicitacao solicitacao = solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new NotFoundException("Solicitação não encontrada"));

        ComprovanteValidation.validarArquivo(file);

        Path destino = Paths.get(uploadDir, file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new OperationNotAllowedException("Erro ao salvar arquivo");
        }

        Comprovante comprovante = new Comprovante(
                null,
                destino.toString(),
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize(),
                LocalDateTime.now(),
                solicitacao
        );

        solicitacao.setComprovante(comprovante);
        comprovanteRepository.save(comprovante);
        return toResponse(comprovante);
    }

    public ComprovanteResponse buscarPorId(Long id) {
        Comprovante comprovante = comprovanteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comprovante não encontrado"));
        return toResponse(comprovante);
    }

    public List<ComprovanteResponse> listarTodos() {
        return comprovanteRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ComprovanteResponse atualizar(Long solicitacaoId, MultipartFile file) {
        Solicitacao solicitacao = solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new NotFoundException("Solicitação não encontrada"));

        ComprovanteValidation.validarArquivo(file);

        Comprovante comprovanteAntigo = solicitacao.getComprovante();
        if(comprovanteAntigo != null){
            try {
                Files.deleteIfExists(Paths.get(comprovanteAntigo.getCaminho()));
            } catch(IOException ex){
                throw new OperationNotAllowedException("Erro ao remover arquivo antigo");
            }
            comprovanteRepository.delete(comprovanteAntigo);
        }

        Path destino = Paths.get(uploadDir, file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new OperationNotAllowedException("Erro ao salvar arquivo");
        }

        Comprovante comprovanteNovo = new Comprovante(
                null,
                destino.toString(),
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize(),
                LocalDateTime.now(),
                solicitacao
        );

        solicitacao.setComprovante(comprovanteNovo);
        comprovanteRepository.save(comprovanteNovo);
        return toResponse(comprovanteNovo);
    }

    public ComprovanteResponse toResponse(Comprovante comprovante) {
        return new ComprovanteResponse(comprovante.getId(),
                comprovante.getNomeOriginal(),
                comprovante.getContentType(),
                comprovante.getTamanho(),
                comprovante.getCaminho(),
                comprovante.getUploadAt()
        );
    }
}
