package org.example.dao;

import org.example.Conexao;
import org.example.model.OrdemMateriaPrima;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdemMateriaPrimaDAO {
    public boolean buscarExistencia (int idOrdem,int idMateria){
        String query = "SELECT idOrdem FROM OrdemProducao WHERE idOrdem = ? AND idMateriaPrima = ? ";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, idOrdem);
            stmt.setInt(2, idMateria);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void inserirOrdemMateria (OrdemMateriaPrima ordemMateriaPrima){
        String query = "INSERT INTO OrdemMateriaPrima (idOrdem, idMateriaPrima, quantidade) VALUES (?,?,?)";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, ordemMateriaPrima.getIdOrdem());
            stmt.setInt(2, ordemMateriaPrima.getIdMateriaPrima());
            stmt.setDouble(3, ordemMateriaPrima.getQuantidade());

            stmt.executeUpdate();

            System.out.println("\nMatéria prima associado com a ordem de produção com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // vou usar esse loop para verificar materia associado a ordem por id
    public List<OrdemMateriaPrima> buscarOrdemMateriaPorId(int idOrdem) {

        String query = "SELECT idOrdem, idMateriaPrima, quantidade FROM OrdemMateriaPrima WHERE idOrdem = ?";

        List<OrdemMateriaPrima> ordemPecaList = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, idOrdem);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int idOrdemNew = rs.getInt("idOrdem");
                int idMateriaPrima = rs.getInt("idMateriaPrima");
                double quantidade = rs.getDouble("quantidade");

                OrdemMateriaPrima ordemPeca = new OrdemMateriaPrima(idOrdemNew, idMateriaPrima, quantidade);
                ordemPecaList.add(ordemPeca);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return ordemPecaList;
    }
}
