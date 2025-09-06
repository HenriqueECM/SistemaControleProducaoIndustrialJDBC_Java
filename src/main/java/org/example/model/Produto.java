package org.example.model;

public class Produto {
    private int id;
    private String nome, categoria;

    public Produto(String nome, String categoria){
        this.categoria = categoria;
        this.nome = nome;
    }

    public Produto(int id, String nome, String categoria){
        this.nome = nome;
        this.id = id;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
