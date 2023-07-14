/**
 * @filename    ScheduleFormatter.java
 * @author 	    ENSF 409 Project Group 5
 * @members     Caleb Jacobs, Ryan Pryor, Jacob Koep, Max Trainor
 * @version     1.5
 * @since  	    1.0
 */

package edu.ucalgary.oop;

import java.util.ArrayList;

/**
 * interface for creating a formatted String to display the generated schedule
 */
public interface ScheduleFormatter {
    
    /* METHODS */
    /**
     * creates a formatted String displaying the generated schedule
     * 
     * @param treatmentList ArrayList<ArrayList> of Treatment objects passed from Schedule
     * @param feedingList   ArrayList<ArrayList> of Animal (Feeding) objects passed from Schedule
     * @param cleaningList  ArrayList<ArrayList> of Animal (Cleaning) objects passed from Schedule
     * @param backupList    ArrayList            of Boolean (backup required) objects passed from Schedule
     * @return              return a formatted String to display the generated schedule
     */
    public static String formattedOutput(ArrayList<ArrayList<Treatment>> treatmentList, 
        ArrayList<ArrayList<Animal>> feedingList, ArrayList<ArrayList<Animal>> cleaningList, ArrayList<Boolean> backupList) {
    
        String output = "Daily Schedule\n------------------------------";

        for(int hour = 0; hour < 24; hour++) {
            if (treatmentList.get(hour).get(0) != null
            || feedingList.get(hour).get(0) != null
            || cleaningList.get(hour).get(0) != null) 
            {
                String startHour = Integer.toString(hour) + ":00";
                if(hour < 10) {
                    startHour = "0" + startHour;
                }
                output = output.concat(startHour + "------------------------------");

                if (backupList.get(hour)) {
                    output = output.concat(" (Call Backup)");
                }
                
                if (treatmentList.get(hour).get(0) != null) {
                    output = addTreatments(output, treatmentList.get(hour));
                }

                if (feedingList.get(hour).get(0) != null) {
                    output = addFeedings(output, feedingList.get(hour));
                }

                if (cleaningList.get(hour).get(0) != null) {
                    output = addCleanings(output, cleaningList.get(hour));
                }

                output = output.concat("------------------------------");
            }
        }
        return output;
    }

    /**
     * appends the Treatment objects to the formatted String "output"
     * 
     * @param output        formatted return String
     * @param treatments    Treatment objects to be added to output
     * @return              returns the formatted output String with all Treatment objects appended
     */
    public static String addTreatments(String output, ArrayList<Treatment> treatments) {
        output = output.concat("\n-----Tasks-----\n");

        for(Treatment treatment : treatments) {
            output = output.concat(Integer.toString(treatment.getAnimal().getAnimalID()) + "/" +
                treatment.getAnimal().getName() + " | ");
            
            output = output.concat(Integer.toString(treatment.getTask().getTaskID()) + "/" +
                treatment.getTask().getDescription() + " | " +
                Integer.toString(treatment.getTask().getDuration()) + " mins.\n");
        }
        return output;
    }

    /**
     * appends the Animal (feeding) objects to the formatted String "output"
     * 
     * @param output        formatted return String
     * @param feedings    Animal (feeding) objects to be added to output
     * @return              returns the formatted output String with all Animal (feeding) objects appended
     */
    public static String addFeedings(String output, ArrayList<Animal> feedings) {
        output = output.concat("\n-----Feedings-----\n");

        Animal prevAnimal = null;
        for(Animal feeding : feedings) {
            output = output.concat(Integer.toString(feeding.getAnimalID()) + "/" +
                feeding.getName() + " | ");
            
            if (prevAnimal == null || !Schedule.isSameSpecies(prevAnimal.getFeeding(), feeding.getFeeding())) {
                output = output.concat("Prep-time: " + Integer.toString(feeding.getFeeding().getPrepTime()) + " mins. ");
            }
            
            output = output.concat("Feed time: " + Integer.toString(feeding.getFeeding().getDuration()) + " mins.\n");
            prevAnimal = feeding;
        }
        return output;
    }

    /**
     * appends the Animal (cleaning) objects to the formatted String "output"
     * 
     * @param output        formatted return String
     * @param cleanings     Animal (cleaning) objects to be added to output
     * @return              returns the formatted output String with all Animal (cleaning) objects appended
     */
    public static String addCleanings(String output, ArrayList<Animal> cleanings) {
        output = output.concat("\n-----Cleanings-----\n");

        for(Animal cleaning : cleanings) {
            output = output.concat(Integer.toString(cleaning.getAnimalID()) + "/" +
                cleaning.getName() + " | ");
            
            output = output.concat("Cleaning time: " + Integer.toString(cleaning.getCleaningDuration()) + " mins.\n");
        }
        return output;
    }
}
