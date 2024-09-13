package Question_Answer_System;

import java.sql.*;

public class Questions {
    
    public static void addQuestion(Connection con,String nameQ, int idD, int idS, int idType) {
        String query = "INSERT INTO Questions (idQ,nameQ, idD, idS, idType) VALUES (?,?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
        	int maxId = 0;
        	maxId = DBHelper.getMaxId(con,"Questions","idQ");
        	pstmt.setInt(1, ++maxId);
        	pstmt.setString(2, nameQ);
            pstmt.setInt(3, idD);
            pstmt.setInt(4, idS);
            pstmt.setInt(5, idType);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void deleteQuestion(Connection con,int idQ) {
        String query = "DELETE FROM Questions_Answers WHERE idQ = " + idQ;
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
        	 pstmt.executeUpdate();
            String queryQ = "DELETE FROM Questions WHERE idQ = "+ idQ;
            try (PreparedStatement pstmtQ = con.prepareStatement(queryQ)) {
            	 pstmtQ.executeUpdate(); 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void addAnswerForQuestion(Connection con,int idQ, int idA, boolean isCorrect) {
        String query = "INSERT INTO Questions_Answers (idQ, idA, isCorrect) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, idQ);
            pstmt.setInt(2, idA);
            pstmt.setBoolean(3, isCorrect);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void deleteAnswerFromQuestion(Connection con,int idQ, int idA) {
        String query = "DELETE FROM Questions_Answers WHERE idQ = ? AND idA = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, idQ);
            pstmt.setInt(2, idA);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void getQuestionsWithAnswers(Connection con,int idS) {
        String query = "SELECT Questions.idQ ,Questions.nameQ,DifficultyLevels.levelName,Answers.idA, Answers.nameA, Questions_Answers.isCorrect "
        		+ "FROM Questions "
        		+ "LEFT JOIN Questions_Answers ON Questions_Answers.idQ = Questions.idQ "
        		+ "LEFT JOIN Answers ON Answers.idA = Questions_Answers.idA " 
        		+ "JOIN DifficultyLevels ON Questions.idD = DifficultyLevels.idD "
        		+ "WHERE Questions.idS = "+idS+" ORDER BY Questions.idQ";
        printQuestionsWithAnswers(con,query) ;
        
    }

    public static void printQuestionsWithAnswers(Connection con,String query) {
    	String[][] QA = DBHelper.allQuestionConnected(con,query);
    	if (QA.length == 0) {
            System.out.println("No questions found.");
            return;
        }
    	String sameQ = QA[0][0];
    	boolean ifpQ = false;
    	
    	for (int i = 0; i < QA.length; i++) {
    		if(QA[i][4] == null) {
    			System.out.println(QA[i][0] + ". " + QA[i][1] + " (" + QA[i][2] + ")");
    		}
    		else if(QA[i][0].equals(sameQ) && ifpQ == true) {
				System.out.println("\t" + QA[i][3] + ". " + QA[i][4]+ " (" + QA[i][5] + ")");
			}
    		else if(QA[i][0].equals(sameQ) && ifpQ == false) {
				System.out.println(QA[i][0] + ". " + QA[i][1] + " (" + QA[i][2] + ")");
				System.out.println("\t" + QA[i][3] + ". " + QA[i][4]+ " (" + QA[i][5] + ")");
				ifpQ = true;
			}
			else {
				sameQ = QA[i][0];
				ifpQ = false;
				i--;
			}
		}
    	System.out.println("\n");
    }

}
