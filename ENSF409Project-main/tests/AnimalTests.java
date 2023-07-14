package edu.ucalgary.oop;

import edu.ucalgary.oop.animals.*;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;
import java.util.*;

public class AnimalTests {

	// Values used for all tests involving Animal
	int expectedID = 12345;
	String expectedName = "Bob";
	Feeding expectedFeeding = new Feeding(new int[]{1, 2, 3}, 5, 10);
	boolean isOrphan = false;

	Animal sharedTestAnimal = new Animal(expectedID, expectedName);

	public AnimalTests() {}

	// Generic Animal Tests:

	/**
	 * Tests the basic constructor for the Animal class - ensures the created object is not null
	 */
	@Test
	public void testAnimalConstructor() {
		Animal animal = new Animal(123, "Bob");
		assertNotNull("Animal constructor did not create an object", animal);
	}

	// Test getID
	/**
	 * Tests the getter method for the animal id within an animal object
	 */
	@Test
	public void testAnimalGetID() {
		int actualID = sharedTestAnimal.getAnimalID();
		assertEquals("constructor or getter gave wrong value for ID", expectedID, actualID);
	}

	// Test getName
	/**
	 * Tests the getter method for the animals name within an animal object
	 */
	@Test
	public void testAnimalGetName() {
		String actualName = sharedTestAnimal.getName();
		assertEquals("constructor or getter gave wrong value for name", expectedName, actualName);
	}

	// Test setName
	/**
	 * Tests the setter method for the animals name within an animal object
	 */
	@Test
	public void testAnimalSetName() {
		Animal testAnimal = new Animal(123, "Bob");
		String expectedNewName = "Alice";
		testAnimal.setName(expectedNewName);
		String actualNewName = testAnimal.getName();
		assertEquals("setter or getter gave wrong value for name", expectedNewName, actualNewName);
	}

	// Test getIsOrphan()
	/**
	 * Tests the getter method for the boolean indicating whether or not an Animal object is an orphan
	 */
	@Test
	public void testGetIsOrphan() {
		Animal orphanedAnimal1 = new Animal(00000, "Joe, Bob");
		Animal orphanedAnimal2 = new Animal(11111, "Joe and Bob");
		Animal nonOrphanedAnimal = new Animal(22222, "Larry");

		assertTrue("getIsOrphan() did not return true with names seperated by a comma.",orphanedAnimal1.getIsOrphan());
		assertTrue("getIsOrphan() did not return true with names seperated by an 'and'.", orphanedAnimal2.getIsOrphan());
		assertTrue("getIsOrphan() did not return false for a non-orphaned animal.", !nonOrphanedAnimal.getIsOrphan());
	}

	// Test getFeeding() (only test existence, values for Feeding tested in FeedingTests.java)
	/**
	 * Tests both the setter and the getter for the Feeding object member of Animal
	 */
	@Test
	public void testGetFeeding() {
		sharedTestAnimal.setFeeding(expectedFeeding);
		assertNotNull("getFeeding() did not return a Feeding object.", sharedTestAnimal.getFeeding());
	}

	// Test getCleaning() (only test existence, values for Cleaning tested in CleaningTests.java)
	/**
	 * Tests the getter method for the animals cleaning duration within an animal object
	 */
	@Test
	public void testGetCleaning() {
		sharedTestAnimal.getCleaningDuration();
		assertNotNull("getCleaning() did not return the Cleaning duration.", sharedTestAnimal.getCleaningDuration());
	}

	// Other getters are for properties that aren't set on a generic Animal

	// Coyote Tests:

	// Test default feeding
	/**
	 * Tests that the correct default member values are instantiated within the Feeding object inherited via the Animal parent object to coyote
	 */
	@Test
	public void testCoyoteDefaultFeeding() {
		Coyote coyote = new Coyote(123, "Bob");
		Feeding expectedFeeding = new Feeding(new int[]{19, 20, 21}, 5, 10);
		Feeding actualFeeding = coyote.getFeeding();
		assertArrayEquals("Coyote initialized with feeding with wrong timeslots", expectedFeeding.getTimeSlots(), actualFeeding.getTimeSlots());
		assertEquals("Coyote initialized with feeding with wrong duration", expectedFeeding.getDuration(), actualFeeding.getDuration());
		assertEquals("Coyote initialized with feeding with wrong prep time", expectedFeeding.getPrepTime(), actualFeeding.getPrepTime());
	}

