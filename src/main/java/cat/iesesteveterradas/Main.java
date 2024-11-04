package cat.iesesteveterradas;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String DB_URL = "jdbc:sqlite:data/forhonor.db";
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                if (!databaseExists(conn)) {
                    initializeDatabase(conn);
                    insertDatabase(conn);
                }
                runProgram(conn);
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public static Path obtenirPathFitxer() {
        return Paths.get(System.getProperty("user.dir"), "data", "bones_practiques_programacio.txt");
    }

    public static List<String> readFileContent(Path filePath) throws IOException {
        return Files.readAllLines(filePath);
    }

    private static void runProgram(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n\nChoose an option:");
            System.out.println("1. Show a table");
            System.out.println("2. Show characters by faction");
            System.out.println("3. Show best attacker by faction");
            System.out.println("4. Show best defender by faction");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    showTable(conn, scanner);
                    break;
                case 2:
                    showCharactersByFaction(conn, scanner);
                    break;
                case 3:
                    showBestAttackerByFaction(conn, scanner);
                    break;
                case 4:
                    showBestDefenderByFaction(conn, scanner);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
        System.out.println("Application closed.");
    }

    static void initializeDatabase(Connection conn) {
        String createFaccioTable = "CREATE TABLE IF NOT EXISTS Faccio (" +
                "id INTEGER PRIMARY KEY," +
                "nom VARCHAR(15) NOT NULL," +
                "resum VARCHAR(500)" +
                ");";
        String createPersonatgeTable = "CREATE TABLE IF NOT EXISTS Personatge (" +
                "id INTEGER PRIMARY KEY," +
                "nom VARCHAR(15) NOT NULL," +
                "atac REAL," +
                "defensa REAL," +
                "idFaccio INTEGER," +
                "FOREIGN KEY (idFaccio) REFERENCES Faccio(id)" +
                ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createFaccioTable);
            stmt.execute(createPersonatgeTable);
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    static boolean databaseExists(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Faccio'");
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking database existence: " + e.getMessage());
            return false;
        }
    }

     static void insertDatabase(Connection conn) {
        String[] faccioInserts = {
                "INSERT INTO Faccio (nom, resum) VALUES ('Cavallers', 'Though seen as a single group, the Knights are hardly unified. There are many Legions in Ashfeld, the most prominent being The Iron Legion.');",
                "INSERT INTO Faccio (nom, resum) VALUES ('Vikings', 'The Vikings are a loose coalition of hundreds of clans and tribes, the most powerful being The Warborn.');",
                "INSERT INTO Faccio (nom, resum) VALUES ('Samurais', 'The Samurai are the most unified of the three factions, though this does not say much as the Daimyos were often battling each other for dominance.');"
        };

        String[] personatgeInserts = {
                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Warden', 1, 3, 1);",
                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Conqueror', 2, 2, 1);",
                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Peacekeep', 2, 3, 1);",
                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Raider', 3, 3, 2);",
                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Warlord', 2, 2, 2);",
                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Berserker', 1, 1, 2);",
                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Kensei', 3, 2, 3);",
                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Shugoki', 2, 1, 3);",
                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Orochi', 3, 2, 3);"
        };

        try (Statement stmt = conn.createStatement()) {
            for (String sql : faccioInserts) stmt.execute(sql);
            for (String sql : personatgeInserts) stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error in insert data in a database: " + e.getMessage());
        }
    }

    static void showTable(Connection conn, Scanner scanner) {
        System.out.println("Which table would you like to view? (Faccio/Personatge)");
        String table = scanner.nextLine();

        String sql = "SELECT * FROM " + table;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(metaData.getColumnName(i) + ": " + rs.getObject(i) + " ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error showing table: " + e.getMessage());
        }
    }

    static void showCharactersByFaction(Connection conn, Scanner scanner) {
        System.out.println("Enter faction name:");
        String factionName = scanner.nextLine();

        String sql = "SELECT Personatge.nom FROM Personatge JOIN Faccio ON Personatge.idFaccio = Faccio.id WHERE Faccio.nom = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, factionName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("Character: " + rs.getString("nom"));
            }
        } catch (SQLException e) {
            System.out.println("Error showing characters by faction: " + e.getMessage());
        }
    }

    static void showBestAttackerByFaction(Connection conn, Scanner scanner) {
        System.out.println("Enter faction name:");
        String factionName = scanner.nextLine();

        String sql = "SELECT Personatge.nom, MAX(Personatge.atac) FROM Personatge JOIN Faccio ON Personatge.idFaccio = Faccio.id WHERE Faccio.nom = ? ORDER BY atac DESC LIMIT 1";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, factionName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Best attacker: " + rs.getString("nom") + " with attack: " + rs.getFloat("MAX(Personatge.atac)"));
            }
        } catch (SQLException e) {
            System.out.println("Error showing best attacker by faction: " + e.getMessage());
        }
    }

    static void showBestDefenderByFaction(Connection conn, Scanner scanner) {
        System.out.println("Enter faction name:");
        String factionName = scanner.nextLine();

        String sql = "SELECT Personatge.nom, MAX(Personatge.defensa) FROM Personatge JOIN Faccio ON Personatge.idFaccio = Faccio.id WHERE Faccio.nom = ? ORDER BY defensa DESC LIMIT 1";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, factionName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Best defender: " + rs.getString("nom") + " with defense: " + rs.getFloat("MAX(Personatge.defensa)"));
            }
        } catch (SQLException e) {
            System.out.println("Error showing best defender by faction: " + e.getMessage());
        }
    }
}
