package edu.ucalgary.oop;

import edu.ucalgary.oop.animals.*;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class ScheduleFormatterTests
{
    Animal animal1 = new Coyote(1, "Jeff");
    Animal animal2 = new Beaver(2, "Mason");
    Task task1 = new Task(1, "Vaccination", 5,2);
    Task task2 = new Task(2, "Check-up", 10,2);
    Treatment treatment1 = new Treatment(1, animal1, task1, 8);
    Treatment treatment2 = new Treatment(2, animal2, task2, 9);

    /**
     * Tests the formattedOutput method of ScheduleFormatter to ensure that with a generated schedule, the formatted output is as expected
     * based on the stylization of the schedule
     */
    @Test
    public void testFormattedOutput() {
        // Test case 1: Empty schedules
        ArrayList<ArrayList<Treatment>> treatmentList = new ArrayList<>();
        ArrayList<ArrayList<Animal>> feedingList = new ArrayList<>();
        ArrayList<ArrayList<Animal>> cleaningList = new ArrayList<>();
        ArrayList<Boolean> backupList = new ArrayList<>();
        for (int i = 0; i < 24; i++)
        {
            ArrayList<Treatment> tmp1 = new ArrayList<Treatment>();
            ArrayList<Animal> tmp2 = new ArrayList<Animal>();
            ArrayList<Animal> tmp3 = new ArrayList<Animal>();
            tmp1.add(null);
            tmp2.add(null);
            tmp3.add(null);
            treatmentList.add(tmp1);
            cleaningList.add(tmp2);
            feedingList.add(tmp3);
            backupList.add(false);
        }
        String expectedOutput = "Daily Schedule\n------------------------------";
        String actualOutput = ScheduleFormatter.formattedOutput(treatmentList, feedingList, cleaningList, backupList);
        assertEquals("Output was not as expected for an empty schedule. Check spaces, dashes, and all events for correctness."
                ,expectedOutput, actualOutput);

        // Test case 2: Non-empty schedules
        feedingList.get(8).set(0, animal1);
        feedingList.get(10).set(0, animal2);
        cleaningList.get(12).set(0, animal1);
        treatmentList.get(14).set(0, treatment1);
        treatmentList.get(14).add(treatment2);
        backupList.set(8, true);

        String expectedOutput1 = "Daily Schedule\n" +
                "------------------------------08:00------------------------------ (Call Backup)\n-----Feedings-----\n1/Jeff | Prep-time: 10 mins. Feed time: 5 mins." +
                "\n------------------------------10:00------------------------------\n-----Feedings-----\n2/Mason | Prep-time: 0 mins. Feed time: 5 mins." +
                "\n------------------------------12:00------------------------------\n-----Cleanings-----\n1/Jeff | Cleaning time: 5 mins." +
                "\n------------------------------14:00------------------------------\n-----Tasks-----\n1/Jeff | 1/Vaccination | 5 mins.\n2/Mason | 2/Check-up | 10 mins." +
                "\n------------------------------";
        String actualOutput1 = ScheduleFormatter.formattedOutput(treatmentList, feedingList, cleaningList, backupList);
        assertEquals("Output for a non-empty schedule was not as expected. Check dashes, spaces, new lines and events for correctness."
                ,expectedOutput1, actualOutput1);
    }

     /**
     * Tests the addTreatments method of ScheduleFormatter to ensure that with an empty schedule, the correct header is created
     *  for the medical tasks section
     */
    @Test
    public void testAddTreatments() {
        // Test case 1: Empty treatment list
        String output = "Test output";
        ArrayList<Treatment> treatments = new ArrayList<>();
        String expectedOutput = "Test output\n-----Tasks-----\n";
        String actualOutput = ScheduleFormatter.addTreatments(output, treatments);
        assertEquals("Output for an empty treatment list was not was not as expected. Check dashes, spaces, new lines and event for correctness."
                ,expectedOutput, actualOutput);

        // Test case 2: Non-empty treatment list
        output = "";
        treatments.add(treatment1);
        treatments.add(treatment2);

        String expectedOutput1 = "\n-----Tasks-----\n" +
                "1/Jeff | 1/Vaccination | 5 mins.\n" +
                "2/Mason | 2/Check-up | 10 mins.\n";
        String actualOutput1 = ScheduleFormatter.addTreatments(output, treatments);
        assertEquals("Output for a non-empty treatment list was not was not as expected. Check dashes, spaces, new lines and event for correctness.",
                expectedOutput1, actualOutput1);
    }

    /**
     * Tests the addFeedings method of ScheduleFormatter to ensure that with an empty schedule, the correct header is created
     * for the feedings section of the schedule output
    */
    @Test
    public void testAddFeedings() {
        // Test case 1: Empty treatment list
        String output = "Test output";
        ArrayList<Animal> feedings = new ArrayList<>();
        String expectedOutput = "Test output\n-----Feedings-----\n";
        String actualOutput = ScheduleFormatter.addFeedings(output, feedings);
        assertEquals("Output for an empty feeding list was not was not as expected. Check dashes, spaces, new lines and event for correctness.",
                expectedOutput, actualOutput);

        // Test case 2: Non-empty treatment list
        output = "";
        feedings.add(animal1);
        feedings.add(animal2);

        String expectedOutput1 = "\n-----Feedings-----\n" +
                "1/Jeff | Prep-time: 10 mins. Feed time: 5 mins.\n" +
                "2/Mason | Prep-time: 0 mins. Feed time: 5 mins.\n";
        String actualOutput1 = ScheduleFormatter.addFeedings(output, feedings);
        assertEquals("Output for a non-empty feeding list was not was not as expected. Check dashes, spaces, new lines and event for correctness."
                ,expectedOutput1, actualOutput1);
    }

    /**
     * Tests the addCleanings method of ScheduleFormatter to ensure that with an empty schedule, the correct header is created
     *  for the cleaning section
     */
    @Test
    public void testAddCleanings()
    {
        // Test case 1: Empty treatment list
        String output = "Test output";
        ArrayList<Animal> cleanings = new ArrayList<>();
        String expectedOutput = "Test output\n-----Cleanings-----\n";
        String actualOutput = ScheduleFormatter.addCleanings(output, cleanings);
        assertEquals("Output for an empty cleaning list was not was not as expected. Check dashes, spaces, new lines and event for correctness.",
                expectedOutput, actualOutput);

        // Test case 2: Non-empty treatment list
        output = "";
        cleanings.add(animal1);
        cleanings.add(animal2);

        String expectedOutput1 = "\n-----Cleanings-----\n" +
                "1/Jeff | Cleaning time: 5 mins.\n" +
                "2/Mason | Cleaning time: 5 mins.\n";
        String actualOutput1 = ScheduleFormatter.addCleanings(output, cleanings);
        assertEquals("Output for a non-empty cleaning list was not was not as expected. Check dashes, spaces, new lines and event for correctness.",
                expectedOutput1, actualOutput1);
    }



}