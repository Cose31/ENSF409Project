/**
 * @filename    AnimalList.java
 * @author 	    ENSF 409 Project Group 5
 * @members     Caleb Jacobs, Ryan Pryor, Jacob Koep, Max Trainor
 * @version     1.1
 * @since  	    1.0
 */

package edu.ucalgary.oop;

import java.util.*;

/**
 * Contains an ArrayList of Animal objects, allowing all animals within
 * the database to be compiled into a single, iterable collection.
 */
public class AnimalList {

    /* MEMBERS */
    private ArrayList<Animal> list = new ArrayList<Animal>();

    /* GETTERS */
    public ArrayList<Animal> getList() { return this.list; }

    /* METHODS */

    /**
     * Takes an Animal object as an argument and adds it to the
     * end of the ArrayList "list". 
     * 
     * @param animal    Animal object to be added to list
     */
    public void addAnimal(Animal animal) {
        this.list.add(animal);
    }

    /**
     * Clears all elements from the ArrayList "list".
     */
    public void clearList() {
        this.list.clear();
    }
}
