package org.example.model;

public class OrdemMateriaPrima {
    private int idOrdem, idMateriaPrima;
    private double quantidade;

    public OrdemMateriaPrima (int idOrdem, int idMateriaPrima, double quantidade){
        this.idMateriaPrima = idMateriaPrima;
        this.idOrdem = idOrdem;
        this.quantidade = quantidade;
    }

    public int getIdOrdem() {
        return idOrdem;
    }

    public void setIdOrdem(int idOrdem) {
        this.idOrdem = idOrdem;
    }

    public int getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public void setIdMateriaPrima(int idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
}
