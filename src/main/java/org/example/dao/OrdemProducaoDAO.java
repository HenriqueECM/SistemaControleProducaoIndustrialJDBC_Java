package org.example.dao;

import org.example.Conexao;
import org.example.model.OrdemProducao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdemProducaoDAO {
    public void inserirOrdemProducao(OrdemProducao ordemProducao){
        String query = "INSERT INTO OrdemProducao (idProduto, idMaquina, quantidadeProduzir, dataSolicitacao, status) VALUES (?,?,?,?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, ordemProducao.getIdProduto());
            stmt.setInt(2, ordemProducao.getIdMaquina());
            stmt.setDouble(3, ordemProducao.getQuantidadeProduzir());
            stmt.setDate(4, Date.valueOf(ordemProducao.getDataSolicitacao()));
            stmt.setString(5, ordemProducao.getStatus());
            stmt.executeUpdate();

            System.out.println("\nOrdem de produção iniciado com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean buscarExistenciaProducao(OrdemProducao ordemProducao){
        String query = "SELECT idMaquina FROM OrdemProducao WHERE idMaquina = ? AND status = ?";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, ordemProducao.getIdMaquina());
            stmt.setString(2, ordemProducao.getStatus());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<OrdemProducao> listarOrdemPendente(){
        String query = "SELECT id, idProduto, idMaquina, quantidadeProduzir, dataSolicitacao, status FROM OrdemProducao WHERE status = 'PENDENTE'";

        List<OrdemProducao> ordemProducaoList = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                int idProduto = rs.getInt("idProduto");
                int idMaquina = rs.getInt("idMaquina");
                double quantidadeProduzir = rs.getDouble("quantidadeProduzir");
                Date dataSolicitacao = rs.getDate("dataSolicitacao");
                String status = rs.getString("status");
                OrdemProducao ordemProducao = new OrdemProducao(id, idProduto, idMaquina, quantidadeProduzir, dataSolicitacao.toLocalDate(), status);
                ordemProducaoList.add(ordemProducao);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return ordemProducaoList;
    }

    public List<OrdemProducao> listarOrdem(){
        String query = "SELECT id, idProduto, idMaquina, quantidadeProduzir, dataSolicitacao, status FROM OrdemProducao";

        List<OrdemProducao> ordemProducaoList = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                int idProduto = rs.getInt("idProduto");
                int idMaquina = rs.getInt("idMaquina");
                double quantidadeProduzir = rs.getDouble("quantidadeProduzir");
                Date dataSolicitacao = rs.getDate("dataSolicitacao");
                String status = rs.getString("status");
                OrdemProducao ordemProducao = new OrdemProducao(id, idProduto, idMaquina, quantidadeProduzir, dataSolicitacao.toLocalDate(), status);
                ordemProducaoList.add(ordemProducao);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return ordemProducaoList;
    }

    public void atualizarStatusConcluida (int id, String status){
        String query = "UPDATE OrdemProducao SET status = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, status);
            stmt.setInt(2, id);
            stmt.executeUpdate();

            System.out.println("\nOrdem de produção concluida com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}