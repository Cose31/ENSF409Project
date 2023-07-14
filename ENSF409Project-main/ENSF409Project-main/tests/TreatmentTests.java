package edu.ucalgary.oop;

import org.junit.Test;
import org.junit.Assert;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TreatmentTests {

    // create "expected value" variables for test comparison
    int expectedTreatmentId = 1;

    int expectedAnimalId1 = 12345;
    String expectedAnimalName1 = "Ricky Bobby";
    Animal expectedAnimal1 = new Animal(expectedAnimalId1, expectedAnimalName1);
    int expectedAnimalId2 = 54321;
    String expectedAnimalName2 = "Bobby Ricky";
    Animal expectedAnimal2 = new Animal(expectedAnimalId2, expectedAnimalName2);

    int expectedTaskId1 = 12345;
    String expectedTaskDescription1 = "bone-in pizza";
    int expectedTaskDuration1 = 7;
    int expectedTaskTimeWindow1 = 24;
    Task expectedTask1 = new Task(expectedTaskId1, expectedTaskDescription1, expectedTaskDuration1, expectedTaskTimeWindow1);
    int expectedTaskId2 = 54321;
    String expectedTaskDescription2 = "boneless pizza";
    int expectedTaskDuration2 = 11;
    int expectedTaskTimeWindow2 = 42;
    Task expectedTask2 = new Task(expectedTaskId2, expectedTaskDescription2, expectedTaskDuration2, expectedTaskTimeWindow2);

    int expectedStartHour1 = 12;
    Treatment sharedTestTreatment1 = new Treatment(expectedTreatmentId, expectedAnimal1, expectedTask1, expectedStartHour1);
    int expectedStartHour2 = 11;
    Treatment sharedTestTreatment2 = new Treatment(expectedTreatmentId, expectedAnimal2, expectedTask2, expectedStartHour2);

    // blank constructor
    public TreatmentTests() {}

    // generic Treatment tests

    // test the constructor for Treatment
    /**
     * Ensures the Treatment constructor does not instantiate a null object
     */
    @Test
    public void testTreatmentConstructor()
    {
        Treatment treatmentTest = new Treatment(1, new Animal(12345, "Elvis Presley"), new Task(23451, "marinara sauce", 3, 4), 5);
        assertNotNull("Treatment constructor did not create an object.", treatmentTest);
    }

    /**
     * Tests the getter method getAnimal within Treatment to ensure that it does not return a null Animal object
     */
    @Test
    public void testGetAnimal()
    {
        Animal myAnimal = sharedTestTreatment1.getAnimal();
        assertNotNull("getAnimal() did not return an object of type Animal.", myAnimal);
    }

    /**
     * Tests the getter method getTask within Treatment to ensure that it does not return a null Task object
     */
    @Test
    public void testGetTask()
    {
        Task myTask = sharedTestTreatment1.getTask();
        assertNotNull("getTask() did not return an object of type Task.", myTask);
    }

    // test getStartHour
    /**
     * Tests the getter method getStartHour within Treatment to ensure that it returns the correct integer given the test object being used
     */
    @Test
    public void testGetStartHour()
    {
        int actualStartHour1 = sharedTestTreatment1.getStartHour();
        assertEquals("Actual value of startHour does not match expected value.", expectedStartHour1, actualStartHour1);
    }

    // test setStartHour()
    /**
     * Tests the setter method getStartHour within Treatment to ensure that it sets the correct integer given the test object being used
     */
    @Test
    public void testSetStartHour()
    {
        sharedTestTreatment1.setStartHour(expectedStartHour2);
        int actualStartHour2 = sharedTestTreatment1.getStartHour();
        assertEquals("Final value (post-setStartHour() call) of startHour does not match expected value.", expectedStartHour2, actualStartHour2);
    }

    // create "expected value" variables for test comparison
    Animal testAnimal = new Animal(12345, "Dave Chappelle");
    Task testTask = new Task(54321, "do a barrel roll", 30, 4);
    Treatment testTreatment = new Treatment(expectedTreatmentId, testAnimal, testTask, 1);
    TreatmentList testTreatmentList = new TreatmentList();

    // generic TreatmentList tests

    // test the constructor for TreatmentList
    /**
     * Ensures that the constructor for TreatmentList does not instantiate a null object
     */
    @Test
    public void testTreatmentListConstructor()
    {
        testTreatmentList = new TreatmentList();
        assertNotNull("TreatmentList constructor did not create an object.", testTreatmentList);
    }

    /**
     * Tests the getter method getTreatmentList within Treatment to ensure that it does not return a null ArrayList<Treatment> object
     */
    @Test
    public void testGetTreatmentList()
    {
        ArrayList<Treatment> testArrayList = testTreatmentList.getList();
        assertNotNull("TreatmentList.getTreatmentList() did not successfully return an object of type ArrayList<Treatment>", testArrayList);
    }

    // test addTreatment
    @Test
    /**
     * Tests the method addTreatment to ensure that a Treatment object can be added to the ArrayList of Treatments within a TreatentList object
     */
    public void testAddTreatment()
    {
        ArrayList<Treatment> testArrayList = testTreatmentList.getList();
        assertTrue("ArrayList should be empty until TreatmentList.addTreatment() is called, but is not.",testArrayList.isEmpty());

        testTreatmentList.addTreatment(testTreatment);
        testArrayList = testTreatmentList.getList();
        assertFalse("testTreatmentList should contain one Treatment object, but is empty.", testArrayList.isEmpty());
    }

    // test clearList
    /**
     * Tests the method clearList to ensure that the ArrayList of Treatments within a TreatmentList object can be emptied
     */
    @Test
    public void testClearList()
    {
        testTreatmentList.addTreatment(new Treatment(1,testAnimal, testTask, 15));
        ArrayList<Treatment> testArrayList = testTreatmentList.getList();
        assertFalse("testTreatmentList should contain Treatment object(s), but is empty.", testArrayList.isEmpty());

        testTreatmentList.clearList();
        testArrayList = testTreatmentList.getList();
        assertTrue("testTreatmentList should be empty, but contains Treatment object(s).", testArrayList.isEmpty());
    }
}
