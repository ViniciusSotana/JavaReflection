package br.edu.fema.model;

import br.edu.fema.reflection.annotations.C;
import br.edu.fema.reflection.annotations.D;
import br.edu.fema.reflection.annotations.R;
import br.edu.fema.reflection.annotations.U;

import java.util.UUID;

@C @R @U @D
public class Aluno {

    private UUID id;
    private String nome;
    private String matricula;

    public Aluno(UUID id, String nome, String matricula) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