	// Test default cleaning
	/**
	 * Tests that the correct default value for the member variable cleaningDuration inherited via the Animal parent object to coyote is instantiated
	 */
	@Test
	public void testCoyoteDefaultCleaning() {
		Coyote coyote = new Coyote(123, "Bob");
		int actualCleaning = coyote.getCleaningDuration();
		assertEquals("Coyote initialized with cleaning with wrong duration", 5, actualCleaning);
	}

	// Fox Tests:

	// Test default feeding
	/**
	 * Tests that the correct default values is instantiated for the member variable cleaningDuration inherited via the Animal parent object to fox
	 */
	@Test
	public void testFoxDefaultFeeding() {
		Fox fox = new Fox(123, "Bob");
		Feeding expectedFeeding = new Feeding(new int[]{0, 1, 2}, 5, 5);
		Feeding actualFeeding = fox.getFeeding();
		assertArrayEquals("Fox initialized with feeding with wrong timeslots", expectedFeeding.getTimeSlots(), actualFeeding.getTimeSlots());
		assertEquals("Fox initialized with feeding with wrong duration", expectedFeeding.getDuration(), actualFeeding.getDuration());
		assertEquals("Fox initialized with feeding with wrong prep time", expectedFeeding.getPrepTime(), actualFeeding.getPrepTime());
	}

	// Test default cleaning
	// Test default cleaning
	/**
	 * Tests that the correct default value for the member variable cleaningDuration inherited via the Animal parent object to fox is instantiated
	 */
	@Test
	public void testFoxDefaultCleaning()
	{
		Fox fox = new Fox(123, "Bob");
		int actualCleaning = fox.getCleaningDuration();
		assertEquals("Fox initialized with cleaning with wrong duration", 5, actualCleaning);
	}

	// Porcupine Tests:

	// Test default feeding
	/**
	 * Tests that the correct default member values are instantiated within the Feeding object inherited via the Animal parent object to porcupine
	 */
	@Test
	public void testPorcupineDefaultFeeding() {
		Porcupine porcupine = new Porcupine(123, "Bob");
		Feeding expectedFeeding = new Feeding(new int[]{19, 20, 21}, 5, 0);
		Feeding actualFeeding = porcupine.getFeeding();
		assertArrayEquals("Porcupine initialized with feeding with wrong timeslots", expectedFeeding.getTimeSlots(), actualFeeding.getTimeSlots());
		assertEquals("Porcupine initialized with feeding with wrong duration", expectedFeeding.getDuration(), actualFeeding.getDuration());
		assertEquals("Porcupine initialized with feeding with wrong prep time", expectedFeeding.getPrepTime(), actualFeeding.getPrepTime());
	}

	// Test default cleaning
	// Test default cleaning
	/**
	 * Tests that the correct default value for the member variable cleaningDuration inherited via the Animal parent object to porcupine is instantiated
	 */
	@Test
	public void testPorcupineDefaultCleaning() {
		Porcupine porcupine = new Porcupine(123, "Bob");
		int actualCleaning = porcupine.getCleaningDuration();
		assertEquals("Porcupine initialized with cleaning with wrong duration", 10, actualCleaning);
	}

	// Raccoon Tests:

	// Test default feeding
	/**
	 * Tests that the correct default member values are instantiated within the Feeding object inherited via the Animal parent object to raccoon
	 */
	@Test
	public void testRaccoonDefaultFeeding() {
		Raccoon raccoon = new Raccoon(123, "Bob");
		Feeding expectedFeeding = new Feeding(new int[]{0, 1, 2}, 5, 0);
		Feeding actualFeeding = raccoon.getFeeding();
		assertArrayEquals("Raccoon initialized with feeding with wrong timeslots", expectedFeeding.getTimeSlots(), actualFeeding.getTimeSlots());
		assertEquals("Raccoon initialized with feeding with wrong duration", expectedFeeding.getDuration(), actualFeeding.getDuration());
		assertEquals("Raccoon initialized with feeding with wrong prep time", expectedFeeding.getPrepTime(), actualFeeding.getPrepTime());
	}

