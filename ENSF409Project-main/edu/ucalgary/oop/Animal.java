/**
 * @filename    Animal.java
 * @author 	    ENSF 409 Project Group 5
 * @members     Caleb Jacobs, Ryan Pryor, Jacob Koep, Max Trainor
 * @version     1.2
 * @since  	    1.0
 */

 /** 
  * Parent class for Beaver, Coyote, Fox, Porcupine, and Raccoon.
  * Contains all member variables shared across all animal types.
  */
package edu.ucalgary.oop;

public class Animal {

    /* MEMBERS */
    private final int ANIMALID;
    private String name;
    private Feeding feeding;
    private int cleaningDuration;
    private boolean isOrphan;
    
    /* CONSTRUCTOR */
    public Animal(int id, String name) {
        this.ANIMALID = id;
        this.name = name;
        
        this.isOrphan = detectIsOrphan();
    }

    /* GETTERS */
    public int getAnimalID() { return this.ANIMALID; }
    public String getName() { return this.name; }
    public Feeding getFeeding() { return this.feeding; }
    public int getCleaningDuration() { return this.cleaningDuration; }
    public boolean getIsOrphan() { return this.isOrphan; }

    /* SETTERS */
    public void setName(String newName) { this.name = newName; }
    public void setCleaningDuration(int duration) { this.cleaningDuration = duration; }
    public void setFeeding(Feeding feeding) { this.feeding = feeding; }

    /* METHODS */

    /**
     * Determines if an Animal in the database is an orphan, based on the criteria that different orphaned
     * animals of the same species are contained within a single database entry (and therefore a single
     * Animal object), so their "name" will contain either "," or "and".
     * 
     * Returns true if the Animal is an orphan, otherwise returns false.
     * 
     * @return      boolean indicating whether Animal is an orphan
     */
    private boolean detectIsOrphan()
    {
        if (this.name.contains(",") || this.name.contains("and")) { return true; }
        else { return false; }
    }
}