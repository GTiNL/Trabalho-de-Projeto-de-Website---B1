package com.escola.horarios.controlador;

import com.escola.horarios.modelo.Turma;
import com.escola.horarios.servico.HorarioServico;
import com.escola.horarios.servico.TurmaServico;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/turmas")
public class TurmaControlador {

    private final TurmaServico turmaServico;
    private final HorarioServico horarioServico;

    public TurmaControlador(TurmaServico turmaServico, HorarioServico horarioServico) {
        this.turmaServico = turmaServico;
        this.horarioServico = horarioServico;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("turmas", turmaServico.listarTodos());
        return "turmas/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("turma", new Turma());
        return "turmas/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Turma turma = turmaServico.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));
        model.addAttribute("turma", turma);
        return "turmas/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("turma") Turma turma, BindingResult result) {
        if (result.hasErrors()) {
            return "turmas/formulario";
        }
        turmaServico.salvar(turma);
        return "redirect:/turmas";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        // Remove também os horários que dependiam desta turma.
        horarioServico.excluirPorTurma(id);
        turmaServico.excluir(id);
        return "redirect:/turmas";
    }
}
