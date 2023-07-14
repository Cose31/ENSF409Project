package edu.ucalgary.oop;

import static org.junit.Assert.*;
import org.junit.*;
import edu.ucalgary.oop.animals.*;
import java.util.*;
import java.sql.SQLException;

public class ScheduleTest
{
    //some expected values to be used in testing
    Schedule schedule;
    int expectedID = 12345;
    String expectedName = "Bob";
    Animal sharedTestAnimal = new Animal(expectedID, expectedName);
    Task task1 = new Task(1, "Vaccination", 5,2);
    Treatment treatment1 = new Treatment(1, sharedTestAnimal, task1, 8);

    int expectedID2 = 54321;
    String expectedName2 = "Steve";
    Animal sharedTestAnimal2 = new Animal(expectedID2, expectedName2);
    Task task2 = new Task(2, "Foot-Wrap", 10, 3);
    Treatment treatment2 = new Treatment(2, sharedTestAnimal2, task2, 12);

    /**
     * instantiate the schedule object before any tests
     */
    @Before
    public void setUp()
    {
        schedule = new Schedule();
    }

    /**
     * Ensures that when the database is observed and copied into List objects, the List objects are correctly
     * instantiated
     * @throws SQLException
     */
    @Test
    public void testParseDataBase() throws SQLException
    {
        schedule.parseDataBase();
        assertNotNull("AnimalList is null.",schedule.getAnimalList());
        assertNotNull("Treatment is null.",schedule.getTreatmentList());
        assertNotNull("TaskList is null.",schedule.getTaskList());
    }


    /**
     * Ensures that the method clearSchedule within Schedule clears the 2D array lists within Schedule
     * properly
     */
    @Test
    public void testClearSchedule()
    {
        //Add non-null objects to the Arraylists
        ArrayList<Treatment> treatments = new ArrayList<Treatment>();
        treatments.add(treatment1);
        ArrayList<Animal> feedingAnimals = new ArrayList<Animal>();
        feedingAnimals.add(sharedTestAnimal);
        ArrayList<Animal> cleaningAnimals = new ArrayList<Animal>();
        cleaningAnimals.add(sharedTestAnimal);
        ArrayList<Boolean> backupRequired = new ArrayList<Boolean>();
        backupRequired.add(true);

        //Add ArrayLists to the schedules
        schedule.getTreatmentSchedule().add(treatments);
        schedule.getFeedingSchedule().add(feedingAnimals);
        schedule.getCleaningSchedule().add(cleaningAnimals);
        schedule.getBackupRequired().add(true);

        //clear the schedule
        schedule.clearSchedule();

        //check if schedule is cleared
        for (int i = 0; i < 24; i++)
        {
            assertEquals("The treatment schedule was not null at timeslot " + i + " where it was supposed to be.",
                    null, schedule.getTreatmentSchedule().get(i).get(0));
            assertEquals("The feeding schedule was not null at timeslot " + i + " where it was supposed to be.",
                    null, schedule.getFeedingSchedule().get(i).get(0));
            assertEquals("The cleaning schedule was not null at timeslot " + i + " where it was supposed to be.",
                    null, schedule.getCleaningSchedule().get(i).get(0));
            assertFalse("BackupRequired was true at timeslot " + i + " where it was supposed to be false.",
                    schedule.getBackupRequired().get(i));
        }
    }

