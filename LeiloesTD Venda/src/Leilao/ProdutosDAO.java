package Leilao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {
    private Connection conn;

    // Construtor
    public ProdutosDAO() {
        try {
            this.conn = conectaDAO.getConnection();
        } catch (SQLException e) {
            // Mensagem de erro se não conseguir conectar
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    // Recuperação dos dados
    public ArrayList<ProdutosDTO> getProdutos() {
        String sql = "SELECT * FROM produtos";
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProdutosDTO produtos = new ProdutosDTO(); 
                produtos.setId(rs.getInt("id")); // ID
                produtos.setNome(rs.getString("nome")); // ID
                produtos.setValor(rs.getInt("valor")); // Rua
                produtos.setStatus(rs.getString("status")); // Data
                
                lista.add(produtos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
     // Salvando os dados no banco de dados
    public int salvar(ProdutosDTO produtos) {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        Connection conn = conectaDAO.conectar();

        if (conn == null) {
            System.out.println("Erro: Conexão com o banco falhou.");
            return -1;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produtos.getNome()); // Salvando a quadra
            stmt.setInt(2, produtos.getValor()); // Salvando a rua
            stmt.setString(3, produtos.getStatus()); // Salvando a data

            stmt.executeUpdate();
            return 1;
        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate entry")) {
                return 1062; 
            }
            System.err.println("Erro ao salvar dados: " + e.getMessage());
            return -1;
        } finally {
            conectaDAO.desconectar(conn);
        }
    }

    public boolean venderProduto(int produtoId) {
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
        try (Connection conn = conectaDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, produtoId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status para 'Vendido': " + e.getMessage());
            return false;
        }
    }
}