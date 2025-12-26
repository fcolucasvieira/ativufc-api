package br.ufc.ativufc.utils.validation;

import br.ufc.ativufc.exception.NotFoundException;
import br.ufc.ativufc.exception.OperationNotAllowedException;
import br.ufc.ativufc.model.Comprovante;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ComprovanteValidation {
    private static final long MAX_SIZE = 10_000_000; // 10 MB

    public static void validarArquivo(MultipartFile file){
        if(file.isEmpty())
            throw new OperationNotAllowedException("Arquivo vazio não pode ser enviado");
        if(file.getSize() > MAX_SIZE)
            throw new OperationNotAllowedException("Arquivo excede tamanho permitido");
    }

    public static void validarArquivoAntigo(Comprovante comprovante){
        if(comprovante == null)
            throw new NotFoundException("Solicitação não possui comprovante para atualizar");
    }
}
