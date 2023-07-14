package edu.ucalgary.oop;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class FeedingTests {

    // create "expected value" variables for test comparison
    int[] expectedTimeSlots = new int[]{1, 2, 3};
    int expectedDuration = 10;
    int expectedPrepTime = 5;

    Feeding sharedTestFeeding = new Feeding(expectedTimeSlots, expectedDuration, expectedPrepTime);

    // blank constructor
    public FeedingTests() {}

    // generic Feeding tests

    // test the constructor for Feeding
    /**
     * Tests the Feeding constructor to ensure a null object is not instantiated
     */
    @Test
    public void testFeedingConstructor()
    {
        Feeding feedingTest = new Feeding(new int[]{1, 2, 3}, 10, 5);
        assertNotNull("Feeding constructor did not create an object.", feedingTest);
    }

    // test getTimeSlots
    /**
     * Tests the getter method getTimeSlots to ensure that the returned array is of the correct length
     */
    @Test
    public void testGetTimeSlotsA()
    {
        int[] actualTimeSlots = sharedTestFeeding.getTimeSlots();
        assertEquals("Actual timeSlots[] length does not match expected length.", expectedTimeSlots.length, actualTimeSlots.length);
    }

    /**
     * Test the getter method getTimeSlots to ensure that the returned array of ints is the same as was provided to the sharedTestFeeding constructor
     */
    @Test
    public void testGetTimeSlotsB()
    {
        int[] actualTimeSlots = sharedTestFeeding.getTimeSlots();
        assertArrayEquals("Actual value in timeSlots[] does not match expected value.", expectedTimeSlots, actualTimeSlots);
    }

    // test getDuration
    /**
     * Test the getter method getTimeSlots to ensure that the returned duration (int) is the same as was provided to the sharedTestFeeding constructor
     */
    @Test
    public void testGetDuration()
    {
        int actualDuration = sharedTestFeeding.getDuration();
        assertEquals("Actual DURATION value does not match expected value.", expectedDuration, actualDuration);
    }

    // test getPrepTime
    /**
     * Test the getter method getTimeSlots to ensure that the returned feeding preperation time (int) is the same as was provided to the sharedTestFeeding constructor
     */
    @Test
    public void testGetPrepTime()
    {
        int actualPrepTime = sharedTestFeeding.getPrepTime();
        assertEquals("Actual PREPTIME value does not match expected value.", expectedPrepTime, actualPrepTime);
    }
}