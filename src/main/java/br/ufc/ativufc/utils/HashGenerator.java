package br.ufc.ativufc.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

public class HashGenerator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.print("Digite a senha: ");
        String senha = scanner.next();
        String hash = encoder.encode(senha);
        System.out.println("Senha original: " + senha);
        System.out.println("Hash gerado: " + hash);
    }
}
