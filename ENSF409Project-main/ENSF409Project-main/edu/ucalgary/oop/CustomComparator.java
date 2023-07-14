/**
 * @filename    CustomComparator.java
 * @author 	    ENSF 409 Project Group 5
 * @members     Caleb Jacobs, Ryan Pryor, Jacob Koep, Max Trainor
 * @version     1.0
 * @since  	    1.0
 */

package edu.ucalgary.oop;

import java.util.Comparator;

/**
 * Used for ordering Treaments in the list from lowest the highest timeWindow
 * Returns positive number when o1 has a larger timeWindow, and negative when o2's is larger
 * These ints are used in the sorting algorithm provided by java Collections                                                                  
 * Refer to javas documentation on Comparator and compare
 */
public class CustomComparator implements Comparator<Treatment> {
    /**
     * Compares timeWindow of two Treatment objects
     * Returns positive int when o1.timeWindow > o2.timeWindow, 0 when o2.timeWindow == o2.timeWindow, negative otherwise
     * @param o1    first Treatment being compared
     * @param o2    second Treatment being compared
     * @return      difference between o1.timeWindow and o2.timeWindow
     */
    @Override
    public int compare(Treatment o1, Treatment o2) { 
        return o1.getTask().getTimeWindow() - o2.getTask().getTimeWindow();
    }
}
