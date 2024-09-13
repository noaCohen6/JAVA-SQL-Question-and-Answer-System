package Question_Answer_System;

import java.sql.*;

public class Test {

	public static void addTest(Connection con, String nameT, String date, int idS, int idL) {
		String query = "INSERT INTO Test (idT,nameT, date,idS,idL) VALUES (?,?,?,?,?)";
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			int maxId = 0;
			maxId = DBHelper.getMaxId(con, "Test", "idT");
			pstmt.setInt(1, ++maxId);
			pstmt.setString(2, nameT);
			pstmt.setDate(3, java.sql.Date.valueOf(date));
			pstmt.setInt(4, idS);
			pstmt.setInt(5, idL);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addQuestionForTest(Connection con,int idT, int idQ) {
		String query = "INSERT INTO Test_Questions (idT, idQ) VALUES (?, ?)";
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setInt(1, idT);
			pstmt.setInt(2, idQ);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void deleteQuestionFormTest(Connection con,int idT, int idQ) {
		String query = "DELETE FROM Test_Questions WHERE idT = ? AND idQ = ?";
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setInt(1, idT);
			pstmt.setInt(2, idQ);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getTestWithQuestions(Connection con, int idT, int idS) {
		String queryQ = "SELECT Questions.idQ ,Questions.nameQ,DifficultyLevels.levelName,Answers.idA, Answers.nameA, Questions_Answers.isCorrect "
				+ "FROM Questions " + "LEFT JOIN Questions_Answers ON Questions_Answers.idQ = Questions.idQ "
				+ "LEFT JOIN Answers ON Answers.idA = Questions_Answers.idA "
				+ "JOIN DifficultyLevels ON Questions.idD = DifficultyLevels.idD "
				+ "JOIN Test_Questions ON Questions.idQ = Test_Questions.idQ " + "WHERE Test_Questions.idT = " + idT;
		String query = "SELECT Test.idT, Test.nameT, Test.date, Test.idS, Subject.nameS, Test.idL " + "FROM Test "
				+ "JOIN Subject ON Test.idS = Subject.idS" + " WHERE Test.idS = " + idS + " AND Test.idT = " + idT;
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			System.out.printf("%-10s %-20s %-20s %-10s %-20s %-10s%n", "idT", "nameT", "date", "idS", "nameS", "idL");
			System.out.println("---------------------------------------------------------------------------------------------------");

			while (rs.next()) {
				String nameT = rs.getString("nameT");
				String date = rs.getString("date");
				String nameS = rs.getString("nameS");
				int idL = rs.getInt("idL");
				System.out.printf("%-10d %-20s %-20s %-10d %-20s %-10d%n\n", idT, nameT, date, idS, nameS, idL);
			}
			Questions.printQuestionsWithAnswers(con, queryQ);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void printAllTest(Connection con, int idS) {
		String sql = "SELECT Test.idT, Test.nameT, Test.date, Test.idS, Subject.nameS, Test.idL " + "FROM Test "
				+ "JOIN Subject ON Test.idS = Subject.idS" + " WHERE Test.idS = " + idS;

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();

			// Print the table header
			System.out.printf("%-10s %-20s %-20s %-10s %-20s %-10s%n", "idT", "nameT", "date", "idS", "nameS", "idL");
			System.out.println(
					"------------------------------------------------------------------------------------------");

			// Print each row in the table
			while (rs.next()) {
				int idT = rs.getInt("idT");
				String nameT = rs.getString("nameT");
				String date = rs.getString("date");
				String nameS = rs.getString("nameS");
				int idL = rs.getInt("idL");
				System.out.printf("%-10d %-20s %-20s %-10d %-20s %-10d%n", idT, nameT, date, idS, nameS, idL);
			}
			System.out.println("\n");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
