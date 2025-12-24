package br.ufc.ativufc.utils.validation;

import br.ufc.ativufc.exception.OperationNotAllowedException;
import org.springframework.web.multipart.MultipartFile;

public class ComprovanteValidation {
    private static final long MAX_SIZE = 10_000_000; // 10 MB

    public static void validarArquivo(MultipartFile file){
        if(file.isEmpty())
            throw new OperationNotAllowedException("Arquivo vazio não pode ser enviado");
        if(file.getSize() > MAX_SIZE)
            throw new OperationNotAllowedException("Arquivo excede tamanho permitido");
    }
}
