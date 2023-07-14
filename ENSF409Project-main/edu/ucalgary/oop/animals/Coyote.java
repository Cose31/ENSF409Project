/**
 * @filename    Coyote.java
 * @author 	    ENSF 409 Project Group 5
 * @members     Caleb Jacobs, Ryan Pryor, Jacob Koep, Max Trainor
 * @version     1.1
 * @since  	    1.0
 */

package edu.ucalgary.oop.animals;

import edu.ucalgary.oop.Animal;
import edu.ucalgary.oop.Feeding;

/**
 * contains species specific Feeding and Cleaning information to facilitate
 * the creation of Animal objects of species "coyote".
 * Inherits Animal.
 */
public class Coyote extends Animal {

    /* MEMBERS */
    private final Feeding FEEDING = new Feeding(new int[]{19, 20, 21}, 5, 10);
    
    /* CONSTRUCTOR */
    public Coyote(int animalID, String animalName) {
        super(animalID, animalName);
        
        this.setFeeding(this.FEEDING);
        this.setCleaningDuration(5);
    }

    

}
