/**
 * @filename    AnimalDatabase.java
 * @author 	    ENSF 409 Project Group 5
 * @members     Caleb Jacobs, Ryan Pryor, Jacob Koep, Max Trainor
 * @version     1.3
 * @since  	    1.0
 */

package edu.ucalgary.oop;

import edu.ucalgary.oop.animals.*;
import java.util.*;
import java.sql.*;

/**
 * class containing Connection and ResultSet for connected database
 */
public class AnimalDatabase {

    /* MEMBERS */
    private Connection dbConnect;
    private ResultSet results;

    /* METHODS */
    /**
     * attempts to create a connection to the database using DriverManager.getConnection
     * catches SQLException and prints stack trace
     */
    public void connectDataBase() throws SQLException { //Trys to connect to the ewr database using student and ensf as a user and password combo
        dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr","student","ensf");
    }

    /**
     * closes connection to the database
     */
    public void closeDataBase()  throws SQLException { //Trys to close access to the database
        results.close();
        dbConnect.close();
    }

    /**
     * attempts to fill an AnimalList object with all Animals found in the connected database
     * catches SQLException and prints stack trace
     */
    public AnimalList extractAnimals() throws SQLException {
        AnimalList animalList = new AnimalList();
       
        PreparedStatement pullAnimals = dbConnect.prepareStatement("SELECT * FROM animals"); //Grabs all information at once in the animals table
        results = pullAnimals.executeQuery(); //Stores the animals table in a ResultSet object
        int animalID;
        String animalName;
        String animalSpecies;
        
        
        while (results.next()) {      //iterates through each row
            animalID = results.getInt("AnimalID"); //parse the ID of the animal into an integer
            animalName = results.getString("AnimalNickname"); //parse the name of the animal into a String
            animalSpecies = results.getString("AnimalSpecies"); //parse the species of the animal into a String

            Animal newAnimal = new Animal(0, "placeholder"); //need to instantiate a dummy object to avoid compile-time error
            
            switch(animalSpecies) {   //Using the species string, instatiate newAnimal using the associated subclass constructor for the species.
                case "coyote":
                    newAnimal = new Coyote(animalID, animalName);
                    break;
                case "porcupine":
                    newAnimal = new Porcupine(animalID, animalName);
                    break;
                case "fox":
                    newAnimal = new Fox(animalID, animalName);
                    break;
                case "raccoon":
                    newAnimal = new Raccoon(animalID, animalName);
                    break;
                case "beaver":
                    newAnimal = new Beaver(animalID, animalName);
                    break;
            }
            animalList.addAnimal(newAnimal); //add the Animal object created here into the AnimalList object, animalList.
        }
        pullAnimals.close(); //Close the SQL statement to avoid unwanted resource usage.
        
        return animalList;
    }

    /**
     * attempts to fill a TaskList object with all Tasks found in the connected database
     * catches SQLException and prints stack trace
     */
    public TaskList extractTasks() throws SQLException { //Works similarily to extractAnimals(), but does not contain multiple constructors and simply uses the Task constructor instead.
        TaskList taskList = new TaskList();
        
        PreparedStatement pullTasks = dbConnect.prepareStatement("SELECT * FROM tasks");
        results = pullTasks.executeQuery();

        while (results.next()) {
            int taskID = results.getInt("TaskID");
            String description = results.getString("Description");
            int duration = results.getInt("Duration");
            int maxWindow = results.getInt("MaxWindow");

            Task newTask = new Task(taskID, description, duration, maxWindow);

            taskList.addTask(newTask);
        }
        pullTasks.close();
        

        return taskList;
    }

    /**
     * attempts to fill a TreatmentList object with all Treatments found in the connected database
     * catches SQLException and prints stack trace
     */
    public TreatmentList extractTreatments(AnimalList animalList, TaskList taskList) throws SQLException {
        TreatmentList treatmentList = new TreatmentList();
            
        PreparedStatement pullTreatments = dbConnect.prepareStatement("SELECT * FROM treatments"); //Grabs all information at once in the treatments table
        results = pullTreatments.executeQuery(); //Stores the table in a ResultSet object
        
        while (results.next()) { //iterates through each row
            int treatmentID = results.getInt("TreatmentID");
            int animalID = results.getInt("AnimalID");
            int taskID = results.getInt("TaskID");
            int startHour = results.getInt("StartHour"); //parsing of various fields into integers

            Animal inputAnimal = findAnimal(animalID, animalList); //Needs to find an Animal object with an ID number corresponding to the database to be stored in the Treatment object
            Task inputTask = findTask(taskID, taskList); //Same idea, finds a Task object with the same ID number as was pulled from this row of the treatments table.
            //Note that under normal operation of the GUI, it will not be possible to insert a Treatment with a TaskID or AnimalID that does not exist.

            Treatment newTreatment = new Treatment(treatmentID, inputAnimal, inputTask, startHour);
            treatmentList.addTreatment(newTreatment); //Add the new Treatment object to treatmentList

            Collections.sort(treatmentList.getList(), new CustomComparator()); //Sorts the treatmentList based on a comparator which prioritizes tasks with smaller timeWindows
        }
        pullTreatments.close(); //Close SQL statement
          
 
        return treatmentList;
    }

    /**
     * returns an Animal object with the given animalID found in animalList
     * 
     * @param animalID      desired animalID (Animal identifier)
     * @param animalList    AnimalList to search for desired Animal
     * @return              Animal found within animalList where Animal.animalID == animalID
     */
    private static Animal findAnimal(int animalID, AnimalList animalList) {
        Iterator<Animal> it = animalList.getList().iterator();
        Animal observed = animalList.getList().get(0); //Another dummy object to avoid compile time error
        
        while (it.hasNext()) {
            observed = it.next();
            if (observed.getAnimalID() == animalID) {  //Iterates through animalList until it finds an Animal with the same ID as the argument to this method
                return observed;
            }
        }
        return observed;
    }

    /**
     * returns a Task object with the given taskID found in taskList
     * 
     * @param taskID     desired taskID (Task identifier)
     * @param taskList   TaskList to search for desired Task
     * @return           Task found within taskList where Task.taskID == taskID
     */
    private static Task findTask(int taskID, TaskList taskList) { //Works identically to findAnimal
        Iterator<Task> it = taskList.getList().iterator();
        Task observed = taskList.getList().get(0);

        while (it.hasNext()) {
            observed = it.next();
            if (observed.getTaskID() == taskID) {
                return observed;
            }
        }
        return observed;
    }

    /**
     * attempts to update a Treatment inside the database with a new StartHour
     * returns a boolean indicating whether the update was successful
     * catches SQLException and prints stack trace
     * 
     * @param treatment Treatment who's StartHour is to be modified
     * @param newStart  new StartHour for treatment
     * @return          true if update was a success, false otherwise
     */
    public boolean modifyTreatmentStartTime(Treatment treatment, int newStart)  throws SQLException {
        int result = 0;
        connectDataBase();

        
        String query = "UPDATE treatments SET StartHour = ? WHERE TreatmentID = ?";
        PreparedStatement pullTreatments = this.dbConnect.prepareStatement(query);
        pullTreatments.setInt(1, newStart);
        pullTreatments.setInt(2, treatment.getTreatmentID());

        result = pullTreatments.executeUpdate();  
    

        closeDataBase();

        if (result != 1) {
            return false;
        } else {
            return true;
        }
    }
}
