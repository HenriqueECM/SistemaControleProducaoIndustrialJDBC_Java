package org.example.dao;

import org.example.Conexao;
import org.example.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    public void inserirProduto(Produto produto){
        String query = "INSERT INTO Produto (nome, categoria) VALUES(?,?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getCategoria());
            stmt.executeUpdate();

            System.out.println("\nProduto cadastrado com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean buscarExistenciaProduto(Produto produto){
        String query = "SELECT nome FROM Produto WHERE nome = ? AND categoria = ?";

        try (Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getCategoria());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Produto> listarProdutos(){
        List<Produto> produtoList = new ArrayList<>();

        String query = "SELECT id, nome, categoria FROM Produto";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String nome =rs.getString("nome");
                String categoria = rs.getString("categoria");

                Produto produto = new Produto(id, nome, categoria);
                produtoList.add(produto);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return produtoList;
    }
}