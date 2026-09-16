package com.escola.horarios.controlador;

import com.escola.horarios.modelo.DiaSemana;
import com.escola.horarios.modelo.Horario;
import com.escola.horarios.servico.DisciplinaServico;
import com.escola.horarios.servico.HorarioServico;
import com.escola.horarios.servico.ProfessorServico;
import com.escola.horarios.servico.TurmaServico;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/horarios")
public class HorarioControlador {

    private final HorarioServico horarioServico;
    private final ProfessorServico professorServico;
    private final DisciplinaServico disciplinaServico;
    private final TurmaServico turmaServico;

    public HorarioControlador(HorarioServico horarioServico,
                               ProfessorServico professorServico,
                               DisciplinaServico disciplinaServico,
                               TurmaServico turmaServico) {
        this.horarioServico = horarioServico;
        this.professorServico = professorServico;
        this.disciplinaServico = disciplinaServico;
        this.turmaServico = turmaServico;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("horarios", horarioServico.listarVisualizacoes());
        return "horarios/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("horario", new Horario());
        adicionarListasDeApoio(model);
        return "horarios/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Horario horario = horarioServico.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Horário não encontrado"));
        model.addAttribute("horario", horario);
        adicionarListasDeApoio(model);
        return "horarios/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("horario") Horario horario, BindingResult result, Model model) {
        if (horario.getHoraInicio() != null && horario.getHoraFim() != null
                && !horario.getHoraFim().isAfter(horario.getHoraInicio())) {
            result.rejectValue("horaFim", "invalido", "O horário de término deve ser após o horário de início");
        }
        if (result.hasErrors()) {
            adicionarListasDeApoio(model);
            return "horarios/formulario";
        }
        horarioServico.salvar(horario);
        return "redirect:/horarios";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        horarioServico.excluir(id);
        return "redirect:/horarios";
    }

    private void adicionarListasDeApoio(Model model) {
        model.addAttribute("professores", professorServico.listarTodos());
        model.addAttribute("disciplinas", disciplinaServico.listarTodos());
        model.addAttribute("turmas", turmaServico.listarTodos());
        model.addAttribute("diasSemana", DiaSemana.values());
    }
}
