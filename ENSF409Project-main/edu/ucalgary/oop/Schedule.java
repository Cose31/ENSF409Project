/**
 * @filename    Schedule.java
 * @author 	    ENSF 409 Project Group 5
 * @members     Caleb Jacobs, Ryan Pryor, Jacob Koep, Max Trainor
 * @version     1.9
 * @since  	    1.0
 */

package edu.ucalgary.oop;

import java.util.*;
import java.sql.*;

/**
 * class for setting and holding schedule/daily plan
 */
public class Schedule implements ScheduleFormatter {

    /* MEMBERS */
    private ArrayList<ArrayList<Treatment>> treatmentSchedule = new ArrayList<ArrayList<Treatment>>();
    private ArrayList<ArrayList<Animal>> feedingSchedule = new ArrayList<ArrayList<Animal>>();
    private ArrayList<ArrayList<Animal>> cleaningSchedule = new ArrayList<ArrayList<Animal>>();
    private ArrayList<Boolean> backupRequired = new ArrayList<Boolean>();
    
    private AnimalList animalList = new AnimalList();
    private TreatmentList treatmentList = new TreatmentList();
    private TaskList taskList = new TaskList();

    private AnimalDatabase animalDatabase = new AnimalDatabase();

    /* GETTERS */
    public AnimalList getAnimalList() { return this.animalList; }
    public TreatmentList getTreatmentList() { return this.treatmentList; }
    public TaskList getTaskList() { return this.taskList; }
    public ArrayList<ArrayList<Treatment>> getTreatmentSchedule() { return this.treatmentSchedule; }
    public ArrayList<ArrayList<Animal>> getFeedingSchedule() { return this.feedingSchedule; }
    public ArrayList<ArrayList<Animal>> getCleaningSchedule() { return this.cleaningSchedule; }
    public ArrayList<Boolean> getBackupRequired() { return this.backupRequired; }
    public AnimalDatabase getAnimalDatabase() { return this.animalDatabase; }

    /* METHODS */
    /**
     * clears all lists, then uses animalDatabase to fill all three lists with objects found in database
     */
    public void parseDataBase() throws SQLException {

        animalList.clearList();    
        treatmentList.clearList(); 
        taskList.clearList();

        animalDatabase.connectDataBase();

        this.animalList = animalDatabase.extractAnimals(); 
        this.taskList = animalDatabase.extractTasks();
        this.treatmentList = animalDatabase.extractTreatments(this.animalList, this.taskList);

        animalDatabase.closeDataBase();
    }
    
    /**
     * resets Schedule, clearing all ArrayLists and initializing them with null values
     */
    public void clearSchedule() {
        treatmentSchedule.clear();  
        feedingSchedule.clear();
        cleaningSchedule.clear(); 
        backupRequired.clear();

        for (int i = 0; i < 24; i++) {
            ArrayList<Treatment> tmp1 = new ArrayList<Treatment>();
            ArrayList<Animal> tmp2 = new ArrayList<Animal>();
            ArrayList<Animal> tmp3 = new ArrayList<Animal>(); 
            tmp1.add(null);
            tmp2.add(null);
            tmp3.add(null);
            treatmentSchedule.add(tmp1);
            cleaningSchedule.add(tmp2);
            feedingSchedule.add(tmp3);
            backupRequired.add(false);
        } 
    }

    /**
     * fills treatmentSchedule with valid Treatments from treatmentList
     * attempts to schedule normally first, then attempts to schedule using backup (scheduleTreatmentWithBackup())
     * adds Treatments that could not be scheduled to failedTreatmentList to be returned
     * failedTreatmentList will be of size 0 if all Treatments were successfully scheduled
     * 
     * @return  ArrayList<Treatment> containing all Treatment objects that were unable to be scheduled
     */
    public ArrayList<Treatment> scheduleTreatments() {
        ArrayList<Treatment> failedTreatmentList = new ArrayList<Treatment>();
        for (Treatment currentTreatment: this.treatmentList.getList()) {

            int firstTimeSlot = currentTreatment.getStartHour();
            int lastTimeSlot = firstTimeSlot + currentTreatment.getTask().getTimeWindow();

            if (lastTimeSlot >= 24) {
                lastTimeSlot -= 24;
            }
            int i = firstTimeSlot;
            while (i != lastTimeSlot) {
                if (isTime(i, currentTreatment, false) && !hasTaskScheduled(currentTreatment)) {
                    if (this.treatmentSchedule.get(i).get(0) == null) {
                        this.treatmentSchedule.get(i).set(0, currentTreatment);
                    } else {
                        this.treatmentSchedule.get(i).add(currentTreatment);
                    }
                    this.backupRequired.set(i, false);
                }
                if (i < 23) {
                    i++;
                } else {
                    i = 0;
                }
            }

            if (!hasTaskScheduled(currentTreatment)) {
                scheduleTreatmentWithBackup(currentTreatment);
            }

            if (!hasTaskScheduled(currentTreatment)) {
                failedTreatmentList.add(currentTreatment);
            }
        }
        return failedTreatmentList;
    }

