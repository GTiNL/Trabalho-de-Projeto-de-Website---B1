package com.escola.horarios.modelo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class Disciplina {

    private Long id;

    @NotBlank(message = "O nome da disciplina é obrigatório")
    private String nome;

    @Positive(message = "A carga horária deve ser maior que zero")
    private Integer cargaHoraria;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(Integer cargaHoraria) { this.cargaHoraria = cargaHoraria; }
}
