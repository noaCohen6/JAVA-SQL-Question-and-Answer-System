package Question_Answer_System;

import java.sql.*;

public class Lecturers {

	public static void addLecturer(Connection con, String nameL, String email, String phoneNum) {
		String query = "INSERT INTO Lecturers (idL,nameL, email,phoneNUm) VALUES (?,?,?, ?)";
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			int maxId = 0;
			maxId = DBHelper.getMaxId(con, "Lecturers", "idL");
			pstmt.setInt(1, ++maxId);
			pstmt.setString(2, nameL);
			pstmt.setString(3, email);
			pstmt.setString(4, phoneNum);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateLecturerName(Connection con, int idL, String newNameL) {
		String query = "UPDATE Lecturers SET nameL = ? WHERE idL = ?";

		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, newNameL);
			pstmt.setInt(2, idL);
			pstmt.executeUpdate();

		} catch (

		SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateLecturerEmail(Connection con, int idL, String newEmail) {
		String query = "UPDATE Lecturers SET email = ? WHERE idL = ?";

		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, newEmail);
			pstmt.setInt(2, idL);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateLecturerPhoneNum(Connection con, int idL, String newPhoneNum) {
		String query = "UPDATE Lecturers SET phoneNUm = ? WHERE idL = ?";

		try (PreparedStatement pstmt = con.prepareStatement(query)) {	
			pstmt.setString(1, newPhoneNum);
			pstmt.setInt(2, idL);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void connectLecturerToSubcect(Connection con, int idS, int idL) {
		String query = "INSERT INTO subject_lecturers (idS, idL) VALUES (?, ?)";
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setInt(1, idS);
			pstmt.setInt(2, idL);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printAllLecturers(Connection con) {
		String sql = "SELECT Lecturers.idL, Lecturers.nameL, Lecturers.email, Lecturers.phoneNum,"
				+ "STRING_AGG(Subject.nameS, ', ' ORDER BY Subject.nameS ASC) AS subjects " + "FROM Lecturers "
				+ "LEFT JOIN Subject_Lecturers ON Lecturers.idL = Subject_Lecturers.idL "
				+ "LEFT JOIN Subject ON Subject_Lecturers.idS = Subject.idS "
				+ "GROUP BY Lecturers.idL, Lecturers.nameL, Lecturers.email, Lecturers.phoneNum";

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();

			// Print the table header
			System.out.printf("%-10s %-20s %-30s %-20s %-20s%n", "idL", "nameL", "email", "phoneNum", "subjects");
			System.out.println(
					"----------------------------------------------------------------------------------------------");

			// Print each row in the table
			while (rs.next()) {
				int idL = rs.getInt("idL");
				String nameL = rs.getString("nameL");
				String email = rs.getString("email");
				String phoneNum = rs.getString("phoneNum");
				String subjects = rs.getString("subjects");
				System.out.printf("%-10d %-20s %-30s %-20s %-20s%n", idL, nameL, email, phoneNum, subjects);
			}
			System.out.println("\n");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
