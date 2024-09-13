package Question_Answer_System;
import java.sql.*;


public class Type {
    
    public static void printAllType(Connection con) {
        String sql = "SELECT idType, nameType FROM Type";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            // Print the table header
            System.out.printf("%-10s %-30s%n", "idType", "nameType");
            System.out.println("-----------------------------------------");

            // Print each row in the table
            while (rs.next()) {
                int idType = rs.getInt("idType");
                String nameType = rs.getString("nameType");
                System.out.printf("%-10d %-30s%n", idType, nameType);
            }
            System.out.println("\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
