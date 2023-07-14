/* W23 Project Example Database
   2023 Barcomb
 
 Each time this file is executed, it will reset the database to the original state defined below.
 
 */

DROP DATABASE IF EXISTS EWR;
CREATE DATABASE EWR; 
USE EWR;

DROP TABLE IF EXISTS ANIMALS;
CREATE TABLE ANIMALS (
	AnimalID		int not null AUTO_INCREMENT,
        AnimalNickname		varchar(25),
	AnimalSpecies		varchar(25),
	primary key (AnimalID)
);

INSERT INTO ANIMALS (AnimalID, AnimalNickname, AnimalSpecies) VALUES
(1, 'Loner', 'coyote'),
(2, 'Biter', 'coyote'),
(3, 'Bitter', 'coyote'),
(4, 'Pencil', 'coyote'),
(5, 'Eraser', 'coyote'),
(6, 'Spot', 'coyote');


DROP TABLE IF EXISTS TASKS;
CREATE TABLE TASKS (
	TaskID			int not null AUTO_INCREMENT,
	Description		varchar(50),
        Duration                int,
        MaxWindow               int,
	primary key (TaskID)
);

INSERT INTO TASKS (TaskID, Description, Duration, MaxWindow) VALUES
(1, 'Rebandage leg wound', 20, 1),
(2, 'Apply burn ointment back', 5, 1);

DROP TABLE IF EXISTS TREATMENTS;
CREATE TABLE TREATMENTS (
      	TreatmentID	int not null AUTO_INCREMENT,
	AnimalID	int not null,
	TaskID		int not null,
	StartHour	int not null,
	primary key (TreatmentID)
);

INSERT INTO TREATMENTS (AnimalID, TaskID, StartHour) VALUES
(1, 1, 4),
(2, 1, 4),
(3, 1, 4),
(4, 1, 4),
(5, 1, 4),
(6, 1, 4),
(6, 2, 4),
(1, 1, 18),
(2, 1, 18),
(3, 1, 18),
(4, 1, 18),
(5, 1, 18),
(6, 1, 18),
(6, 2, 18);

