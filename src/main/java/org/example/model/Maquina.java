package org.example.model;

public class Maquina {
    private int id, idSetor;
    private String nome, status;

    public Maquina(int idSetor, String nome, String status){
        this.nome = nome;
        this.status = status;
        this.idSetor = idSetor;
    }

    public Maquina(int id, int idSetor, String nome, String status){
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.idSetor = idSetor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSetor() {
        return idSetor;
    }

    public void setIdSetor(int idSetor) {
        this.idSetor = idSetor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
