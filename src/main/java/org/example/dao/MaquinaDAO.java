package org.example.dao;

import org.example.Conexao;
import org.example.model.Maquina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaquinaDAO {
    public void inserirMaquina (Maquina maquina){
        String query = "INSERT INTO Maquina (nome, idSetor, status) VALUES (?,?,?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, maquina.getNome());
            stmt.setInt(2, maquina.getIdSetor());
            stmt.setString(3, maquina.getStatus());
            stmt.executeUpdate();

            System.out.println("\nMáquina cadastrado com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean buscarExistencia (Maquina maquina){
        String query = "SELECT nome FROM Maquina WHERE nome = ? AND idSetor = ?";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, maquina.getNome());
            stmt.setInt(2, maquina.getIdSetor());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Maquina> listarMaquinaOperacional(){
        List<Maquina> maquinaList = new ArrayList<>();

        String query = "SELECT id, nome, idSetor, status FROM Maquina WHERE status = 'OPERACIONAL'";
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int idSetor = rs.getInt("idSetor");
                String status = rs.getString("status");
                Maquina maquina = new Maquina(id, idSetor, nome, status);
                maquinaList.add(maquina);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return maquinaList;
    }

    public void atualizarStatusEmProducao(int id){
        String query = "UPDATE Maquina SET status = 'EM_PRODUCAO' WHERE id = ?";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("\nMáquina ID: " + id + " está em produção." );
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
