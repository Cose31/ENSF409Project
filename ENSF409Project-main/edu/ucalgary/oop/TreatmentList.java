/**
 * @filename    TreatmentList.java
 * @author 	    ENSF 409 Project Group 5
 * @members     Caleb Jacobs, Ryan Pryor, Jacob Koep, Max Trainor
 * @version     1.3
 * @since  	    1.0
 */

package edu.ucalgary.oop;

import java.util.*;

/**
 * Contains an ArrayList of Treatment objects, allowing all treatments 
 * within the database to be compiled into a single, iterable collection.
 */
public class TreatmentList {
    
    /* MEMBERS */
    private ArrayList<Treatment> list = new ArrayList<Treatment>();

    /* GETTERS */
    public ArrayList<Treatment> getList() { return this.list; }

    /* METHODS */

    /**
     * Takes a Treatment object as an argument and adds it to the
     * end of the ArrayList "list". 
     * @param treatment Treatment object to add to list
     */
    public void addTreatment(Treatment treatment) {
        this.list.add(treatment);
    }

    /**
     * Clears all elements from the ArrayList "list".
     */
    public void clearList() {
        this.list.clear();
    }
}
