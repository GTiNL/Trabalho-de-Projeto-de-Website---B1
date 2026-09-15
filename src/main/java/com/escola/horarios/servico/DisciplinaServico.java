package com.escola.horarios.servico;

import com.escola.horarios.modelo.Disciplina;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Armazena as disciplinas em memória (sem banco de dados).
 */
@Service
public class DisciplinaServico {

    private final Map<Long, Disciplina> disciplinas = new ConcurrentHashMap<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public DisciplinaServico() {
        salvar(criar("Matemática", 80));
        salvar(criar("Português", 80));
    }

    public List<Disciplina> listarTodos() {
        return disciplinas.values().stream()
                .sorted(Comparator.comparing(Disciplina::getNome, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public Optional<Disciplina> buscarPorId(Long id) {
        return Optional.ofNullable(disciplinas.get(id));
    }

    public Disciplina salvar(Disciplina disciplina) {
        if (disciplina.getId() == null) {
            disciplina.setId(proximoId.getAndIncrement());
        }
        disciplinas.put(disciplina.getId(), disciplina);
        return disciplina;
    }

    public void excluir(Long id) {
        disciplinas.remove(id);
    }

    private Disciplina criar(String nome, int cargaHoraria) {
        Disciplina d = new Disciplina();
        d.setNome(nome);
        d.setCargaHoraria(cargaHoraria);
        return d;
    }
}
