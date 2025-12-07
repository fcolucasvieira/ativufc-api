package br.ufc.ativufc.utils;

import java.util.ArrayList;
import java.util.List;

public class PasswordValidator {

    public static List<String> validarSenha(String senha) {
        List<String> falhas = new ArrayList<>();

        if (senha == null || senha.length() < 8)
            falhas.add("A senha deve possuir pelo menos 8 caracteres.");

        if (!senha.matches(".*[A-Z].*"))
            falhas.add("A senha deve possuir ao menos uma letra maiúscula.");

        if (!senha.matches(".*[a-z].*"))
            falhas.add("A senha deve possuir ao menos uma letra minúscula.");

        if (!senha.matches(".*[0-9].*"))
            falhas.add("A senha deve possuir pelo menos um dígito numérico.");

        if (!senha.matches(".*[^A-Za-z0-9].*"))
            falhas.add("A senha deve possuir pelo menos um caractere especial (e.g, !@#$%).");

        return falhas;
    }
}
