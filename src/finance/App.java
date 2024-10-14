package finance;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection()) {
            Statement stmt = connection.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS transactions ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "montant DOUBLE, "
                    + "description VARCHAR(255), "
                    + "date DATE, "
                    + "categorie VARCHAR(255)"
                    + ")";
            stmt.execute(createTableSQL);
            System.out.println("Table 'transactions' créée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }
}
