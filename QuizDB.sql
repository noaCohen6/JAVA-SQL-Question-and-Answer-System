CREATE TABLE Subject (
    idS INT PRIMARY KEY,
    nameS VARCHAR(100));

CREATE TABLE DifficultyLevels (
    idD INT PRIMARY KEY,
    levelName VARCHAR(50));

INSERT INTO DifficultyLevels (idD, levelName) VALUES
    (1, 'Easy'),
    (2, 'Medium'),
    (3, 'Hard');


CREATE TABLE Type (
    idType INT PRIMARY KEY,
    nameType VARCHAR(50));

INSERT INTO Type (idType, nameType) VALUES
    (1, 'mulQuestions'),
    (2, 'openQuestions');


CREATE TABLE Questions (
    idQ INT PRIMARY KEY,
    nameQ VARCHAR(255),
    idD INT,
    idS INT,
	idType INT,
    FOREIGN KEY (idS) REFERENCES Subject(idS),
    FOREIGN KEY (idD) REFERENCES DifficultyLevels(idD),
	FOREIGN KEY (idType) REFERENCES Type(idType)
);



CREATE TABLE Answers (
    idA INT PRIMARY KEY,
    nameA VARCHAR(255),
    idS INT,
    FOREIGN KEY (idS) REFERENCES Subject(idS));


CREATE TABLE Test (
    idT INT PRIMARY KEY,
    nameT VARCHAR(100),
    date DATE,
	idS INT,
	idL INT,
    FOREIGN KEY (idS) REFERENCES Subject(idS),
	FOREIGN KEY (idL) REFERENCES Lecturers(idL)
);

CREATE TABLE Lecturers (
    idL INT PRIMARY KEY,
    nameL VARCHAR(100),
    email VARCHAR(255),
    phoneNUm VARCHAR(20)
);

CREATE TABLE Questions_Answers (
    idQ INT,
    idA INT,
	isCorrect BOOLEAN,
	PRIMARY KEY(idQ,idA),
    FOREIGN KEY (idQ) REFERENCES Questions(idQ),
	FOREIGN KEY (idA) REFERENCES Answers(idA)
);



CREATE TABLE Subject_Lecturers (
    idS INT,
    idL INT,
	PRIMARY KEY(idS,idL),
    FOREIGN KEY (idS) REFERENCES Subject(idS),
    FOREIGN KEY (idL) REFERENCES Lecturers(idL)
);

CREATE TABLE Test_Questions (
    idT INT,
    idQ INT,
	PRIMARY KEY(idT,idQ),
    FOREIGN KEY (idT) REFERENCES Test(idT),
    FOREIGN KEY (idQ) REFERENCES Questions(idQ)
);

INSERT INTO Subject (idS, nameS) VALUES
    (1, 'Mathematics'),
    (2, 'Physics'),
    (3, 'History'),
    (4, 'Biology'),
    (5, 'Computer Science'),
    (6, 'Literature'),
    (7, 'Chemistry'),
    (8, 'Geography'),
    (9, 'Art'),
    (10, 'Music');


INSERT INTO Questions (idQ, nameQ, idD, idS,idType) VALUES
    (100, 'What is the square root of 25?', 2, 1,2), -- Medium
    (101, 'Who discovered gravity?', 3, 2,1), -- Hard
    (102, 'When was the Declaration of Independence signed?', 1, 3,1), -- Easy
    (103, 'What is the powerhouse of the cell?', 2, 4,2), -- Medium
    (104, 'What is the capital of France?', 1, 8,1), -- Easy
    (105, 'Who painted the Mona Lisa?', 3, 9,1), -- Hard
    (106, 'What is the atomic number of Oxygen?', 2, 7,2), -- Medium
    (107, 'Who wrote Hamlet?', 3, 6,1), -- Hard
    (108, 'What is the derivative of x^2?', 2, 1,2), -- Medium
    (109, 'Who composed the Moonlight Sonata?', 3, 10,2); -- Hard





INSERT INTO Answers (idA, nameA, idS) VALUES
    (1, '5', 1),
    (2, '4', 1),
    (3, 'Isaac Newton', 2),
    (4, 'Galileo Galilei', 2),
    (5, '1776', 3),
    (6, '1492', 3),
    (7, 'Mitochondria', 4),
    (8, 'Nucleus', 4),
    (9, 'Paris', 8),
    (10, 'London', 8),
    (11, 'Leonardo da Vinci', 9),
    (12, 'Michelangelo', 9),
    (13, '8', 7),
    (14, '16', 7),
    (15, 'Shakespeare', 6),
    (16, 'Poe', 6),
    (17, '2x', 1),
    (18, '3x', 1),
    (19, 'Beethoven', 10),
    (20, 'Mozart', 10);

INSERT INTO Lecturers (idL, nameL, email, phoneNUm) VALUES
    (100, 'Bar Smith', 'Bar.smith@example.com', '123-456-7890'),
    (101, 'Noa Johnson', 'Noa.johnson@example.com', '456-789-0123'),
    (102, 'Adir Brown', 'Adir.brown@example.com', '789-012-3456'),
    (103, 'Eden Davis', 'Eden.davis@example.com', '234-567-8901'),
    (104, 'Almog Wilson', 'Almog.wilson@example.com', '567-890-1234');



INSERT INTO Test (idT, nameT, date,idS,idL) VALUES
    (1, 'Midterm Exam', '2024-08-15',1,101),
    (2, 'Final Exam', '2024-12-20',7,101),
    (3, 'Quiz 1', '2024-09-30',1,103),
    (4, 'Quiz 2', '2024-11-15',3,104),
    (5, 'Term Paper', '2024-10-31',6,100);

INSERT INTO subject_lecturers (idS,idL) VALUES
	(1,101),
	(7,101),
	(1,103),
	(3,104),
	(6,100),
	(3,102);

INSERT INTO Questions_Answers (idQ, idA, isCorrect) VALUES
    (100, 1, TRUE),  -- Math, square root of 25, answer: 5
    (101, 3, TRUE),  -- Physics, discovered gravity, answer: Isaac Newton
	(101,4, FALSE), -- Physics, discovered gravity, answer: Galileo
    (102, 5, TRUE),  -- History, Declaration of Independence, answer: 1776
	(102, 6, FALSE),  -- History, Declaration of Independence, answer: 1492
    (103, 7, TRUE),  -- Biology, powerhouse of cell, answer: Mitochondria
    (104, 9, TRUE),  -- Geography, capital of France, answer: Paris
  	(104, 10, FALSE),  -- Geography, capital of France, answer: london
    (105, 11, TRUE), -- Art, Mona Lisa, answer: Leonardo da Vinci
    (105, 12, FALSE), -- Art, Mona Lisa, answer: michelangelo
    (106, 13, TRUE), -- Chemistry, atomic number of Oxygen, answer: 8
    (107, 15, TRUE), -- Literature, wrote Hamlet, answer: Shakespeare
	(107, 16, FALSE), -- Literature, wrote Hamlet, answer: poe
    (108, 17, TRUE), -- Math, derivative of x^2, answer: 2x
    (109, 19, TRUE);-- Music, Moonlight Sonata, answer: Beethoven

INSERT INTO Test_Questions(idT,idQ) VALUES
	(1,100),
	(1,108);

