package Question_Answer_System;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

public class Program {
	private static Scanner s = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {
		boolean fContinueSubject = true;
		int idSubject = 0;
		try {
			Connection con = DBconnection.getConnection();

			if (con != null) {
				System.out.println("Connected to the database successfully!\n");

				do {
					Subject.printAllSubjects(con);
					System.out.println("\nChoose one of the following options:");
					System.out.println("1 - Choose an existing subject");
					System.out.println("2 - Create new subject");
					System.out.println("0 - Exit");

					System.out.println("Enter your choice ---> ");
					int choiceSubject = s.nextInt();

					switch (choiceSubject) {
					case 1:
						System.out.println("Enter the number of the subject ");
						idSubject = s.nextInt();
						fContinueSubject = false;
						break;
					case 2:
						System.out.println("Enter the name of the new subject ");
						Subject.addSubject(con, s.next());
						break;
					case 0:
						fContinueSubject = false;
						break;
					default:
						System.out.println("Wrong option");
					}
					System.out.println();

				} while (fContinueSubject);

				int choice;
				boolean fContinue = true;

				do {
					System.out.println("\nChoose one of the following options:");
					System.out.println("1 - Print the Data Base for specific subject");
					System.out.println("2 - Add a new answer to specific subject");
					System.out.println("3 - Add an answer to an existing question");
					System.out.println("4 - Add a new question to specific subject");
					System.out.println("5 - Delete an answer from a question");
					System.out.println("6 - Delet a question from specific subject");
					System.out.println("7 - Add lecturer");
					System.out.println("8 - Update lecturer");
					System.out.println("9 - Print all lecturers");
					System.out.println("10 - Creat or update test for specific subject");
					System.out.println("11 - Print all test info");
					System.out.println("12 - Print specific test");
					System.out.println("13 - Export test to text file");
					System.out.println("0 - Exit && Close connection");

					System.out.println("Enter your choice ---> ");
					choice = s.nextInt();
					System.out.println("\n");
					
					switch (choice) {
					case 1:
						printDataBase(con, idSubject);
						break;
					case 2:
						AddAnswerToSubject(con, idSubject);
						break;
					case 3:
						AddAnswerToExistingQuestion(con, idSubject);
						break;
					case 4:
						AddQuestionToSubject(con, idSubject);
						break;
					case 5:
						DeleteAnswerFromQuestion(con, idSubject);
						break;
					case 6:
						DeletQuestionFromSubject(con, idSubject);
						break;
					case 7:
						Addlecturer(con);
						break;
					case 8:
						UpdateLecturer(con);
						break;
					case 9:
						printLecturers(con);
						break;
					case 10:
						CreatUpdateTestForSubject(con, idSubject);
						break;
					case 11:
						PrintAllTestInfo(con,idSubject);
						break;
					case 12:
						PrintAllTestInfo(con,idSubject);
						PrintTestWithQuestions(con,idSubject);
						break;
					case 13:
						ExportTestToTextFile(con,idSubject);
					case 0:
						try {
				            con.close();
				            System.out.println("\nConnection closed.");
				        } catch (SQLException e) {
				            e.printStackTrace();
				        }
						fContinue = false;
						break;
					default:
						System.out.println("Wrong option");
					}
					System.out.println();

				} while (fContinue);

				System.out.println("bye!");

			} else {
				System.out.println("Failed to connect to the database.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	

	public static void printDataBase(Connection con, int idS) {
		Questions.getQuestionsWithAnswers(con, idS);
		Answers.printAllAnswers(con, idS);
	}
	public static void printLecturers(Connection con) {
		Lecturers.printAllLecturers(con);
	}
	public static void PrintAllTestInfo(Connection con,int idS) {
		Test.printAllTest(con,idS);
	}
	public static void PrintTestWithQuestions(Connection con,int idS) {
		System.out.println("Enter the number of the test that you want to print (Write the idT)");
		int idT = s.nextInt();
		System.out.println("\n");
		Test.getTestWithQuestions(con,idT,idS);
	}

	public static void AddAnswerToSubject(Connection con, int idS) {
		System.out.println("Write the new answer you want to add to the database");
		s.nextLine();
		String aName = s.nextLine();
		System.out.println("\n");
		Answers.addAnswer(con, aName, idS);
		System.out.println("The answer was added\n");
	}

	public static void AddAnswerToExistingQuestion(Connection con, int idS) {
		printDataBase(con, idS);
		System.out.println("Which question would you like to add an answer? (Write the idQ)");
		int idQ = s.nextInt();
		System.out.println("\n");
		String queryOQ = "SELECT * FROM Questions_Answers WHERE Questions_Answers.idQ= " + idQ;
		try (PreparedStatement pstmtOQ = con.prepareStatement(queryOQ)) {
			try (ResultSet rs = pstmtOQ.executeQuery()) {
				if (rs.next()) {
					String query = "SELECT * FROM Questions WHERE idQ = " + idQ + " AND idS= " + idS
							+ " AND idType = 1";

					try (PreparedStatement pstmt = con.prepareStatement(query)) {
						try (ResultSet rsQ = pstmt.executeQuery()) {
							if (!rsQ.next()) {
								System.out.println(
										"The question does not exist in this subject or it is an open question");
								return;
							}
						}
					}
				}
			}

			System.out.println("Write the answer you want to add to the question (Write the idA)");
			int idA = s.nextInt();
			System.out.println("\n");
			String queryA = "SELECT * FROM Answers WHERE idA = " + idA + " AND idS= " + idS;
			try (PreparedStatement pstmtA = con.prepareStatement(queryA)) {
				if (pstmtA == null) {
					System.out.println("The answer does not exist on this subject");
					return;
				}
				System.out.println("Write if the answer is true or false");
				boolean ifCorrect = s.nextBoolean();
				Questions.addAnswerForQuestion(con, idQ, idA, ifCorrect);
				System.out.println("The answer was added\n");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void AddQuestionToSubject(Connection con, int idS) {
		System.out.println("Write the new question you want to add to the database");
		s.nextLine();
		String qName = s.nextLine();
		System.out.println("\n");
		
		System.out.println("Select the difficulty level of the question (Write the idD");
		printDifficultyLevel(con);
		int idD = s.nextInt();
		System.out.println("\n");
		
		System.out.println("Select the type of the question (Write the idType");
		printType(con);
		int idType = s.nextInt();
		System.out.println("\n");

		Questions.addQuestion(con, qName, idD, idS, idType);
		System.out.println("The question was added\n");
	}

	public static void printDifficultyLevel(Connection con) {
		DifficultyLevels.printAllDifficultyLevels(con);
	}

	public static void printType(Connection con) {
		Type.printAllType(con);
	}

	public static void DeleteAnswerFromQuestion(Connection con, int idS) {
		Questions.getQuestionsWithAnswers(con, idS);
		System.out.println("Enter the Question number that you want to delete the Answer form (Write the idQ)");
		int idQ = s.nextInt();
		System.out.println("\n");
		String query = "SELECT * FROM Questions WHERE idQ = " + idQ + " AND idS= " + idS + " AND idType =1";
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next()) {
					System.out.println("The question does not exist in this subject or it is an open question and you can not delete the answer");
					return;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String queryQ = "SELECT Questions.idQ ,Questions.nameQ,DifficultyLevels.levelName,Answers.idA, Answers.nameA, Questions_Answers.isCorrect "
        		+ "FROM Questions "
        		+ "LEFT JOIN Questions_Answers ON Questions_Answers.idQ = Questions.idQ "
        		+ "LEFT JOIN Answers ON Answers.idA = Questions_Answers.idA " 
        		+ "JOIN DifficultyLevels ON Questions.idD = DifficultyLevels.idD "
        		+ "WHERE Questions.idS = "+idS+ " AND Questions.idQ = "+ idQ;
		Questions.printQuestionsWithAnswers(con, queryQ);
		

		System.out.println("Enter the answer number that you want to delete (Write the idA)");
		int idA = s.nextInt();
		System.out.println("\n");
		
		String queryA = "SELECT * FROM Questions_Answers WHERE idQ = " + idQ + " AND idA = " + idA;
		try (PreparedStatement pstmt = con.prepareStatement(queryA)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next()) {
					System.out.println("The answer is not connection to the question\n");
					return;
				}
				Questions.deleteAnswerFromQuestion(con,idQ,idA);
				System.out.println("The answer was deleted\n");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void DeletQuestionFromSubject(Connection con, int idSubject) {
		Questions.getQuestionsWithAnswers(con, idSubject);
		System.out.println("Enter the Question number that you want to delete  (Write the idQ)");
		int idQ = s.nextInt();
		System.out.println("\n");
		String query = "SELECT * FROM Questions WHERE idQ = " + idQ + " AND idS = " + idSubject;
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next()) {
					System.out.println("The question does not exist in this subject\n");
					return;
				}
				Questions.deleteQuestion(con,idQ);
				System.out.println("The question was deleted\n");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void Addlecturer(Connection con) {
		System.out.println("Write the lecturer's name (first and last name)");
		s.nextLine();
		String lName = s.nextLine();
		System.out.println("\n");
		System.out.println("Write the lecturer's email ");
		String email = s.next();
		System.out.println("\n");
		System.out.println("Write the lecturer's phone number ");
		String phoenNum = s.next();
		System.out.println("\n");
		Lecturers.addLecturer(con,lName,email,phoenNum);
		
		int idL = DBHelper.getMaxId(con,"Lecturers","idL");
		addSubjectToLecturer(con,idL);
		System.out.println("The lectuer was added\n");
	}
	
	public static void addSubjectToLecturer(Connection con,int idL) {
		Subject.printAllSubjects(con);
		System.out.println("Choose the subject that the lecturer teaches (Write the idS)");
		int idS = s.nextInt();
		System.out.println("\n");
		Lecturers.connectLecturerToSubcect(con,idS,idL);
		System.out.println("You added the subject to the lectuer\n");
	}
	
	public static void UpdateLecturer(Connection con) {
		int choice;
		boolean fContinue = true;

		do {
			System.out.println("\nChoose one of the following options:");
			System.out.println("1 - Update lecturer name");
			System.out.println("2 - Update Lecturer email");
			System.out.println("3 - Update Lecturer phone number");
			System.out.println("4 - Add a subject to the lecturer");
			System.out.println("Enter your choice ---> ");
			choice = s.nextInt();
			switch (choice) {
			case 1:
				UpdateLecturerName(con);
				fContinue = false;
				break;
			case 2:
				UpdateLecturerEmail(con);
				fContinue = false;
				break;
			case 3:
				UpdateLecturerPhoneNum(con);
				fContinue = false;
				break;
			case 4:
				AddSubjectToLecturer(con);
				fContinue = false;
				break;
			default:
				System.out.println("Wrong option");
			}
			System.out.println();

		} while (fContinue);
	}
	
	public static void UpdateLecturerName(Connection con) {
		printLecturers(con);
		System.out.println("Enter the number of the lecturer that you want to change his name (Write the idL)");
		int idL = s.nextInt();
		String query = "SELECT * FROM Lecturers WHERE idL = " + idL;
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next()) {
					System.out.println("The lecturers dose not exist\n");
					return;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n");
		System.out.println("Write the new lecturer's name (first and last name)");
		s.nextLine();
		String lName = s.nextLine();
		System.out.println("\n");
		Lecturers.updateLecturerName(con,idL,lName);
		System.out.println("You uptate the lectuer's name\n");
	}

	public static void UpdateLecturerEmail(Connection con) {
		printLecturers(con);
		System.out.println("Enter the number of the lecturer that you want to change his email (Write the idL)");
		int idL = s.nextInt();
		String query = "SELECT * FROM Lecturers WHERE idL = " + idL;
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next()) {
					System.out.println("The lecturers dose not exist\n");
					return;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n");
		System.out.println("Write the new lecturer's email");
		String email = s.next();
		System.out.println("\n");
		Lecturers.updateLecturerEmail(con,idL,email);
		System.out.println("You uptate the lectuer's email\n");
	}
	
	public static void UpdateLecturerPhoneNum(Connection con) {
		printLecturers(con);
		System.out.println("Enter the number of the lecturer that you want to change his phone number (Write the idL)");
		int idL = s.nextInt();
		String query = "SELECT * FROM Lecturers WHERE idL = " + idL;
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next()) {
					System.out.println("The lecturers dose not exist\n");
					return;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n");
		System.out.println("Write the new lecturer's phone number");
		String phoenNum = s.next();
		System.out.println("\n");
		Lecturers.updateLecturerPhoneNum(con,idL,phoenNum);
		System.out.println("You uptate the lectuer's phone number\n");
	}
	public static void AddSubjectToLecturer(Connection con) {
		printLecturers(con);
		System.out.println("Enter the number of the lecturer that you want to change his email (Write the idL)");
		int idL = s.nextInt();
		String query = "SELECT * FROM Lecturers WHERE idL = " + idL;
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next()) {
					System.out.println("The lecturers dose not exist\n");
					return;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n");
		addSubjectToLecturer(con,idL);
		
	}
	
	public static void CreatUpdateTestForSubject(Connection con, int idSubject) {
		int choice;
		boolean fContinue = true;

		do {
			System.out.println("\nChoose one of the following options:");
			System.out.println("1 - Creat a new test");
			System.out.println("2 - Update test");
			System.out.println("Enter your choice ---> ");
			choice = s.nextInt();
			switch (choice) {
			case 1:
				CreatNewTest(con,idSubject);
				fContinue = false;
				break;
			case 2:
				UpdateTest(con,idSubject);
				fContinue = false;
				break;
			
			default:
				System.out.println("Wrong option");
			}
			System.out.println();

		} while (fContinue);
	}
	
	public static void CreatNewTest(Connection con, int idSubject) {
		System.out.println("Write the test name");
		s.nextLine();
		String tName = s.nextLine();
		System.out.println("\n");
		System.out.println("Write the test date (yyyy-mm-dd)");
		String date = s.next();
		System.out.println("\n");
		printLecturers(con);
		System.out.println("Enter the number of the lecturer that creats the test (Write the idL)");
		int idL = s.nextInt();
		System.out.println("\n");
		String query = "SELECT * FROM subject_lecturers WHERE idS = " + idSubject + " AND idL = " + idL;
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next()) {
					System.out.println("The lecturers is not connected to this subject\n");
					return;
				}
				Test.addTest(con,tName,date,idSubject,idL);
				System.out.println("You have successfully created the test, now you can add questions to it\n");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void UpdateTest(Connection con, int idSubject) {
		PrintAllTestInfo(con, idSubject);
		
		System.out.println("Enter the number of the test that you want to update (Write the idT)");
		int idT = s.nextInt();
		System.out.println("\n");
		String query = "SELECT * FROM Test WHERE idT = " + idT +" AND idS = " + idSubject; 
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next()) {
					System.out.println("The test is not connected to this subject");
					return;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n");
		Test.getTestWithQuestions(con,idT,idSubject);
		int choice;
		boolean fContinue = true;

		do {
			System.out.println("\nChoose one of the following options:");
			System.out.println("1 - Add a new question to the test");
			System.out.println("2 - Delete a question from the test");
			System.out.println("Enter your choice ---> ");
			choice = s.nextInt();
			switch (choice) {
			case 1:
				 AddNewQuestionToTest(con,idSubject,idT);
				fContinue = false;
				break;
			case 2:
				DeledteQuestionToTest(con,idSubject,idT);
				fContinue = false;
				break;
			
			default:
				System.out.println("Wrong option");
			}
			System.out.println();

		} while (fContinue);
		
	}
	
	public static void AddNewQuestionToTest(Connection con, int idSubject, int idT) {
		Questions.getQuestionsWithAnswers(con, idSubject);
		System.out.println("Enter the question number that you want to add to the test (Write the idQ)");
		int idQ = s.nextInt();
		System.out.println("\n");
		Test.addQuestionForTest(con,idT,idQ);
		System.out.println("The question was added to the test\n");
	}
	
	public static void DeledteQuestionToTest(Connection con, int idSubject, int idT) {
		String query = "SELECT * FROM Test_Questions WHERE idT = " + idT;
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next()) {
					System.out.println("The test does not have questions\n");
					return;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Test.getTestWithQuestions(con,idT,idSubject);
		System.out.println("Enter the question number that you want to delete from the test (Write the idQ)");
		int idQ = s.nextInt();
		System.out.println("\n");
		Test.deleteQuestionFormTest(con,idT,idQ);
		System.out.println("The question was deledted from the test\n");
	}
	
	public static void ExportTestToTextFile(Connection con, int idSubject) throws FileNotFoundException {
		PrintAllTestInfo(con, idSubject);
		String testName = null;
		System.out.println("Enter the number of the test that you want to export (Write the idT)");
		int idT = s.nextInt();
		System.out.println("\n");
		String query = "SELECT nameT FROM Test WHERE idT = " + idT +" AND idS = " + idSubject; 
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				if (!rs.next()) {
					System.out.println("The test is not connected to this subject\n");
					return;
				}
				testName = rs.getString("nameT");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("This is the test you chose to export\n\n");
		Test.getTestWithQuestions(con,idT,idSubject);
		File f1 = new File(testName + ".txt");
		PrintWriter pw = new PrintWriter(f1);
		
		ExportToTextFile.exportFile(con, idT, idSubject, pw);
		System.out.println("The test was export to the file\n");
		pw.close();
		
	}

}
