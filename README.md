# Question and Answer System

This Java project implements a test creation system with a database of questions and answers using SQL in pgAdmin4. The system manages subjects, questions, answers, tests, and lecturers, allowing for the creation and management of educational tests.

## Database Structure

The system uses the following SQL tables:

1. **Subject** (idS PK, nameS)
2. **Answers** (idA PK, nameA, idS FK)
3. **Questions** (idQ PK, nameQ, idD FK, idS FK, idType FK)
4. **Test** (idT PK, nameT, date, idS FK, idL FK)
5. **Lecturers** (idL PK, nameL, email, phoneNum)
6. **Difficulty Levels** (idD PK, levelName)
7. **Type** (idType PK, nameType)
8. **Questions_Answers** (idQ PK FK, idA PK FK, isCorrect)
9. **Subject_Lecturers** (idS PK FK, idL PK FK)
10. **Test_Questions** (idT PK FK, idQ PK FK)

*Note: PK stands for Primary Key, FK stands for Foreign Key*

## Class Descriptions

### Subject
- Describes a subject
- Attributes: id, name
- Linked to Questions and Answers classes

### Answers
- Describes an answer
- Attributes: id, name, subject

### Questions
- Describes a question
- Attributes: id, name, level of difficulty, subject, type
- Linked to Answers class

### Test
- Describes a test in a specific subject
- Attributes: id, name, date, subject, lecturer
- Linked to Subject, Lecturers, and Questions classes

### Lecturers
- Describes lecturers
- Attributes: id, name, email, phone number
- Linked to Subject class

### Type
- Describes the type of question
- Attributes: id, name

### Difficulty Levels
- Describes the difficulty level of a question
- Attributes: id, level name

## Features

The system provides the following functionalities:

1. Print the Data Base for a specific subject
2. Add a new answer to a specific subject
3. Add an answer to an existing question
4. Add a new question to a specific subject
5. Delete an answer from a question
6. Delete a question from a specific subject
7. Add a lecturer
8. Update lecturer information
9. Print all lecturers
10. Create or update a test for a specific subject
11. Print all test information
12. Print a specific test
13. Export a test to a text file
0. Exit and close connection

## Getting Started

1. Clone the repository
2. Set up pgAdmin4 and create the necessary tables
3. Configure the database connection in the Java project
4. Run the application
5. Use the menu options to interact with the system

## Dependencies

- Java (version X.X)
- pgAdmin4
- PostgreSQL JDBC driver
