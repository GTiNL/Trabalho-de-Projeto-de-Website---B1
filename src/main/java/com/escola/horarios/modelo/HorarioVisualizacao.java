package com.escola.horarios.modelo;

import java.time.LocalTime;

/**
 * Representação de um Horário já com os nomes resolvidos,
 * usada apenas para exibição nas telas (evita lookups no template).
 */
public class HorarioVisualizacao {

    private final Long id;
    private final String professorNome;
    private final String disciplinaNome;
    private final String turmaNome;
    private final DiaSemana diaSemana;
    private final LocalTime horaInicio;
    private final LocalTime horaFim;

    public HorarioVisualizacao(Long id, String professorNome, String disciplinaNome, String turmaNome,
                        DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFim) {
        this.id = id;
        this.professorNome = professorNome;
        this.disciplinaNome = disciplinaNome;
        this.turmaNome = turmaNome;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    public Long getId() { return id; }
    public String getProfessorNome() { return professorNome; }
    public String getDisciplinaNome() { return disciplinaNome; }
    public String getTurmaNome() { return turmaNome; }
    public DiaSemana getDiaSemana() { return diaSemana; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFim() { return horaFim; }
}
