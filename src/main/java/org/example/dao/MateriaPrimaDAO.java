package org.example.dao;

import org.example.Conexao;
import org.example.model.MateriaPrima;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MateriaPrimaDAO {
    public void inserirMateriaPrima(MateriaPrima materiaPrima){
        String query = "INSERT INTO MateriaPrima (nome, estoque) VALUES(?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, materiaPrima.getNome());
            stmt.setDouble(2, materiaPrima.getEstoque());
            stmt.executeUpdate();

            System.out.println("Matéria-Prima cadastrado com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean buscarExistenciaMateriaPrima (MateriaPrima materiaPrima){
        String query = "SELECT nome FROM MateriaPrima WHERE nome = ?";

        try (Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, materiaPrima.getNome());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<MateriaPrima> listarMateriaPrima (){
        String query = "SELECT id, nome, estoque FROM MateriaPrima WHERE estoque > 0";

        List<MateriaPrima> materiaPrimaList = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double estoque = rs.getDouble("estoque");
                MateriaPrima materiaPrima = new MateriaPrima(id, nome, estoque);
                materiaPrimaList.add(materiaPrima);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return materiaPrimaList;
    }

    public Double verificarEstoqueMateriaPorId(int idMateria){
        String query = "SELECT estoque FROM MateriaPrima WHERE id = ?";

        double quantidade = 0;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, idMateria);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                quantidade = rs.getDouble("estoque");

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return quantidade;
    }

    public void atualizarEstoque (int id, double estoque){
        String query = "UPDATE MateriaPrima SET estoque = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setDouble(1, estoque);
            stmt.setInt(2, id);
            stmt.executeUpdate();

            System.out.println("\nestoque atualizado com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