	// Test default cleaning
	// Test default cleaning
	/**
	 * Tests that the correct default value for the member variable cleaningDuration inherited via the Animal parent object to raccoon is instantiated
	 */
	@Test
	public void testRaccoonDefaultCleaning() {
		Raccoon raccoon = new Raccoon(123, "Bob");
		int actualCleaning = raccoon.getCleaningDuration();
		assertEquals("Raccoon initialized with cleaning with wrong duration", 5, actualCleaning);
	}

	// Beaver Tests:

	// Test default feeding
	/**
	 * Tests that the correct default member values are instantiated within the Feeding object inherited via the Animal parent object to beaver
	 */
	@Test
	public void testBeaverDefaultFeeding() {
		Beaver beaver = new Beaver(123, "Bob");
		Feeding expectedFeeding = new Feeding(new int[]{8, 9, 10}, 5, 0);
		Feeding actualFeeding = beaver.getFeeding();
		assertArrayEquals("Beaver initialized with feeding with wrong timeslots", expectedFeeding.getTimeSlots(), actualFeeding.getTimeSlots());
		assertEquals("Beaver initialized with feeding with wrong duration", expectedFeeding.getDuration(), actualFeeding.getDuration());
		assertEquals("Beaver initialized with feeding with wrong prep time", expectedFeeding.getPrepTime(), actualFeeding.getPrepTime());
	}

	// Test default cleaning
	// Test default cleaning
	/**
	 * Tests that the correct default value for the member variable cleaningDuration inherited via the Animal parent object to beaver is instantiated
	 */
	@Test
	public void testBeaverDefaultCleaning() {
		Beaver beaver = new Beaver(123, "Bob");
		int actualCleaning = beaver.getCleaningDuration();
		assertEquals("Beaver initialized with cleaning with wrong duration", 5, actualCleaning);
	}

	// AnimalList tests:

	// Test constructor
	/**
	 * Tests that the AnimalList constructor correctly creates an AnimalList object
	*/
	@Test
	public void testAnimalListConstructor() {
		AnimalList list = new AnimalList();
		assertNotNull("AnimalList constructor did not create an object", list);
	}

	/**
	 * Tests the getter method getList() to ensure it returns a non-null object
	 */
	// Test getAnimalList:
	@Test
	public void testAnimalListGetAnimalList() {
		AnimalList list = new AnimalList();
		ArrayList<Animal> arrayList = list.getList();
		assertNotNull("AnimalList getAnimalList returned null", arrayList);
	}

	// Test addAnimal
	/**
	 * Tests the method addAnimal in AnimalList to ensure that animals can be added to the ArrayList of Animals member
	 */
	@Test
	public void testAnimalListAddAnimal() {
		AnimalList list = new AnimalList();
		Beaver beaver = new Beaver(123, "Bob");
		list.addAnimal(beaver);
		Animal storedAnimal = list.getList().get(0);
		assertEquals("AnimalList AddAnimal did not add an animal with the correct ID", 123, storedAnimal.getAnimalID());
		assertEquals("AnimalList AddAnimal did not add an animal with the correct name", "Bob", storedAnimal.getName());
	}

	// Test clearList
	/**
	 * Test the mthod clearList in AnimalList to ensure that the ArrayList of Animals member can be emptied.
	 */
	@Test
	public void testAnimalListClearList() {
		AnimalList list = new AnimalList();
		Beaver beaver = new Beaver(123, "Bob");
		list.addAnimal(beaver);
		list.clearList();
		int expectedSize = 0;
		int actualSize = list.getList().size();
		assertEquals("AnimalList clearList did not clear the list", expectedSize, actualSize);
	}
}