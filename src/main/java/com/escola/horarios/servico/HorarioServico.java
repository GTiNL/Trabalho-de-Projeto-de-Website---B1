package com.escola.horarios.servico;

import com.escola.horarios.modelo.Horario;
import com.escola.horarios.modelo.HorarioVisualizacao;
import com.escola.horarios.modelo.Professor;
import com.escola.horarios.modelo.Disciplina;
import com.escola.horarios.modelo.Turma;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Armazena os horários de aula em memória (sem banco de dados)
 * e monta as visualizações (HorarioVisualizacao) usadas nas telas.
 */
@Service
public class HorarioServico {

    private final Map<Long, Horario> horarios = new ConcurrentHashMap<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    private final ProfessorServico professorServico;
    private final DisciplinaServico disciplinaServico;
    private final TurmaServico turmaServico;

    public HorarioServico(ProfessorServico professorServico,
                           DisciplinaServico disciplinaServico,
                           TurmaServico turmaServico) {
        this.professorServico = professorServico;
        this.disciplinaServico = disciplinaServico;
        this.turmaServico = turmaServico;
    }

    public List<Horario> listarTodos() {
        return new ArrayList<>(horarios.values());
    }

    public Optional<Horario> buscarPorId(Long id) {
        return Optional.ofNullable(horarios.get(id));
    }

    public Horario salvar(Horario horario) {
        if (horario.getId() == null) {
            horario.setId(proximoId.getAndIncrement());
        }
        horarios.put(horario.getId(), horario);
        return horario;
    }

    public void excluir(Long id) {
        horarios.remove(id);
    }

    public void excluirPorProfessor(Long professorId) {
        horarios.values().removeIf(h -> h.getProfessorId().equals(professorId));
    }

    public void excluirPorDisciplina(Long disciplinaId) {
        horarios.values().removeIf(h -> h.getDisciplinaId().equals(disciplinaId));
    }

    public void excluirPorTurma(Long turmaId) {
        horarios.values().removeIf(h -> h.getTurmaId().equals(turmaId));
    }

    public List<HorarioVisualizacao> listarVisualizacoes() {
        return montarVisualizacoes(listarTodos());
    }

    public List<HorarioVisualizacao> listarVisualizacoesPorProfessor(Long professorId) {
        List<Horario> filtrados = horarios.values().stream()
                .filter(h -> h.getProfessorId().equals(professorId))
                .collect(Collectors.toList());
        return montarVisualizacoes(filtrados);
    }

    public List<HorarioVisualizacao> listarVisualizacoesPorTurma(Long turmaId) {
        List<Horario> filtrados = horarios.values().stream()
                .filter(h -> h.getTurmaId().equals(turmaId))
                .collect(Collectors.toList());
        return montarVisualizacoes(filtrados);
    }

    private List<HorarioVisualizacao> montarVisualizacoes(List<Horario> lista) {
        return lista.stream()
                .sorted(Comparator.comparing(Horario::getDiaSemana)
                        .thenComparing(Horario::getHoraInicio))
                .map(h -> {
                    String professorNome = professorServico.buscarPorId(h.getProfessorId())
                            .map(Professor::getNome).orElse("(professor removido)");
                    String disciplinaNome = disciplinaServico.buscarPorId(h.getDisciplinaId())
                            .map(Disciplina::getNome).orElse("(disciplina removida)");
                    String turmaNome = turmaServico.buscarPorId(h.getTurmaId())
                            .map(Turma::getNome).orElse("(turma removida)");
                    return new HorarioVisualizacao(h.getId(), professorNome, disciplinaNome, turmaNome,
                            h.getDiaSemana(), h.getHoraInicio(), h.getHoraFim());
                })
                .collect(Collectors.toList());
    }
}
