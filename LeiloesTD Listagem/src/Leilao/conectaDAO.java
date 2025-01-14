package Leilao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

// Conexão com o banco de dados
public class conectaDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/uc11?useSSL=true&verifyServerCertificate=false";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static Connection conectar() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão com o banco de dados estabelecida com sucesso.");
            return conn;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível estabelecer conexão com o banco de dados.");
            System.err.println("Erro ao conectar ao banco: " + e.getMessage() + ". Por favor, verifique o nome de usuário e a senha.");
            return null;
        }
    }
    
    public static void desconectar(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexão encerrada com sucesso.");
            } catch (SQLException e) {
                System.err.println("Erro ao encerrar conexão: " + e.getMessage());
            }
        }
    }
}
