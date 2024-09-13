package Question_Answer_System;
import java.sql.*;

public class DBHelper {
	
	// Generic method to get the maximum ID from any table and column
    public static int getMaxId(Connection con, String tableName, String columnName) {
        int maxId = -1;
        String sql = "SELECT MAX(" + columnName + ") AS max_id FROM " + tableName;
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                maxId = rs.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return maxId;
    }
    public static int getRowCount(Connection con,String query) {
        int rowCount = 0;
        String countQuery = "SELECT COUNT(*) FROM (" + query + ") AS countTable";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(countQuery)) {

            if (rs.next()) {
                rowCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }
    
    public static String[][] allQuestionConnected(Connection con,String query){
    	int row = DBHelper.getRowCount(con,query);

        String[][] questionsWithAnswers = new String[row][6];

        try (PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
        	int rowIndex =0;
            while (rs.next()) {
            	int idQ = rs.getInt("idQ");
                String question = rs.getString("nameQ");
                String levelName = rs.getString("levelName");
                int idA = rs.getInt("idA");
                String answer = rs.getString("nameA");
                boolean isCorrect = rs.getBoolean("isCorrect");
                
                
                questionsWithAnswers[rowIndex][0] = String.valueOf(idQ);
                questionsWithAnswers[rowIndex][1] = question;
                questionsWithAnswers[rowIndex][2] = levelName;
                questionsWithAnswers[rowIndex][3] = String.valueOf(idA);
                questionsWithAnswers[rowIndex][4] = answer;
                questionsWithAnswers[rowIndex][5] = isCorrect ? "True" : "False";
                
                rowIndex++;
                
            }
            return questionsWithAnswers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return new String[0][0];
    }

}