    /**
     * attempts to add currentTreatment to treatmentSchedule using backup
     * also updates backupRequired to true for this time slot if successful
     * 
     * @param currentTreatment  Treatment to be scheduled using backup
     */
    private void scheduleTreatmentWithBackup(Treatment currentTreatment) {

        int firstTimeSlot = currentTreatment.getStartHour();
        int lastTimeSlot = firstTimeSlot + currentTreatment.getTask().getTimeWindow();

        if (lastTimeSlot >= 24) {
            lastTimeSlot -= 24;
        }

        int i = firstTimeSlot;
        while (i != lastTimeSlot) {
            if (isTime(i, currentTreatment, true) && !hasTaskScheduled(currentTreatment)) {
                if (this.treatmentSchedule.get(i).get(0) == null) {
                    this.treatmentSchedule.get(i).set(0, currentTreatment);
                } else {
                    this.treatmentSchedule.get(i).add(currentTreatment);
                }
                this.backupRequired.set(i, true);
            }
            if (i < 23) {
                i++;
            } else {
                i = 0;
            }
        }
    }

    /**
     * fills feedingSchedule with valid Animals from animalList
     * attempts to schedule normally first, then attempts to schedule using backup (scheduleFeedingWithBackup())
     * adds Animals that could not be scheduled to failedFeedingList to be returned
     * 
     * @return  ArrayList<Animal> containing all Animal objects that were unable to be scheduled
     */
    public ArrayList<Animal> scheduleFeeding() {
        ArrayList<Animal> failedFeedingList = new ArrayList<Animal>();
        for (Animal currentAnimal: this.animalList.getList()) {
            int[] timeSlots = currentAnimal.getFeeding().getTimeSlots();

            for (int time: timeSlots) {
                if (isTime(time, currentAnimal.getFeeding(), false) && !hasFeedingScheduled(currentAnimal) && !currentAnimal.getIsOrphan()) {
                    if (feedingSchedule.get(time).get(0) == null) {
                        this.feedingSchedule.get(time).set(0, currentAnimal);
                    } else {
                        this.feedingSchedule.get(time).add(currentAnimal);
                    }
                this.backupRequired.set(time, false);
                addExtraFeedings(time, currentAnimal);
                }  
            }

            if (!hasFeedingScheduled(currentAnimal) && !currentAnimal.getIsOrphan()) {
                scheduleFeedingWithBackup(currentAnimal);
            }

            if (!hasFeedingScheduled(currentAnimal) && !currentAnimal.getIsOrphan()) {
                failedFeedingList.add(currentAnimal);
            }
        }

        return failedFeedingList;

    }

    /**
     * adds extra feedings for Animals of the same species to the same time slot if applicable and possible
     * 
     * @param timeSlot  time slot to add additional feedings to
     * @param animal    Animal to check for same species feedings
     */
    private void addExtraFeedings(int timeSlot, Animal animal) {
        for (Animal currentAnimal: this.animalList.getList()) {
            if (isSameSpecies(animal.getFeeding(), currentAnimal.getFeeding())) {
                if (isTime(timeSlot, currentAnimal.getFeeding(), false) && (!hasFeedingScheduled(currentAnimal) && !currentAnimal.getIsOrphan())) {
                    if (feedingSchedule.get(timeSlot).get(0) == null) {
                        this.feedingSchedule.get(timeSlot).set(0, currentAnimal);
                    } else {
                        this.feedingSchedule.get(timeSlot).add(currentAnimal);
                    }
                    this.backupRequired.set(timeSlot, false);
                }
            }
        }
    }

    /**
     * attempts to add animal to feedingSchedule using backup
     * also updates backupRequired to true for this time slot if successful
     * 
     * @param animal Animal to be scheduled using backup
     */
    private void scheduleFeedingWithBackup(Animal animal) {
        int[] timeSlots = animal.getFeeding().getTimeSlots();
        for (int time: timeSlots) {
            if (isTime(time, animal.getFeeding(), true) && (!hasFeedingScheduled(animal) && !animal.getIsOrphan())) {
                if (feedingSchedule.get(time).get(0) == null) {
                    this.feedingSchedule.get(time).set(0, animal);
                } else {
                    this.feedingSchedule.get(time).add(animal);
                }
                this.backupRequired.set(time, true);
            }  
        }
    }

