package org.example.model;

import java.time.LocalDate;

public class OrdemProducao {
    private int id, idProduto, idMaquina;
    private LocalDate dataSolicitacao;
    private double quantidadeProduzir;
    private String status;

    public OrdemProducao(int id, int idProduto, int idMaquina, LocalDate dataSolicitacao, double quantidadeProduzir, String status){
        this.id = id;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
        this.idProduto = idProduto;
        this.idMaquina = idMaquina;
        this.quantidadeProduzir = quantidadeProduzir;
    }

    public OrdemProducao(int idProduto, int idMaquina, double quantidadeProduzir,LocalDate dataSolicitacao, String status){
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
        this.idProduto = idProduto;
        this.idMaquina = idMaquina;
        this.quantidadeProduzir = quantidadeProduzir;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public double getQuantidadeProduzir() {
        return quantidadeProduzir;
    }

    public void setQuantidadeProduzir(double quantidadeProduzir) {
        this.quantidadeProduzir = quantidadeProduzir;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
