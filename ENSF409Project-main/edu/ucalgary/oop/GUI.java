/**
 * @filename    GUI.java
 * @author 	    ENSF 409 Project Group 5
 * @members     Caleb Jacobs, Ryan Pryor, Jacob Koep, Max Trainor
 * @version     1.6
 * @since  	    1.0
 */

package edu.ucalgary.oop;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;

/**
 * class responsible for instantiating and maintaining the graphical user interface (gui) for the scheduling program
 */
public class GUI {

	/* MEMBERS */
	private JFrame frame;
	private boolean success = false;
	private boolean volunteersNeeded = false;
	private Schedule schedule = new Schedule();
	private boolean[] volunteersConfirmed;

	private ArrayList<Treatment> failedTreatments = new ArrayList<Treatment>();
	private ArrayList<Animal> failedFeedings = new ArrayList<Animal>();
	private ArrayList<Animal> failedCleanings = new ArrayList<Animal>();

	/* CONSTRUCTOR */
	public GUI() {
		frame = new JFrame("Animal Care Scheduler");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel homeScreenPanel = buildHomePanel();
		changePanel(homeScreenPanel);
		frame.setSize(850, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/* MAIN */
	public static void main(String[] args) {
		GUI gui = new GUI();
	}

	/* METHODS */
	/**
	 * creates the home screen panel that the user first sees upon starting the program
	 */
	private JPanel buildHomePanel() {
		JPanel homeScreenPanel = new JPanel();
		homeScreenPanel.setLayout(new BorderLayout());
		homeScreenPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 100, 100));
		JLabel welcomeLabel = new JLabel("Welcome to the animal care scheduler");//modify
		welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		JButton generateScheduleButton = new JButton("Generate Schedule");
		generateScheduleButton.setFont(new Font("Arial", Font.PLAIN, 40));
		generateScheduleButton.addActionListener(new GenerateScheduleListener());
		homeScreenPanel.add(welcomeLabel, BorderLayout.NORTH);
		homeScreenPanel.add(generateScheduleButton, BorderLayout.CENTER);
		return homeScreenPanel;
	}

	/**
	 * creates the confirm button and user prompt to allow the user to confirm that a volunteer
	 * should be added to a time slot
	 */
	private JScrollPane buildVolunteerConfirmationPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		ArrayList<Boolean> extraVolunteersNeeded = schedule.getBackupRequired();
		volunteersConfirmed = new boolean[extraVolunteersNeeded.size()];
		for(int i = 0; i < extraVolunteersNeeded.size(); i++) {
			if(extraVolunteersNeeded.get(i)) {
				JPanel row = new JPanel();
				JLabel label = new JLabel("Backup Volunteer needed at " + i + ":00. Confirm:");
				JButton button = new JButton("Confirm");
				button.addActionListener(new ConfirmListener(i, button));
				row.add(label);
				row.add(button);
				panel.add(row);
			} else {
				volunteersConfirmed[i] = true;
			}
		}

		JScrollPane scrollPane = new JScrollPane(panel);

