package com.escola.horarios.modelo;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class Horario {

    private Long id;

    @NotNull(message = "Selecione o professor")
    private Long professorId;

    @NotNull(message = "Selecione a disciplina")
    private Long disciplinaId;

    @NotNull(message = "Selecione a turma")
    private Long turmaId;

    @NotNull(message = "Selecione o dia da semana")
    private DiaSemana diaSemana;

    @NotNull(message = "Informe o horário de início")
    private LocalTime horaInicio;

    @NotNull(message = "Informe o horário de término")
    private LocalTime horaFim;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProfessorId() { return professorId; }
    public void setProfessorId(Long professorId) { this.professorId = professorId; }

    public Long getDisciplinaId() { return disciplinaId; }
    public void setDisciplinaId(Long disciplinaId) { this.disciplinaId = disciplinaId; }

    public Long getTurmaId() { return turmaId; }
    public void setTurmaId(Long turmaId) { this.turmaId = turmaId; }

    public DiaSemana getDiaSemana() { return diaSemana; }
    public void setDiaSemana(DiaSemana diaSemana) { this.diaSemana = diaSemana; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) { this.horaFim = horaFim; }
}
