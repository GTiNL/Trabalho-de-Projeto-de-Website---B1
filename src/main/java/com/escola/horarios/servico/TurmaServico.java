package com.escola.horarios.servico;

import com.escola.horarios.modelo.Turma;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Armazena as turmas em memória (sem banco de dados).
 */
@Service
public class TurmaServico {

    private final Map<Long, Turma> turmas = new ConcurrentHashMap<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public TurmaServico() {
        salvar(criar("3º Ano A", "2026"));
        salvar(criar("2º Ano B", "2026"));
    }

    public List<Turma> listarTodos() {
        return turmas.values().stream()
                .sorted(Comparator.comparing(Turma::getNome, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public Optional<Turma> buscarPorId(Long id) {
        return Optional.ofNullable(turmas.get(id));
    }

    public Turma salvar(Turma turma) {
        if (turma.getId() == null) {
            turma.setId(proximoId.getAndIncrement());
        }
        turmas.put(turma.getId(), turma);
        return turma;
    }

    public void excluir(Long id) {
        turmas.remove(id);
    }

    private Turma criar(String nome, String anoLetivo) {
        Turma t = new Turma();
        t.setNome(nome);
        t.setAnoLetivo(anoLetivo);
        return t;
    }
}