    /**
     * Tests the static method isSameSpecies, which takes in two feeding objects and
     * using the feeding information, determines if the objects belong to Animals of the same species.
     */
    @Test
    public void testIsSameSpecies()
    {
        // Create two Feedings with the same time slots and duration but different prep times
        Feeding feeding1 = new Feeding(new int[]{8, 9},5, 10);
        Feeding feeding2 = new Feeding(new int[]{8, 9},5,  10);

        // Test that isSameSpecies returns true
        assertTrue("isSameSpecies returned false where it should have been true.",
                Schedule.isSameSpecies(feeding1, feeding2));

        // Create two Feedings with different time slots and the same prep time and duration
        Feeding feeding3 = new Feeding(new int[]{8, 9},5, 10);
        Feeding feeding4 = new Feeding(new int[]{9, 10},5,  10);

        // Test that isSameSpecies returns false
        assertFalse("isSameSpecies returned true where it should have been false.",
                Schedule.isSameSpecies(feeding3, feeding4));

        //Create two feedings with the same timeslots and duration but different preptimes
        Feeding feeding5 = new Feeding(new int[]{8, 9},5, 5);
        Feeding feeding6 = new Feeding(new int[]{8, 9},5,  10);

        assertFalse("isSameSpecies returned true where it should have been false.",
                Schedule.isSameSpecies(feeding5, feeding6));

        //Create two feedings with the same timeslots and preptimes but different durations
        Feeding feeding7 = new Feeding(new int[]{8, 9},5, 5);
        Feeding feeding8 = new Feeding(new int[]{8, 9},10,  5);
        System.out.println(feeding7.getDuration());
        System.out.println(feeding8.getDuration());

        // Test that isSameSpecies returns false
        assertFalse("isSameSpecies returned true where it should have been false.", 
                Schedule.isSameSpecies(feeding7, feeding8));
    }

    /**
     * Ensures that with a list of animals, each animal has a cleaning task scheduled only once
     * when the method scheduleCleaning is called
     */
    @Test
    public void testScheduleCleaning()
    {
        schedule.clearSchedule();

        // Create some animals and add them to the schedule's animal list
        Animal animal1 = new Coyote(1, "Simba");
        Animal animal2 = new Beaver(2, "Melman");
        Animal animal3 = new Fox(3, "Dumbo");
        schedule.getAnimalList().addAnimal(animal1);
        schedule.getAnimalList().addAnimal(animal2);
        schedule.getAnimalList().addAnimal(animal3);

        // Schedule cleaning for the animals
        ArrayList<Animal> failedCleaningList = schedule.scheduleCleaning();

        // Assert that failed cleaning list is empty
        assertTrue("Failedcleaninglist was not empty, where it should have been.",failedCleaningList.isEmpty());

        // Assert that each animal has been scheduled for cleaning only once
        assertEquals("Animal1 was scheduled more than once, or not at all.",
                Collections.frequency(schedule.getCleaningSchedule().get(0), animal1), 1);
        assertEquals("Animal2 was scheduled more than once, or not at all.",
                Collections.frequency(schedule.getCleaningSchedule().get(0), animal2), 1);
        assertEquals("Animal3 was scheduled more than once, or not at all.",
                Collections.frequency(schedule.getCleaningSchedule().get(0), animal3), 1);

        //Check that no other cleanings were scheduled in other timeslots
        for (int i = 0; i < 24; i++)
        {
            if (i != 0)
            {
                assertEquals("The cleaning schedule was not null at timeslot " + i + " where it was supposed to be.",
                        null, schedule.getCleaningSchedule().get(i).get(0));
                assertFalse("BackupRequired was true at timeslot " + i + " where it was supposed to be false.",
                        schedule.getBackupRequired().get(i));
            }
        }
    }


