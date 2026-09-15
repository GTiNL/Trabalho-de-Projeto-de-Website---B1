package com.escola.horarios.servico;

import com.escola.horarios.modelo.Professor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Armazena os professores em memória (sem banco de dados),
 * seguindo a ideia de manter o projeto simples.
 */
@Service
public class ProfessorServico {

    private final Map<Long, Professor> professores = new ConcurrentHashMap<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public ProfessorServico() {
        salvar(criar("Ana Souza", "ana.souza@escola.com", "(27) 99999-0001"));
        salvar(criar("Carlos Lima", "carlos.lima@escola.com", "(27) 99999-0002"));
    }

    public List<Professor> listarTodos() {
        return professores.values().stream()
                .sorted(Comparator.comparing(Professor::getNome, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public Optional<Professor> buscarPorId(Long id) {
        return Optional.ofNullable(professores.get(id));
    }

    public Professor salvar(Professor professor) {
        if (professor.getId() == null) {
            professor.setId(proximoId.getAndIncrement());
        }
        professores.put(professor.getId(), professor);
        return professor;
    }

    public void excluir(Long id) {
        professores.remove(id);
    }

    private Professor criar(String nome, String email, String telefone) {
        Professor p = new Professor();
        p.setNome(nome);
        p.setEmail(email);
        p.setTelefone(telefone);
        return p;
    }
}
