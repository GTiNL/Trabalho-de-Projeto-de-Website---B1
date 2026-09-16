package com.escola.horarios.controlador;

import com.escola.horarios.modelo.Professor;
import com.escola.horarios.servico.HorarioServico;
import com.escola.horarios.servico.ProfessorServico;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/professores")
public class ProfessorControlador {

    private final ProfessorServico professorServico;
    private final HorarioServico horarioServico;

    public ProfessorControlador(ProfessorServico professorServico, HorarioServico horarioServico) {
        this.professorServico = professorServico;
        this.horarioServico = horarioServico;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("professores", professorServico.listarTodos());
        return "professores/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("professor", new Professor());
        return "professores/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Professor professor = professorServico.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));
        model.addAttribute("professor", professor);
        return "professores/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("professor") Professor professor, BindingResult result) {
        if (result.hasErrors()) {
            return "professores/formulario";
        }
        professorServico.salvar(professor);
        return "redirect:/professores";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        // Remove também os horários que dependiam deste professor.
        horarioServico.excluirPorProfessor(id);
        professorServico.excluir(id);
        return "redirect:/professores";
    }
}
