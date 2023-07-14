/**
 * @filename    TaskList.java
 * @author 	    ENSF 409 Project Group 5
 * @members     Caleb Jacobs, Ryan Pryor, Jacob Koep, Max Trainor
 * @version     1.2
 * @since  	    1.0
 */

package edu.ucalgary.oop;

import java.util.*;

/**
 * Contains an ArrayList of Task objects, allowing all tasks within
 * the database to be compiled into a single, iterable collection.
 */
public class TaskList {
    
    /* MEMBERS */
    private ArrayList<Task> list = new ArrayList<Task>();

    /* GETTERS */
    public ArrayList<Task> getList() { return this.list; }

    /* METHODS */

    /**
     * Takes a Task object as an argument and adds it to the
     * end of the ArrayList "list". 
     * 
     * @param task  Task object to be added to list
     */
    public void addTask(Task task) {
        this.list.add(task);
    }

    /**
     * Clears all elements from the ArrayList "list".
     */
    public void clearList() {
        this.list.clear();
    }
}
