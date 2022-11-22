import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Poised {

	// Create a Poised object for memory purposes, as well as making use of global variables
	// Initialize menu options
    public static String options;
    public static Scanner Choose = new Scanner(System.in);

    public static void main(String[] args) {

		// Create new instance of Poised object
        Poised p = new Poised();

		// Initialize project count variable
		// Name file path and initialize
        int pcnt;
        String pjnumData;
        String fileName = ("Task8/src/allprojects.txt");
        File projectFile = new File(fileName);
        boolean fileExists = projectFile.exists();

		// Call inputFileSize method to count each project from the file
		// Create a new instance of Project
        int pjnum = p.inputFileSize(fileName);
        Project[] myproject = new Project[pjnum];

        try {

			// Assign respective information to the Project and Person objects using split() and indexing
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            for (int i = 0; i < pjnum; i++) {
                String stringLine = reader.readLine();
                String[] splitdata = stringLine.split(",");

                myproject[i] = new Project();
                myproject[i].projectNumber = splitdata[0];
                myproject[i].projectName = splitdata[1];
                myproject[i].projectType = splitdata[2];
                myproject[i].physicalAddress = splitdata[3];
                myproject[i].ERF = splitdata[4];
                myproject[i].totalAmountCharged = splitdata[5];
                myproject[i].totalAmountToDate = splitdata[6];
                myproject[i].deadlineDate = splitdata[7];
                myproject[i].status = splitdata[8];

                myproject[i].architect.name = splitdata[9];
                myproject[i].architect.phone = splitdata[10];
                myproject[i].architect.email = splitdata[11];
                myproject[i].architect.physicalAddress = splitdata[12];

                myproject[i].contractor.name = splitdata[13];
                myproject[i].contractor.phone = splitdata[14];
                myproject[i].contractor.email = splitdata[15];
                myproject[i].contractor.physicalAddress = splitdata[16];

                myproject[i].customer.name = splitdata[17];
                myproject[i].customer.phone = splitdata[18];
                myproject[i].customer.email = splitdata[19];
                myproject[i].customer.physicalAddress = splitdata[20];

                if (myproject[i].projectName.equals("") || myproject[i].projectName.equals("null") || myproject[i].projectName.equals(" ")) {
                    String namestring = myproject[i].customer.name;
                    myproject[i].projectName = myproject[i].projectType + " " + namestring.substring(namestring.lastIndexOf(" ") + 1);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("File Size Error: " + e);
        }

		// Assign variable to user selection
        String opt = p.menuOption();

        while (!options.equals("9")) {


            switch (opt) {

				// If user selects 1, prompt accordingly
				// Create new instance of project if they choose to add one
				// Add Project object to Project[] array list at the correct position
                case "1":
                    int newarraysize = myproject.length;
                    pjnum = newarraysize + 1;
                    System.out.println("------------------ Add New Project ------------------");
                    Project[] newProject = Arrays.copyOf(myproject, pjnum);
                    myproject = newProject;
                    System.out.println("\n------------------ Please enter information for Project " + pjnum + " ------------------\n");
                    myproject[pjnum - 1] = new Project();
                    myproject[pjnum - 1].inputData();
                    myproject[pjnum - 1].outFileData();
                    break;

					// Display all projects
                case "2":
                    System.out.println("------------------ Viewing All Projects ------------------");
                    for (int i = 0; i < pjnum; i++) {
                        pcnt = i + 1;
                        System.out.println("------------------ Viewing Project " + pcnt + " ------------------");
                        myproject[i].displayData();
                        System.out.println(" ");
                    }
                    break;

					// Update date and assign to correct project in text file
                case "3":
                    System.out.println("------------------ Update Project Completion Date ------------------");
                    System.out.print("Enter project: ");
                    pjnumData = Choose.nextLine();
                    for (int i = 0; i < pjnum; i++) {
                        if (pjnumData.equals(myproject[i].getProjectNumber())) {
                            myproject[i].updateDate();
                        }
                    }
                    break;

				// Update balance and assign to correct project in text file
                case "4":
                    System.out.println("------------------ Update Project Balance ------------------");
                    System.out.print("Enter project: ");
                    pjnumData = Choose.nextLine();
                    for (int i = 0; i < pjnum; i++) {
                        if (pjnumData.equals(myproject[i].getProjectNumber())) {
                            myproject[i].updateAmount();
                        }
                    }
                    break;

					// Display incomplete projects
                case "5":
                    System.out.println("------------------ Viewing Incomplete Projects ------------------");
                    for (int i = 0; i < pjnum; i++) {
                        if (myproject[i].getProjectStatus().equals("Pending")) {
                            pcnt = i + 1;
                            System.out.println("------------------ Viewing Project " + pcnt + " ------------------");
                            myproject[i].displayData();
                            System.out.println(" ");
                        }
                    }
                    break;

					// Display overdue projects
                case "6":
                    System.out.println("------------------ Viewing Overdue Projects ------------------");
                    for (int i = 0; i < pjnum; i++) {
                        try {
                            if (new SimpleDateFormat("dd/MM/yyyy").parse(myproject[i].getDeadlineDate()).before(new Date())) {
                                pcnt = i + 1;
                                System.out.println("------------------ Viewing Project " + pcnt + " ------------------");
                                myproject[i].displayData();
                                System.out.println(" ");
                            }
                        } catch (Exception ex) {
                            System.out.println("Date format error");
                        }

                    }
                    break;

					// Search for a project using a for loop to run through array data
                case "7":
                    System.out.println("------------------ Search for a Project ------------------");
                    System.out.print("Enter Project Name or Number: ");
                    String projnumFindData = Choose.nextLine();
                    for (int i = 0; i < pjnum; i++) {
                        if (projnumFindData.equals(myproject[i].getProjectNumber()) || projnumFindData.equals(myproject[i].getProjectName()))
						{
                            myproject[i].displayData();
                        }
                    }
                    break;

					// Finalize project
				// Call outFileData() to write onto completed projects.txt
                case "8":
                    System.out.println("------------------ Finalise Project ------------------");
                    System.out.print("Enter project: ");
                    pjnumData = Choose.nextLine();
                    for (int i = 0; i < pjnum; i++) {
                        if (pjnumData.equals(myproject[i].getProjectNumber())) {
                            myproject[i].finaliseProject();
                            String outFileData = myproject[i].outFileData();
                            try {
                                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("src/completedprojects.txt", true)));
                                out.println(outFileData);
                                out.close();
                            } catch (IOException e) {
                                System.out.println("Error writing finalise project to file");
                            }
                        }
                    }
                    break;

					// Exit program
                case "9":
                    System.out.println("Option 9 to exit program");
                    break;

					// Use defensive handling should the user make an incorrect input
                default:
                    System.out.println("Invalid Option Entered");
            }

			// recall user selection
            opt = p.menuOption();
        }
    }

	// Create method for menu display
    public String menuOption() {
        System.out.println("---------------- Welcome to Poised Project Manager ----------------");
        System.out.print("\nWhat would you like to do?\n" +
                "1. Add New Project \n" +
                "2. Display All Projects \n" +
                "3. Update Project Completion Date \n" +
                "4. Update Project Balance  \n" +
                "5. Display Incomplete Projects \n" +
                "6. Display Overdue Projects \n" +
                "7. Search for a Project \n" +
                "8. Finalise Project \n" +
                "9. Exit Program \n\n" +
                "Enter selection below: \n");
        options = Choose.nextLine();
        return options;
    }

	// Create method to count lines from file to assign indexes to each project
    public int inputFileSize(String fn) {
        int lines = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fn));
            while (reader.readLine() != null) lines++;
            reader.close();
        } catch (IOException e) {
            System.out.println("File Size Error: " + e);
        }
        return lines;
    }
}