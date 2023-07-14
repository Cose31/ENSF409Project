/**
 * @filename    Treatment.java
 * @author 	    ENSF 409 Project Group 5
 * @members     Caleb Jacobs, Ryan Pryor, Jacob Koep, Max Trainor
 * @version     1.1
 * @since  	    1.0
 */

package edu.ucalgary.oop;

/**
 * "Middleman" class which is used to link a Task object to an
 * Animal object. Also contains an integer for quicker access to 
 * the Task's start hour (startHour).
 */
public class Treatment {
    
    /* MEMBERS */
    private final int TREATMENTID;
    private final Animal ANIMAL;
    private final Task TASK;
    private int startHour;

    /* CONSTRUCTOR */
    public Treatment(int treatmentID, Animal animal, Task task, int startHour) {
        this.TREATMENTID = treatmentID;
        this.ANIMAL = animal;
        this.TASK = task;
        this.startHour = startHour;
    }

    /* GETTERS */
    public int getTreatmentID() { return this.TREATMENTID; }
    public Animal getAnimal() { return this.ANIMAL; }
    public Task getTask() { return this.TASK; }
    public int getStartHour() { return this.startHour; }

    /* SETTERS */
    public void setStartHour(int hour) { this.startHour = hour; }
}
