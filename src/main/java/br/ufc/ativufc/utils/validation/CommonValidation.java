package br.ufc.ativufc.utils.validation;

import br.ufc.ativufc.exception.AlreadyExistsException;
import br.ufc.ativufc.exception.OperationNotAllowedException;
import br.ufc.ativufc.repository.UsuarioRepository;
import br.ufc.ativufc.utils.PasswordValidator;

import java.util.List;
import java.util.stream.Collectors;

public class CommonValidation {
    public static void validarEmailUnico(UsuarioRepository repository, String email) {
        if (repository.existsByEmail(email))
            throw new AlreadyExistsException("Usuário com este email já cadastrado");
    }

    public static void validarSenhaForte(String senha){
        List<String> falhas = PasswordValidator.validarSenha(senha);
        if (!falhas.isEmpty()) {
            String mensagem = "Senha inválida:\n" + falhas.stream()
                    .map(f -> "- " + f)
                    .collect(Collectors.joining("\n"));
            throw new OperationNotAllowedException(mensagem);
        }
    }
}
