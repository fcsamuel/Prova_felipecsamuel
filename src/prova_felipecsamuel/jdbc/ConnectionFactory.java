package prova_felipecsamuel.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Felipe
 */
public class ConnectionFactory {
    
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/prova_felipecsamuel",
                    "postgres","fcsroot");
        } catch (SQLException ex) {
            System.out.println("Erro ao criar uma "
                    + "conexão com o banco de dados "+
                    ex.getMessage());
        }
        return null;
    }
}
