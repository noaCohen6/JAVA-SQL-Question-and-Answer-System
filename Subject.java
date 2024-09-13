package Question_Answer_System;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Subject {
	private Connection con;

    public Subject(DBconnection dbConnection) throws SQLException {
        this.con = DBconnection.getConnection();
    }

    // Add a new subject
    public static int addSubject(Connection con,String nameS) {
    	List<String> sub = getAllSubjectsString(con);
    	for (int i = 0; i < sub.size(); i++) {
			if (sub.get(i) == nameS) {
				return -1;
			}
		}
        String query = "INSERT INTO Subject (idS,nameS) VALUES (?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
        	int maxId = 0;
			try {
				maxId = DBHelper.getMaxId(con,"Subject","idS");
				pstmt.setInt(1, ++maxId);
	            pstmt.setString(2, nameS);
	            pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    // Get all subjects
    public static List<String> getAllSubjectsString(Connection con) {
        List<String> subjects = new ArrayList<>();
        String query = "SELECT nameS FROM Subject";
        try (PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                subjects.add(rs.getString("nameS"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    // Get the ID of a subject by its name
    public int getSubjectId(int idS) {
        String query = "SELECT idS FROM Subject WHERE idS = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, idS);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("idS");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // Return -1 if the subject is not found
    }
    
    public static void printAllSubjects(Connection con) {
        String sql = "SELECT idS, nameS FROM Subject";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            // Print the table header
            System.out.printf("%-10s %-30s%n", "idS", "nameS");
            System.out.println("-----------------------------------------");

            // Print each row in the table
            while (rs.next()) {
                int idS = rs.getInt("idS");
                String nameS = rs.getString("nameS");
                System.out.printf("%-10d %-30s%n", idS, nameS);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
