package Question_Answer_System;
import java.sql.*;

public class DifficultyLevels {
    
    public static void printAllDifficultyLevels(Connection con) {
        String sql = "SELECT idD, levelName FROM DifficultyLevels";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            // Print the table header
            System.out.printf("%-10s %-30s%n", "idD", "levelName");
            System.out.println("-----------------------------------------");

            
            while (rs.next()) {
                int idD = rs.getInt("idD");
                String levelName = rs.getString("levelName");
                System.out.printf("%-10d %-30s%n", idD, levelName);
            }
            System.out.println("\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	
}