		return scrollPane;
	}

	/**
	 * creates a JPanel indicating that the schedule build was a success
	 * 
	 * @param needExtraVolunteers	boolean indicating whether a time window needs backup volunteers
	 * @return						returns a created JPanel for the successful creation window
	 */
	private JPanel buildSuccessPanel(boolean needExtraVolunteers) {
		JPanel successPanel = new JPanel();
		successPanel.setLayout(new BoxLayout(successPanel, BoxLayout.Y_AXIS));
		successPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
		JLabel successLabel = new JLabel("Success! The schedule has been created and saved to schedule.txt");
		successLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		JScrollPane schedule = new JScrollPane(buildSchedulePanel());
		schedule.setPreferredSize(new Dimension(400, 400));
		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ResetListener());
		successPanel.add(successLabel);
		successPanel.add(schedule);
		successPanel.add(buildVolunteerConfirmationPanel());
		successPanel.add(resetButton);
		return successPanel;
	}

	/**
	 * creates a JPanel containing the created schedule
	 * 
	 * @return	returns a created JPanel with the created schedule
	 */
	private JPanel buildSchedulePanel() {
		JPanel schedulePanel = new JPanel();
		schedulePanel.setLayout(new BoxLayout(schedulePanel, BoxLayout.Y_AXIS));
		String output = ScheduleFormatter.formattedOutput(schedule.getTreatmentSchedule(), 
		schedule.getFeedingSchedule(), 
		schedule.getCleaningSchedule(), 
		schedule.getBackupRequired());

		String[] lineString = output.split("\n");

		for (int i = 0; i < lineString.length; i++) {
			schedulePanel.add(new JLabel(lineString[i]));
		}
		
		return schedulePanel;
	}

	/**
	 * creates a JPanel indicated that the schedule was unable to build successfully
	 * 
	 * @return	returns a created JPanel indicating a build failure
	 */
	private JPanel buildfailurePanel() {
		JPanel failurePanel = new JPanel();
		failurePanel.setLayout(new BoxLayout(failurePanel, BoxLayout.Y_AXIS));
		failurePanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
		JLabel failureLabel1 = new JLabel("Failed. Unable to create schedule based on current inputs.");
		failureLabel1.setFont(new Font("Arial", Font.PLAIN, 20));
		JLabel failureLabel2 = new JLabel("Modify start times below and try again");
		failureLabel2.setFont(new Font("Arial", Font.PLAIN, 20));
		JPanel labelsPanel = new JPanel();
		labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
		labelsPanel.add(failureLabel1);
		labelsPanel.add(failureLabel2);
		failurePanel.add(labelsPanel);
		
		JPanel treatmentsPanel = new JPanel();
		treatmentsPanel.setLayout(new BoxLayout(treatmentsPanel, BoxLayout.Y_AXIS));
		for(Treatment t : failedTreatments) {
			treatmentsPanel.add(buildModifyStartTimeRow(t));
		}
		JScrollPane scrollPane = new JScrollPane(treatmentsPanel);
		failurePanel.add(scrollPane);
		if(failedCleanings.size() + failedFeedings.size() != 0) {
			JPanel otherfailuresPanel = new JPanel();
			otherfailuresPanel.setLayout(new BoxLayout(otherfailuresPanel, BoxLayout.Y_AXIS));
			JLabel otherfailuresLabel = new JLabel("There were conflicts with the following tasks:");
			otherfailuresPanel.add(otherfailuresLabel);
			for(Animal a : failedCleanings) {
				JPanel row = new JPanel();
				row.add(new JLabel(a.getName() + "'s cleaning"));
				otherfailuresPanel.add(row);
			}
			for(Animal a : failedFeedings) {
				JPanel row = new JPanel();
				row.add(new JLabel(a.getName() + "'s feeding"));
				otherfailuresPanel.add(row);
			}
			JScrollPane otherfailuresScrollPane = new JScrollPane(otherfailuresPanel);
			failurePanel.add(otherfailuresScrollPane);
		}
		JButton retryButton = new JButton("Retry");
		retryButton.setFont(new Font("Arial", Font.PLAIN, 20));
		retryButton.addActionListener(new GenerateScheduleListener());
		failurePanel.add(retryButton);

		return failurePanel;
	}

	/**
	 * creates a JPanel prompting the user to select an alternate start hour for a Treatment to modify it in the database
	 * 
	 * @param treatment	returns a created JPanel with dropdown menus for the user to select an alternate start time 
	 */
	private JPanel buildModifyStartTimeRow(Treatment treatment) {
		JPanel row = new JPanel();
		Integer[] options = new Integer[23];
		for(int i = 0; i < 24; i++) {
			if(i < treatment.getStartHour()) {
				options[i] = i;
			} else if(i > treatment.getStartHour()) {
				options[i - 1] = i;
			}
		}
		JLabel descriptionLabel = new JLabel(treatment.getAnimal().getName()+ ": "+treatment.getTask().getDescription() + ": (Start Hour: " +treatment.getStartHour() + ":00)");
		descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		JComboBox comboBox = new JComboBox<Integer>(options);
		comboBox.setFont(new Font("Arial", Font.PLAIN, 15));
		JButton addButton = new JButton("Add");
		addButton.setFont(new Font("Arial", Font.PLAIN, 15));
		addButton.addActionListener(new AddListener(treatment, comboBox));
		row.add(descriptionLabel);
		row.add(comboBox);
		row.add(addButton);
		return row;
	}

	/**
	 * replaces the currently displayed GUI JPanel with panel
	 * 
	 * @param panel	new panel to switch the GUI to
	 */
	public void changePanel(JPanel panel) {
		frame.getContentPane().removeAll();
		frame.getContentPane().add(panel);
		frame.revalidate();
	}

	/**
	 * Runs any time generate schedule or retry is clicked, trys to:
	 * 1. Parse the database
	 * 2. Schedule Treatments
	 * 3. Schedule Feedings
	 * 4. Schedule Cleanings
	 * 
	 * The schedule operations return ArrayLists of any Treatments that could not be scheduled
	 * or animals that could not be scheduled for feeding or cleaning.
	 * 
	 * @return two booleans in an array which indicate if the scheduler was sucessful, and if backup volunteers are needed, respectively.
	 */
	public boolean[] testingGenerateSchedule() {
		try {
			schedule.parseDataBase();
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "A problem occured while trying to read the EWR Database:\n" + e.getMessage());
		}
		volunteersNeeded = false;
		schedule.clearSchedule();

		failedTreatments = schedule.scheduleTreatments();
		failedFeedings = schedule.scheduleFeeding();
		failedCleanings = schedule.scheduleCleaning();

		success = failedTreatments.size() == 0 && failedFeedings.size() == 0 && failedCleanings.size() == 0;

		if (schedule.getBackupRequired().contains(true)) {
			volunteersNeeded = true;
		}

		System.out.println("Extra volunteers needed: " + schedule.getBackupRequired());

		System.out.println(ScheduleFormatter.formattedOutput(schedule.getTreatmentSchedule(), 
                                                            schedule.getFeedingSchedule(), 
                                                            schedule.getCleaningSchedule(), 
                                                            schedule.getBackupRequired()
                                                            ));
		try {
			PrintWriter out = new PrintWriter("schedule.txt");
			out.println(ScheduleFormatter.formattedOutput(schedule.getTreatmentSchedule(), 
			schedule.getFeedingSchedule(), 
			schedule.getCleaningSchedule(), 
			schedule.getBackupRequired()));
			out.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "A problem occured while trying to output the schedule to schedule.txt: " + e.getMessage());
		}

		return new boolean[]{success, volunteersNeeded};
	}


	/**
	 * Custom action listener for the generate schedule and retry buttons.
	 * Calls the above method testingGenerateSchedule, and based on the result
	 * decides which panel to load next.
	 */
	public class GenerateScheduleListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Generating schedule now");
			boolean[] result = testingGenerateSchedule();
			boolean success = result[0];
			boolean needExtraVolunteers = result[1];
			System.out.println("Success: " + success + " need extra volunteers: " + needExtraVolunteers);
			if(success) {
				JPanel successPanel = buildSuccessPanel(needExtraVolunteers);
				changePanel(successPanel);
			} else {
				JPanel failurePanel = buildfailurePanel();
				changePanel(failurePanel);
			}
		}
	}

	/**
	 * Custom action listener for the reset button.
	 * Will not change panels until any backup volunteers have been confirmed
	 */
	public class ResetListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			for(boolean ready : volunteersConfirmed) {
				if(!ready) {
					JOptionPane.showMessageDialog(null, "Cannot Reset Scheduler Until all Backup Volunteer Times Have Been Confirmed.");
					System.out.println("Not ready to exit, must confirm all extra volunteers first");
					return;
				}
			}
			System.out.println("Resetting now");
			JPanel homeScreenPanel = buildHomePanel();
			changePanel(homeScreenPanel);
		}
	}

	/**
	 * Custom action listener for the "add" button for modifying a start time of a treatment
	 * Invokes a try-catch block to modify the SQL database, and displays a message dialog if it cannot.
	 */
	public class AddListener implements ActionListener {
		private Treatment treatment;
		private JComboBox comboBox;

		public AddListener(Treatment treatment, JComboBox comboBox) {
			this.treatment = treatment;
			this.comboBox = comboBox;
		}

		public void actionPerformed(ActionEvent event) {
			System.out.println("Adding hour " + (Integer) comboBox.getSelectedItem() + " to " + treatment.getTask().getDescription());
			try {
				schedule.getAnimalDatabase().modifyTreatmentStartTime(treatment, (Integer) comboBox.getSelectedItem());
			}
			catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Could not modify start time: \n" + e.getMessage());
			}
		}
	}

	/**
	 * Custom action listener for the confirm buttons for confirming backup volunteers
	 * Sets a boolean value in a member variable that contains n booleans where n is the number
	 * of schedule time slots where a backup is needed.
	 */
	public class ConfirmListener implements ActionListener {
		private int index;
		private JButton button;

		public ConfirmListener(int i, JButton b) {
			index = i;
			button = b;
		}

		public void actionPerformed(ActionEvent event) {
			volunteersConfirmed[index] = true;
			button.setText("Confirmed");
		}
	}
}