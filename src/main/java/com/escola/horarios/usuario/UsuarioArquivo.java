package com.escola.horarios.usuario;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;
import org.springframework.stereotype.Component;

/** Base de usuarios em arquivo texto: uma linha por usuario -> email;hashDaSenha */
@Component
public class UsuarioArquivo {
    private final Path arquivo = Paths.get("usuarios.txt");

    private synchronized Map<String, String> ler() {
        Map<String, String> m = new LinkedHashMap<>();
        try {
            if (Files.exists(arquivo))
                for (String l : Files.readAllLines(arquivo)) {
                    String[] p = l.split(";", 2);
                    if (p.length == 2) m.put(p[0], p[1]);
                }
        } catch (Exception e) { throw new RuntimeException(e); }
        return m;
    }

    private synchronized void gravar(Map<String, String> m) {
        List<String> linhas = new ArrayList<>();
        m.forEach((e, s) -> linhas.add(e + ";" + s));
        try { Files.write(arquivo, linhas); } catch (Exception e) { throw new RuntimeException(e); }
    }

    private static String hash(String senha) {
        try {
            byte[] b = MessageDigest.getInstance("SHA-256").digest(senha.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(b);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public boolean existe(String email) { return ler().containsKey(email.toLowerCase()); }

    public boolean login(String email, String senha) {
        return hash(senha).equals(ler().get(email.toLowerCase()));
    }

    public synchronized void salvarSenha(String email, String senha) {
        Map<String, String> m = ler();
        m.put(email.toLowerCase(), hash(senha));
        gravar(m);
    }
}