    /**
     * checks if two Feeding objects are for Animals of the same species
     * returns a boolean indicating the result
     * 
     * @param feeding1  first Feeding to compare
     * @param feeding2  second Feeding to compare
     * @return          true if Animals are the same species, false otherwise
     */
    public static boolean isSameSpecies(Feeding feeding1, Feeding feeding2) {
        return (
            Arrays.equals(feeding1.getTimeSlots(), feeding2.getTimeSlots()) 
            && (feeding1.getPrepTime() == feeding2.getPrepTime() 
            && feeding1.getDuration() == feeding2.getDuration())
        );
    }

    /**
     * fills cleaningSchedule with valid Animals from animalList
     * attempts to schedule normally first, then attempts to schedule using backup (scheduleCleaningWithBackup())
     * adds Animals that could not be scheduled to failedCleaningList to be returned
     * 
     * @return  ArrayList<Animal> containing all Animal objects that were unable to be scheduled
     */
    public ArrayList<Animal> scheduleCleaning() {
        ArrayList<Animal> failedCleaningList = new ArrayList<Animal>();
        for (Animal currentAnimal: this.animalList.getList()) {
            for (int i = 0; i < 24; i++) {
                if (isTime(i, currentAnimal.getCleaningDuration(), false) && !hasCleaningScheduled(currentAnimal)) {
                    if (this.cleaningSchedule.get(i).get(0) == null) {
                        this.cleaningSchedule.get(i).set(0, currentAnimal);
                    } else {
                        this.cleaningSchedule.get(i).add(currentAnimal);
                    }
                    this.backupRequired.set(i, false);
                }
            }

            if (!hasCleaningScheduled(currentAnimal)) {
                scheduleCleaningWithBackup(currentAnimal);
            }

            if (!hasCleaningScheduled(currentAnimal)) {
                failedCleaningList.add(currentAnimal);
            }

        }
        return failedCleaningList;
    }

    /**
     * attempts to add currentAnimal to cleaningSchedule using backup
     * also updates backupRequired to true for this time slot if successful
     * 
     * @param currentAnimal  Animal to be scheduled using backup
     */
    private void scheduleCleaningWithBackup(Animal currentAnimal) {
        for (int i = 0; i < 24; i++) {
            if (isTime(i, currentAnimal.getCleaningDuration(), true) && !hasCleaningScheduled(currentAnimal)) {
                if (this.cleaningSchedule.get(i).get(0) == null) {
                    this.cleaningSchedule.get(i).set(0, currentAnimal);
                } else {
                    this.cleaningSchedule.get(i).add(currentAnimal);
                }
                this.backupRequired.set(i, true);
            }
        }
    }

