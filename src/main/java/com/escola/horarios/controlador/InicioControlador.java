package com.escola.horarios.controlador;

import com.escola.horarios.servico.HorarioServico;
import com.escola.horarios.servico.ProfessorServico;
import com.escola.horarios.servico.TurmaServico;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InicioControlador {

    private final ProfessorServico professorServico;
    private final TurmaServico turmaServico;
    private final HorarioServico horarioServico;

    public InicioControlador(ProfessorServico professorServico,
                              TurmaServico turmaServico,
                              HorarioServico horarioServico) {
        this.professorServico = professorServico;
        this.turmaServico = turmaServico;
        this.horarioServico = horarioServico;
    }

    @GetMapping("/")
    public String inicio() {
        return "inicio";
    }

    @GetMapping("/horarios/por-professor")
    public String porProfessor(@RequestParam(required = false) Long professorId, Model model) {
        model.addAttribute("professores", professorServico.listarTodos());
        model.addAttribute("professorId", professorId);
        if (professorId != null) {
            model.addAttribute("professorSelecionado", professorServico.buscarPorId(professorId).orElse(null));
            model.addAttribute("horarios", horarioServico.listarVisualizacoesPorProfessor(professorId));
        }
        return "horarios/por-professor";
    }

    @GetMapping("/horarios/por-turma")
    public String porTurma(@RequestParam(required = false) Long turmaId, Model model) {
        model.addAttribute("turmas", turmaServico.listarTodos());
        model.addAttribute("turmaId", turmaId);
        if (turmaId != null) {
            model.addAttribute("turmaSelecionada", turmaServico.buscarPorId(turmaId).orElse(null));
            model.addAttribute("horarios", horarioServico.listarVisualizacoesPorTurma(turmaId));
        }
        return "horarios/por-turma";
    }
}
