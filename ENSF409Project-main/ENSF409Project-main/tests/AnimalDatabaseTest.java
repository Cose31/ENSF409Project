package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;
import java.sql.SQLException;


public class AnimalDatabaseTest {
    private AnimalDatabase animalDatabase;

    /**
     * Sets up the connection to the database
     * @throws SQLException
     */
    @Before
    public void setUp() throws SQLException
    {
        animalDatabase = new AnimalDatabase();
        animalDatabase.connectDataBase();
    }

    /**
     * disconnects from the database
     * @throws SQLException
     */
    @After
    public void tearDown() throws SQLException
    {
        animalDatabase.closeDataBase();
    }

    /**
     * Tests to see that the list of Animals is not empty after extracting animals from the database
     * @throws SQLException
     */
    @Test
    public void testExtractAnimals() throws SQLException
    {
        AnimalList animalList = animalDatabase.extractAnimals();
        assertTrue("Animals were not extracted successfully, size of the list is still zero.",animalList.getList().size() > 0);
    }

    /**
     * Tests to see that the list of Tasks is not empty after extracting tasks from the database
     * @throws SQLException
     */
    @Test
    public void testExtractTasks() throws SQLException
    {
        TaskList taskList = animalDatabase.extractTasks();
        assertTrue("Tasks were not extracted successfully, size of the list is still zero. ",taskList.getList().size() > 0);
    }

    /**
     * Tests to see that the list of Treatments is not empty after extracting treatments from the database
     * @throws SQLException
     */
    @Test
    public void testExtractTreatments() throws SQLException
    {
        AnimalList animalList = animalDatabase.extractAnimals();
        TaskList taskList = animalDatabase.extractTasks();
        TreatmentList treatmentList = animalDatabase.extractTreatments(animalList, taskList);
        assertTrue("Treatments were not extracted successfully, size of the list is still zero.",treatmentList.getList().size() > 0);
    }
}
