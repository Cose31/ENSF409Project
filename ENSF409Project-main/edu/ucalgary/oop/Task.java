/**
 * @filename    Task.java
 * @author 	    ENSF 409 Project Group 5
 * @members     Caleb Jacobs, Ryan Pryor, Jacob Koep, Max Trainor
 * @version     1.2
 * @since  	    1.0
 */

package edu.ucalgary.oop;

/**
 * Contains relevant information pertaining to a single Task
 * within the database, i.e. the Task's ID, a description, 
 * time window to complete and duration to complete.
 */
public class Task {
    
    /* MEMBERS */
    private final int TASKID;
    private String description;
    private int duration;
    private int timeWindow;
    
    /* CONSTRUCTOR */
    public Task(int taskID, String description, int duration, int timeWindow) {
        this.TASKID = taskID;
        this.description = description;
        this.duration = duration;
        this.timeWindow = timeWindow;
    }
    
    /* GETTERS */
    public int getTaskID() { return this.TASKID; }
    public String getDescription() { return this.description; }
    public int getDuration() { return this.duration; }
    public int getTimeWindow() { return this.timeWindow; }

    /* SETTERS */
    public void setDescription(String desc) { this.description = desc; }
    public void setDuration(int dur) { this.duration = dur; }
    public void setTimeWindow(int tw) { this.timeWindow = tw; }
}
