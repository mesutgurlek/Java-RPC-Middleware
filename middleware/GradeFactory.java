/*
 * GradeFactory Created on Nov 21, 2004 at 2:53:59 AM 
 * Nathan Balon 
 * University of Michigan Dearborn 
 * CIS 578 Advanced Operating Systems
 */
package nathan.middleware;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * GradeFactory creates new instances of grade objects.  To be used
 * by the client to.  The new grades can then be sent to the remote
 * objects on the server.
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 */

public class GradeFactory {

    /**
     *  Creates a new instance of GradeFactory.
     */
    protected GradeFactory() {
        console = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Display the menu of possilbe choices.
     */
    protected void menu() {
        System.out.println("\tGrade Menu");
        System.out.println("-----------------------------");
        System.out.println("1 - Set grade I");
        System.out.println("2 - Set grade II");
        System.out.println("3 - Set grade III");
        System.out.println("4 - Display grades");
        System.out.println("5 - Done");
        System.out.print("$ ");
    }

    /**
     * Get the users input from the console.
     * @return char The letter grade.
     */
    protected char getLetterGrade() {
        char grade = ' ';
        String input = null;
        boolean validInput = false;
        try {
            while (!validInput) {
                System.out.print("Enter letter grade: ");
                input = console.readLine();
                input = input.toUpperCase();
                Pattern p = Pattern.compile("[A-E]");
                Matcher m = p.matcher(input);
                boolean isLetter = m.matches();
                if (isLetter) {
                    grade = input.charAt(0);
                    validInput = true;
                }
                else {
                    System.err.println("Invalid input A-E grade range");
                }
            }
        }
        catch (Exception e) {
            System.err.println("Error reading grade selection");
            e.printStackTrace();
        }
        return grade;
    }

    /**
     * Get the menu choice from the user.
     * @return int The menu selection.
     */
    protected int getMenuChoice(){
        String input = null;
        int choice = 0;
        boolean validInput = false;
        try {
            while (!validInput) {
                this.menu();
                input = console.readLine();
                // check that the input is a number
                Pattern p = Pattern.compile("[1-5]");
                Matcher m = p.matcher(input);
                boolean isNumber = m.matches();
                if (isNumber) {
                    choice = Integer.parseInt(input);
                    validInput = true;
                }
                else {
                    System.err.println("Invalid input");
                }
            }
        }
        catch (Exception e) {
            System.err.println("Error reading grade selection");
            e.printStackTrace();
        }
        return choice;
    }
    
    /**
     * Initialize the grade.
     * @param grade Returns the grade.
     */
    protected void initializeGrade(GradeImpl grade) {
        boolean done = false;
        while (!done){           
            int choice = this.getMenuChoice();
            char letterGrade = ' ';
            switch (choice) {
            case 1:
                letterGrade = this.getLetterGrade();
                System.out.println("letter: " + letterGrade);
                grade.setCourseIGrade(letterGrade);
                break;
            case 2:
                letterGrade = this.getLetterGrade();
                grade.setCourseIIGrade(letterGrade);
                break;
            case 3:
                letterGrade = this.getLetterGrade();
                grade.setCourseIIIGrade(letterGrade);
                break;
            case 4:
                System.out.println(grade);
                break;
            case 5:
                System.out.println("Done entering grades");
                done = true;
                break;
            default:
                System.out.println("Invalid input");
            }
        }
    }

    /**
     * Creates a new grade object to be used.
     * @return GradeImpl The new grade.
     */
    public static GradeImpl create() {
        GradeImpl grade = new GradeImpl();
        GradeFactory g = new GradeFactory();
        g.initializeGrade(grade);
        return grade;
    }

    private BufferedReader console;  		// reads user input from the console

}