    /**
     * Ensures that with a list of animals, each animal has a feeding task scheduled only once
     * when the method scheduleFeeding is called
     * Also tests to ensure that an animal with a feeding object with a duration larger then two hours cannot be scheduled
     */
    @Test
    public void testScheduleFeeding()
    {
        schedule.clearSchedule();

        //Create some animals
        Animal animal1 = new Coyote(1, "Simba");
        Animal animal2 = new Beaver(2, "Melman");
        Animal animal3 = new Fox(3, "Dumbo");

        schedule.getAnimalList().addAnimal(animal1);
        schedule.getAnimalList().addAnimal(animal2);
        schedule.getAnimalList().addAnimal(animal3);

        //Schedule all feedings
        ArrayList<Animal> failedFeedingList = schedule.scheduleFeeding();

        //Check that the feedings were scheduled
        assertFalse("Animal1 feeding was not scheduled.",
                failedFeedingList.contains(animal1));
        assertFalse("Animal2 feeding was not scheduled.",
                failedFeedingList.contains(animal2));
        assertFalse("Animal3 feeding was not scheduled.",
                failedFeedingList.contains(animal3));

        //Check that the feedings were scheduled in the right timeslots
        assertTrue("Animal1 feeding was not scheduled in the right timeslot, or not at all.",
                schedule.getFeedingSchedule().get(19).contains(animal1));
        assertTrue("Animal2 feeding was not scheduled in the right timeslot, or not at all.",
                schedule.getFeedingSchedule().get(8).contains(animal2));
        assertTrue("Animal3 feeding was not scheduled in the right timeslot, or not at all.",
                schedule.getFeedingSchedule().get(0).contains(animal3));


        //Create a bad feeding object
        Feeding badFeeding = new Feeding(new int[]{0,1,2}, 121, 10);
        animal1.setFeeding(badFeeding);

        //schedule bad feeding object
        schedule.clearSchedule();
        schedule.getAnimalList().addAnimal(animal1);
        ArrayList<Animal> failedFeedingList2 = schedule.scheduleFeeding();

        //check that it is contained in the failedFeedingList
        assertTrue("The bad feeding was scheduled, where it should not have been.",
                failedFeedingList2.contains(animal1));

        for (int i = 0; i < 24; i++)
        {
            if (i != 0 && i != 8 && i != 19)
            {
                //check if all other treatment time slots stayed null
                //and all other backuprequired timeslots stayed false (no changes to other timeslots)
                assertEquals("The feeding schedule was not null at timeslot " + i + " where it was supposed to be.",
                        null, schedule.getFeedingSchedule().get(i).get(0));
                assertFalse("BackupRequired was true at timeslot " + i + " where it was supposed to be false.",
                        schedule.getBackupRequired().get(i));
            }
        }

    }

    /**
     * Ensures that with a list of treatments, each treatment is scheduled only once
     * when the method scheduleTreatments is called and is within the range of times
     * defined by its startHour and timeWindow
     */
    @Test
    public void testScheduleTreatments()
    {
        schedule.clearSchedule();

        //Create some treatments
        schedule.getTreatmentList().addTreatment(treatment1);
        schedule.getTreatmentList().addTreatment(treatment2);

        //Schedule the treatments
        ArrayList<Treatment> failedTreatmentList = schedule.scheduleTreatments();
        assertEquals("Not all treatments were scheduled.",0, failedTreatmentList.size());

        //Check that the tasks were scheduled
        ArrayList<ArrayList<Treatment>> treatmentSchedule = schedule.getTreatmentSchedule();
        assertTrue("Treatment1 was not scheduled in timeslot 8.",
                treatmentSchedule.get(8).contains(treatment1));
        assertTrue("Treatment2 was not scheduled in timeslot 12.",
                treatmentSchedule.get(12).contains(treatment2));

        //Make sure they were added and no backup was needed
        assertFalse("Backup was required and set to true in timeslot 8 when it should not have been.",
                schedule.getBackupRequired().get(8));
        assertFalse("Backup was required and set to true in timeslot 12 when it should not have been.",
                schedule.getBackupRequired().get(12));

        //Check that no other tasks were scheduled
        for (int i = 0; i < 24; i++)
        {
            if (i != 8 && i != 12)
            {
                //check if all other treatment time slots stayed null
                //and all other backuprequired timeslots stayed false (no changes to other timeslots)
                assertEquals("The treatment schedule was not null at timeslot " + i + " where it was supposed to be.",
                        null, schedule.getTreatmentSchedule().get(i).get(0));
                assertFalse("BackupRequired was true at timeslot " + i + " where it was supposed to be false.",
                        schedule.getBackupRequired().get(i));
            }
        }
    }
}
