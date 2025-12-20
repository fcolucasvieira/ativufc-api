package br.ufc.ativufc.utils.validation;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senha = "L1u2c3a4s5!";
        String hash = encoder.encode(senha);
        System.out.println("Senha original: " + senha);
        System.out.println("Hash gerado: " + hash);
    }
}