    /**
     * determines whether treatment has already been added to treatmentSchedule
     * returns a boolean indicating result
     * 
     * @param treatment Treatment to be tested
     * @return          true if treatment is found in treatmentSchedule, false otherwise
     */
    private boolean hasTaskScheduled(Treatment treatment) {
        for(ArrayList<Treatment> list: this.treatmentSchedule) {
            for (Treatment currentTreatment: list) {
                if (currentTreatment != null) {
                    if (currentTreatment.getTreatmentID() == treatment.getTreatmentID()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * determines whether animal has already been added to feedingSchedule
     * returns a boolean indicating result
     * 
     * @param animal    Animal to be tested
     * @return          true if animal is found in feedingSchedule, false otherwise
     */
    private boolean hasFeedingScheduled(Animal animal) {
        for(ArrayList<Animal> list: this.feedingSchedule) {
            for (Animal currentAnimal: list) {
                if (currentAnimal != null) {
                    if (currentAnimal.getAnimalID() == animal.getAnimalID()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * determines whether animal has already been added to cleaningSchedule
     * returns a boolean indicating result
     * 
     * @param animal    Animal to be tested
     * @return          true if animal is found in cleaningSchedule, false otherwise
     */
    private boolean hasCleaningScheduled(Animal animal) {
        for(ArrayList<Animal> list: this.cleaningSchedule) {
            for (Animal currentAnimal: list) {
                if (currentAnimal != null) {
                    if (currentAnimal.getAnimalID() == animal.getAnimalID()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * checks if a time slot has sufficient time to complete the given Treatment
     * returns a boolean indicating the result
     * 
     * @param timeSlot  time slot to check for sufficient time
     * @param treatment Treatment to check for eligibility
     * @param addBackup indicates whether backup should be considered relative to time available
     * @return          returns true if timeSlot has sufficient time, false otherwise
     */
    private boolean isTime(int timeSlot, Treatment treatment, boolean addBackup)
    {
        int totalTreatmentTime = sumTime(timeSlot, true); 

        if(!addBackup)
        {
            totalTreatmentTime += treatment.getTask().getDuration();

            return totalTreatmentTime <= 60;
        }

        else
        {
            totalTreatmentTime += treatment.getTask().getDuration();
            return totalTreatmentTime <= 120;
        }
    }

    /**
     * checks if a time slot has sufficient time to complete the given Feeding
     * returns a boolean indicating the result
     * 
     * @param timeSlot  time slot to check for sufficient time
     * @param feeding   Feeding to check for eligibility
     * @param addBackup indicates whether backup should be considered relative to time available
     * @return          returns true if timeSlot has sufficient time, false otherwise
     */
    private boolean isTime(int timeSlot, Feeding feeding, boolean addBackup)
    {
        int totalFeedingTime = sumTime(timeSlot, false);
        Animal prevAnimal = null;
        ArrayList<Animal> list = feedingSchedule.get(timeSlot);
        for(Animal animal: list)
        {
            if (animal != null)
            {
                if(prevAnimal != null && isSameSpecies(animal.getFeeding(), prevAnimal.getFeeding()))
                {
                    totalFeedingTime += animal.getFeeding().getDuration();

                }
                else
                {
                    totalFeedingTime += animal.getFeeding().getPrepTime();
                    totalFeedingTime += animal.getFeeding().getDuration();
                }
                prevAnimal = animal;
            }
        }

        if(prevAnimal != null && isSameSpecies(feeding, prevAnimal.getFeeding()))
        {
            totalFeedingTime += feeding.getDuration();
        }
        else
        {
            totalFeedingTime += feeding.getPrepTime();
            totalFeedingTime += feeding.getDuration();
        }

        if (!addBackup) 
        {
            return totalFeedingTime <= 60;
        } 
        else
        {
            return totalFeedingTime <= 120;
        }    
        
    }

    /**
     * checks if a time slot has sufficient time to complete the given Cleaning (cleaningDuration)
     * returns a boolean indicating the result
     * 
     * @param timeSlot          time slot to check for sufficient time
     * @param cleaningDuration  Cleaning to check for eligibility
     * @param addBackup         indicates whether backup should be considered relative to time available
     * @return                  returns true if timeSlot has sufficient time, false otherwise
     */
    private boolean isTime(int timeSlot, int cleaningDuration, boolean addBackup)
    {
        int totalCleaningTime = sumTime(timeSlot, true);
        totalCleaningTime += cleaningDuration;

        if(!addBackup)
        {
            return totalCleaningTime <= 60;
        }
        else
        {
            return totalCleaningTime <= 120;
        }
    }

    /**
     * calculates total time used by scheduled tasks (Feedings, Treatments, etc.) in the given time slot
     * returns an int of the total time used
     * 
     * @param timeSlot          time slot to calculate time used within
     * @param includeFeeding    indicates whether to include Feedings in the calculations
     * @return                  int equal to the sum of all minutes used within the given timeslot
     */
    private int sumTime(int timeSlot, boolean includeFeeding) {
        int totalTime = 0;
        ArrayList<Treatment> treatmentList = treatmentSchedule.get(timeSlot);
        ArrayList<Animal> feedingList = feedingSchedule.get(timeSlot);
        ArrayList<Animal> cleaningList = cleaningSchedule.get(timeSlot);

        for(Treatment treatment: treatmentList) {
            if (treatment != null) {
                totalTime += treatment.getTask().getDuration();
            }
        }

        if (includeFeeding) {
            Animal prevAnimal = null;
            for(Animal animal: feedingList) {
                if (animal != null)
                {
                    if(prevAnimal != null && isSameSpecies(animal.getFeeding(), prevAnimal.getFeeding()))
                    {
                        totalTime += animal.getFeeding().getDuration();
                    }
                    else
                    {
                        totalTime += animal.getFeeding().getPrepTime();
                        totalTime += animal.getFeeding().getDuration();
                    }
                    prevAnimal = animal;
                }
            }
        }

        for (Animal animal: cleaningList) {
            if (animal != null) {
                totalTime += animal.getCleaningDuration();
            }
        }

        return totalTime;  
    }
}