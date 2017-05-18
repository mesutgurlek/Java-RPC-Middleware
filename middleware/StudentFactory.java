/*
 * CreateStudent
 * Created on Nov 20, 2004 at 11:53:50 PM
 *
 * Nathan Balon
 * University of Michigan Dearborn
 * CIS 578
 * Advanced Operating Systems
 * 
 */
package nathan.middleware;

import java.io.*;
import java.util.regex.*;

/**
 * StudentFactory is used to create a new student by
 * reading in the users input from the terminal then 
 * set all of the attributes for the student.
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 *
 */

public class StudentFactory {
    
    private StudentFactory(){
        console = new BufferedReader(new InputStreamReader(System.in));
    }       
    
    // create a new student by calling the methods to set the attributes of the student
    private Student createStudent(Student student){
        System.out.println("Creating a new student");
        System.out.println("Enter the student information");
        this.setName(student);
        this.setAge(student);
        this.setProgram(student);
        this.setStudentNumber(student);
        this.setAddress(student);
        return student;
    }
    
    // set the name of the student
    private void setName(Student student){
        System.out.print("Enter a name: ");
        String name = null;
        try{
            name = console.readLine();
            student.setName(name);
        }
        catch(IOException e){
            System.err.println("Error reading name");
            e.printStackTrace();
        }
    }
    
    // set the students age
    private void setAge(Student student){
        String input = null;
        int age = 0;
        boolean validInput = false;
        try{
            // read input until a valid age is entered and then set the age
            while(!validInput){
                System.out.print("Enter an age: ");
                input = console.readLine();            
                // check that the input is a number
                Pattern p = Pattern.compile("[0-9]+");
                Matcher m = p.matcher(input);
                boolean isNumber = m.matches();
                if(isNumber){
                    age = Integer.parseInt(input);
                    validInput = true;
                }else{
                    System.out.println("Invalid input");
                }
           }
            student.setAge(age);            
        }
        catch(Exception e){
            System.err.println("Error reading age");
            e.printStackTrace();
        }        
    }
    
    // set the name of the program the student is in
    private void setProgram(Student student){
        System.out.print("Enter the program: ");
        String input = null;
        try{
            input = console.readLine();
            student.setProgram(input);            
        }
        catch(Exception e){
            System.err.println("Error reading program name");
            e.printStackTrace();
        }           
    }
    
    // set the student number
    private void setStudentNumber(Student student){
        System.out.print("Enter the student number: ");
        String input = null;
        try{
            input = console.readLine();
            student.setStudentNumber(input);            
        }
        catch(Exception e){
            System.err.println("Error reading program name");
            e.printStackTrace();
        }           
    }    
    
    // Set the address information for the student
    private void setAddress(Student student){
        System.out.print("Enter the address: ");
        String input = null;
        try{
            input = console.readLine();
            student.setAddress(input);            
        }
        catch(Exception e){
            System.err.println("Error reading program name");
            e.printStackTrace();
        }           
    }     
    
    /**
     * Creates a new Student.
     * @return Student the new student that was created
     */
    public static Student create(){
        Student student = new StudentImpl();
        StudentFactory createPerson = new StudentFactory();
        student = createPerson.createStudent(student);
        return student;
    }
    
    private BufferedReader console;

}
