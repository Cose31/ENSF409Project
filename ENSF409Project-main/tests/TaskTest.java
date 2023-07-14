package edu.ucalgary.oop;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;
import java.util.*;

public class TaskTest
{
    //testing values for Task
    Task task = new Task(1, "Task 1", 40, 3);

    //Test of Task Constructor
    /**
     * Ensures that the constructor for a Task object does not instantiate a null object
     */
    @Test
    public void testTaskConstructor()
    {
        assertNotNull("Task constructor is working improperly, did not create the object.", task);
    }
    //Testing of getTaskID
    /**
     * Tests the getter method getTaskID to ensure that it returns the correct integer passed into the constructor for the shared test object task
     */
    @Test
    public void testGetTaskID()
    {
        assertEquals("Constructor or getter gave wrong TaskID", 1, task.getTaskID());
    }
    /**
     * Tests the getter method getDescription to ensure that it returns the correct String passed into the constructor for the shared test object task
     */
    //Testing of getDescription
    @Test
    public void testGetDescription()
    {
        assertEquals("Constructor or getter gave wrong description", "Task 1", task.getDescription());
    }

    //Testing of setDescription
    /**
     * Tests the setter method setDescription to ensure that it sets the correct integer passed into the method
     */
    @Test
    public void testSetDescription()
    {
        task.setDescription("Updated task description");
        assertEquals("Setter did not update description, or getter for description is not working properly with update", "Updated task description", task.getDescription());
    }

    //Testing of getDuration
    /**
     * Tests the getter method getDuration to ensure that it returns the correct int passed into the constructor for the shared test object task
     */
    @Test
    public void testGetDuration()
    {
        assertEquals("Constructor or getter gave wrong duration", 40, task.getDuration());
    }

    //Testing of SetDuration
    @Test
    /**
     * Tests the setter method setDuration to ensure that it sets the correct integer passed into the method
    */
    public void testSetDuration()
    {
        task.setDuration(30);
        assertEquals("Setter did not update duration properly, or getter for duration is not working properly with update", 30, task.getDuration());
    }

    //Testing of GetTimeWindow
    @Test
    /**
     * Tests the getter method getDuration to ensure that it returns the correct int passed into the constructor for the shared test object task
     */
    public void testGetTimeWindow()
    {
        assertEquals("Constructor or getter gave wrong TimeWindow", 3, task.getTimeWindow());
    }

    //Testing of setTimeWindow
    /**
     * Tests the setter method setDuration to ensure that it sets the correct integer passed into the method
    */
    @Test
    public void testSetTimeWindow()
    {
        task.setTimeWindow(2);
        assertEquals("Setter did not update TimeWindow correctly, or getter for timewindow is not working properly with update", 2, task.getTimeWindow());
    }

    //TaskList Testing begins here

    //Test Constructor for TaskList
    /**
     * Ensures that the constructor for TaskList does not instantiate a null object
     */
    @Test
    public void testTaskListConstructor()
    {
        TaskList taskList = new TaskList();
        assertNotNull("Tasklist object not created.", taskList);
    }

    //Test getTaskList
    /**
    * Tests the getter method getTaskList to ensure that the ArrayList<Task> is returns is not null
    */
    @Test
    public void testGetTaskList()
    {
        TaskList taskList = new TaskList();
        ArrayList<Task> tasks = taskList.getList();
        assertNotNull("TaskList method getTaskList returned null", tasks);
    }

    //Test addTask
    /**
     * Tests the method addTask to ensure that a task can be added to the ArrayList of Tasks within a TaskList object.
     */
    @Test
    public void testAddTask()
    {
        TaskList taskList = new TaskList();
        Task task = new Task(1, "Task 1", 40, 3);
        taskList.addTask(task);
        ArrayList<Task> tasks = taskList.getList();
        assertEquals("AddTask did not add the correct taskID.", 1, tasks.get(0).getTaskID());
        assertEquals("AddTask did not add the correct description.", "Task 1", tasks.get(0).getDescription());
        assertEquals("AddTask did not add the correct duration.", 40, tasks.get(0).getDuration());
        assertEquals("AddTask did not add the correct TimeWindow.", 3, tasks.get(0).getTimeWindow());
    }

    //Test clearList
    /**
     * Tests the method clearList to ensure that the ArrayList of Tasks within a TaskList object can be emptied
     */
    @Test
    public void testClearList()
    {
        TaskList taskList = new TaskList();
        Task task = new Task(1, "Task 1", 40, 3);
        taskList.addTask(task);
        taskList.clearList();
        ArrayList<Task> tasks = taskList.getList();
        assertTrue("Task List is not empty, clearList did not clear properly.", tasks.isEmpty());
    }
}