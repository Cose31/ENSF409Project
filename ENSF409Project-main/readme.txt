Compilation and Running Instructions:

Navigate to the project folder in windows command prompt, and enter the following command for compilation:
javac -cp .;lib/mysql-connector-java-8.0.23.jar edu/ucalgary/oop/*.java

And then to run:
java -cp .;lib/mysql-connector-java-8.0.23.jar edu.ucalgary.oop.GUI  



Running Tests:
Copy all Test files to edu/ucalgary/oop within the project folder
To compile, run from the project folder:
javac -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;lib/mysql-connector-java-8.0.23.jar edu/ucalgary/oop/*.java  

To run tests, run each of these commands from the project folder:
java -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;lib/mysql-connector-java-8.0.23.jar org.junit.runner.JUnitCore  edu.ucalgary.oop.AnimalDatabaseTest
java -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;lib/mysql-connector-java-8.0.23.jar org.junit.runner.JUnitCore  edu.ucalgary.oop.AnimalTests
java -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;lib/mysql-connector-java-8.0.23.jar org.junit.runner.JUnitCore  edu.ucalgary.oop.FeedingTests
java -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;lib/mysql-connector-java-8.0.23.jar org.junit.runner.JUnitCore  edu.ucalgary.oop.ScheduleFormatterTests
java -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;lib/mysql-connector-java-8.0.23.jar org.junit.runner.JUnitCore  edu.ucalgary.oop.ScheduleTest
java -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;lib/mysql-connector-java-8.0.23.jar org.junit.runner.JUnitCore  edu.ucalgary.oop.TaskTest
java -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;lib/mysql-connector-java-8.0.23.jar org.junit.runner.JUnitCore  edu.ucalgary.oop.TreatmentTests
