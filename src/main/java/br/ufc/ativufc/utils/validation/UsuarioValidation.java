package br.ufc.ativufc.utils.validation;

import br.ufc.ativufc.exception.AlreadyExistsException;
import br.ufc.ativufc.exception.OperationNotAllowedException;
import br.ufc.ativufc.model.enums.Perfil;
import br.ufc.ativufc.repository.UsuarioRepository;

public class UsuarioValidation {
    public static void validarPerfilAdmin(Perfil perfil){
        if(!Perfil.ADMIN.equals(perfil))
            throw new OperationNotAllowedException("Somente ADMIN permitido para este cadastro");
    }
}
