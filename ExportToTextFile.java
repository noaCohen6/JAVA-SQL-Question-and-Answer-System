package Question_Answer_System;

import java.io.*;
import java.sql.*;

public class ExportToTextFile {

	public static void exportFile(Connection con, int idT, int idS, PrintWriter pw) {
		String queryQ = "SELECT Questions.idQ ,Questions.nameQ,DifficultyLevels.levelName,Answers.idA, Answers.nameA, Questions_Answers.isCorrect "
				+ "FROM Questions " + "LEFT JOIN Questions_Answers ON Questions_Answers.idQ = Questions.idQ "
				+ "LEFT JOIN Answers ON Answers.idA = Questions_Answers.idA "
				+ "JOIN DifficultyLevels ON Questions.idD = DifficultyLevels.idD "
				+ "JOIN Test_Questions ON Questions.idQ = Test_Questions.idQ " + "WHERE Test_Questions.idT = " + idT;
		String query = "SELECT Test.idT, Test.nameT, Test.date, Test.idS, Subject.nameS, Test.idL " + "FROM Test "
				+ "JOIN Subject ON Test.idS = Subject.idS" + " WHERE Test.idS = " + idS + " AND Test.idT = " + idT;
		try (PreparedStatement pstmt = con.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery())
				 {

			pw.printf("%-10s %-20s %-30s %-10s %-20s %-10s%n", "idT", "nameT", "date", "idS", "nameS", "idL");
			pw.printf(
					"-----------------------------------------------------------------------------------------------\n\n");

			while (rs.next()) {
				String nameT = rs.getString("nameT");
				String date = rs.getString("date");
				String nameS = rs.getString("nameS");
				int idL = rs.getInt("idL");
				pw.printf("%-10d %-20s %-20s %-10d %-20s %-10d%n\n", idT, nameT, date, idS, nameS, idL);
			}
			printQuestionsWithAnswersToFile(con,queryQ,pw);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printQuestionsWithAnswersToFile(Connection con, String query, PrintWriter pw) {
		String[][] QA = DBHelper.allQuestionConnected(con, query);
		if (QA.length == 0) {
			System.out.println("No questions found.");
			return;
		}
		String sameQ = QA[0][0];
		boolean ifpQ = false;

		for (int i = 0; i < QA.length; i++) {
			if (QA[i][4] == null) {
				pw.printf(QA[i][0] + ". " + QA[i][1] + " (" + QA[i][2] + ")\n");
			} else if (QA[i][0].equals(sameQ) && ifpQ == true) {
				pw.printf("\t" + QA[i][3] + ".\t" + QA[i][4] + " (" + QA[i][5] + ")\n");
			} else if (QA[i][0].equals(sameQ) && ifpQ == false) {
				pw.printf(QA[i][0] + ". " + QA[i][1] + " (" + QA[i][2] + ")\n");
				pw.printf("\t" + QA[i][3] + ".\t" + QA[i][4] + " (" + QA[i][5] + ")\n");
				ifpQ = true;
			} else {
				sameQ = QA[i][0];
				ifpQ = false;
				i--;
			}
		}

	}
}
