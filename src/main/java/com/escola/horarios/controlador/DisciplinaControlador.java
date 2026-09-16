package com.escola.horarios.controlador;

import com.escola.horarios.modelo.Disciplina;
import com.escola.horarios.servico.DisciplinaServico;
import com.escola.horarios.servico.HorarioServico;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/disciplinas")
public class DisciplinaControlador {

    private final DisciplinaServico disciplinaServico;
    private final HorarioServico horarioServico;

    public DisciplinaControlador(DisciplinaServico disciplinaServico, HorarioServico horarioServico) {
        this.disciplinaServico = disciplinaServico;
        this.horarioServico = horarioServico;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("disciplinas", disciplinaServico.listarTodos());
        return "disciplinas/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        return "disciplinas/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Disciplina disciplina = disciplinaServico.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada"));
        model.addAttribute("disciplina", disciplina);
        return "disciplinas/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("disciplina") Disciplina disciplina, BindingResult result) {
        if (result.hasErrors()) {
            return "disciplinas/formulario";
        }
        disciplinaServico.salvar(disciplina);
        return "redirect:/disciplinas";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        // Remove também os horários que dependiam desta disciplina.
        horarioServico.excluirPorDisciplina(id);
        disciplinaServico.excluir(id);
        return "redirect:/disciplinas";
    }
}
