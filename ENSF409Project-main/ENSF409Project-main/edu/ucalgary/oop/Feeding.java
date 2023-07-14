/**
 * @filename    Feeding.java
 * @author 	    ENSF 409 Project Group 5
 * @members     Caleb Jacobs, Ryan Pryor, Jacob Koep, Max Trainor
 * @version     1.1
 * @since  	    1.0
 */

package edu.ucalgary.oop;

/**
 * Member variable of all Animal objects.
 * Contains task duration and timeslot value(s) for tasks of 
 * type Feeding.
 */
public class Feeding {
    
    /* MEMBERS */
    private final int[] TIMESLOTS;
    private final int DURATION;
    private final int PREPTIME;

    /* CONSTRUCTOR */
    public Feeding(int[] timeSlots, int duration, int prepTime) {
        this.TIMESLOTS = timeSlots;
        this.DURATION = duration;
        this.PREPTIME = prepTime;
    }

    /* GETTERS */
    public int[] getTimeSlots() { return this.TIMESLOTS; }
    public int getDuration() { return this.DURATION; }
    public int getPrepTime() { return this.PREPTIME; }
}
