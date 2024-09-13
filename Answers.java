package Question_Answer_System;
import java.sql.*;
public class Answers {
    
    public static void addAnswer(Connection con,String nameA, int idS) {
        String query = "INSERT INTO Answers (idA,nameA, idS) VALUES (?,?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
        	int maxId = 0;
        	maxId = DBHelper.getMaxId(con,"Answers","idA");
        	pstmt.setInt(1, ++maxId);
        	pstmt.setString(2, nameA);
            pstmt.setInt(3, idS);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void printAllAnswers(Connection con,int idS) {
        String sql = "SELECT idA, nameA,idS FROM Answers WHERE idS = "+idS;
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            // Print the table header
            System.out.println("\n");
            System.out.printf("%-10s %-20s %-10s%n", "idA", "nameA","idS");
            System.out.println("-----------------------------------------");

            // Print each row in the table
            while (rs.next()) {
                int idA = rs.getInt("idA");
                String nameA = rs.getString("nameA");
                int idSub = rs.getInt("idS");
                System.out.printf("%-10d %-20s %-10d%n", idA, nameA,idSub);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
