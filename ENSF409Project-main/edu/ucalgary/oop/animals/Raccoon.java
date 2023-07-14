/**
 * @filename    Raccoon.java
 * @author 	    ENSF 409 Project Group 5
 * @members     Caleb Jacobs, Ryan Pryor, Jacob Koep, Max Trainor
 * @version     1.2
 * @since  	    1.0
 */

package edu.ucalgary.oop.animals;

import edu.ucalgary.oop.Animal;
import edu.ucalgary.oop.Feeding;

/**
 * contains species specific Feeding and Cleaning information to facilitate
 * the creation of Animal objects of species "raccoon".
 * Inherits Animal.
 */
public class Raccoon extends Animal {

    /* MEMBERS */
    private final Feeding FEEDING = new Feeding(new int[]{0, 1, 2}, 5, 0);

    /* CONSTRUCTOR */
    public Raccoon(int animalID, String animalName) {
        super(animalID, animalName);
        
        this.setFeeding(this.FEEDING);
        this.setCleaningDuration(5);
    }
}
