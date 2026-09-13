package com.escola.horarios.modelo;

import jakarta.validation.constraints.NotBlank;

public class Turma {

    private Long id;

    @NotBlank(message = "O nome da turma é obrigatório")
    private String nome;

    @NotBlank(message = "O ano letivo é obrigatório")
    private String anoLetivo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getAnoLetivo() { return anoLetivo; }
    public void setAnoLetivo(String anoLetivo) { this.anoLetivo = anoLetivo; }
}
