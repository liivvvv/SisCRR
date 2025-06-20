package util;

import org.mindrot.jbcrypt.BCrypt;

public class GeradorDeSenha {
    public static void main(String[] args) {
        String senhaAdmin = "2025";

        // Gera o hash
        String hash = BCrypt.hashpw(senhaAdmin, BCrypt.gensalt(12));

        // Imprime o hash para você copiar e colar no SQL
        System.out.println("Sua senha criptografada é:");
        System.out.println(hash);
    }
}
