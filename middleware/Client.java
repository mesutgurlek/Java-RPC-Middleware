/*
 * Client
 * Created on Nov 21, 2004 at 1:50:24 AM
 *
 * Nathan Balon
 * University of Michigan Dearborn
 * CIS 578
 * Advanced Operating Systems
 * 
 */
package nathan.middleware;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

/**
 * Client is the client portion of the RMI application.
 * The client application uses stub objects to invoke
 * methods on remote objects.  The application is used 
 * to access a remote student list.<br /><br />
 * 
 * To run the client: Client [Service Name] [Regisrty IP] [Registry Port].<br /><br />
 * 
 * Service Name is the name of the remote object which is wished to be used.<br />
 * Registry IP is the IP of the registry that is used to locate a 
 * reference to a remote object.<br />
 * Registry Port is the port number that the registry is listening on.<br />
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 */

public class Client {

    /**
     * Creates a new instance of the client object.
     */
    public Client(){
        console = new BufferedReader(new InputStreamReader(System.in));  
        // creates a new stub object to use to access the server.
        StudentList list = (StudentList)Naming.lookup("StudentList", "localhost", 3000); 
        listOfStubs = new HashMap<String, StudentList>();
        listOfStubs.put("StudentList", list);        
    }
    
    /**
     * Creates a new client object.
     * @param serviceName The name of the service
     * @param registryIP The ip address of the registry.
     * @param registryPort The port number of the registry.
     */
    public Client(String serviceName, String registryIP, int registryPort){
        console = new BufferedReader(new InputStreamReader(System.in));   
        // creates a new stub object to use to access the server
        StudentList list = (StudentList)Naming.lookup(serviceName, registryIP, registryPort);
        listOfStubs = new HashMap<String, StudentList>();
        listOfStubs.put("StudentList", list);
    }    
    /** 
     * getStub is used to look up a reference to a stub
     * when the application is using more than one remote
     * object.
     * @return StudentList The stub to be used by the application.
     */
    public StudentList getStub(){
        String stubToUse = null;
        StudentList stub = null;
        boolean validInput = false;
        Set keys = listOfStubs.keySet();
        // continue to prompt the user until valid input is entered
        while(!validInput){
            // display the names of remote objects
            System.out.println("\nEnter the name of the object to use");
            System.out.println("-----------------------------------");

            Object names [] = keys.toArray();
            for(int i = 0; i < names.length; i++){
                System.out.println(names[i]);
            }
            System.out.print("$ ");
            try{               
                stubToUse = console.readLine();
            } catch(Exception e){
                e.printStackTrace();
            }
            // if the user entered a valid name get the stub 
            if(listOfStubs.containsKey(stubToUse)){
                stub = (StudentList)listOfStubs.get(stubToUse);
                validInput = true;
            } else {
                System.out.println("Invalid input: selection not " +
                               "contained in list of remote objects");
            }
        }
        return stub;
        
    }
    /** 
     * lookupReference is used to allow the user to lookup
     * a new remote object to use in the application.  So
     * the client is able to access multiple remote objects
     * in the application.
     */
    public void lookupReference(){
        try{
            // get the information about the remote object to look up
            System.out.println("Enter the remote object name");
            String objectName = console.readLine();
            System.out.println("Enter the IP address of the registry");
            String address = console.readLine();
            System.out.println("Enter the port number of the registry");
            String portString = console.readLine();
            int port = Integer.parseInt(portString);
            // create a new stub 
            StudentList stub = (StudentList)Naming.lookup(objectName, address, port);
            // add the stub to the hash table
            listOfStubs.put(objectName, stub);
        } catch(Exception e){
            System.err.println("Error reading input to look up object");
        }
    }
    /**
     * Displays the menu of availiable options to the user.
     */
    public void displayMenu(){
        System.out.println("\tStudent List Menu");
        System.out.println("---------------------------------");
        System.out.println("1 - Add new student");
        System.out.println("2 - Delete a student");
        System.out.println("3 - List all students");
        System.out.println("4 - Find a student");
        System.out.println("5 - Set a students grade");
        System.out.println("6 - Get a students grade");
        System.out.println("7 - Lookup object");        
        System.out.println("8 - Exit application");
        System.out.print("$ ");
    }
    
    /**
     * Deletes a student from the server.
     */
    public void deleteStudent(){
        StudentList studentList = null;
        // if more then one stubs exits prompt the user for the stub to use
        if(listOfStubs.size() > 1){
            studentList = this.getStub();
        } 
        // do not prompt the user for input since only one exists
        else {
            Collection c = listOfStubs.values();
            Iterator i = c.iterator();
            studentList = (StudentList)i.next();
        }
        System.out.print("Enter the student number of the student to delete: ");
        String input = null;
        try{
            input = console.readLine();
        } catch(Exception e){
            System.err.println("Error reading student number");
            e.printStackTrace();            
        }
        boolean result = studentList.delStudent(input);
        System.out.println("Student was deleted: " + result + "\n");
    }
    
    /**
     * Lists all students that are on the server.
     */
    public void listAllStudents(){
        StudentList studentList = null;
        // if more then one stubs exits prompt the user for the stub to use
        if(listOfStubs.size() > 1){
            studentList = this.getStub();
        } 
        // do not prompt the user for input since only one exists
        else {
            Collection c = listOfStubs.values();
            Iterator i = c.iterator();
            studentList = (StudentList)i.next();
        }
        Vector listOfStudents = studentList.listAll();
        System.out.println("\n\t\tStudent List");
        System.out.println("--------------------------------------" +
                           "--------------------------------------");
        if(listOfStudents.size() == 0){
            System.out.println("Empty student list\n");
        } 
        for(int i = 0; i < listOfStudents.size(); i++){
            System.out.println(listOfStudents.get(i));
        }
        System.out.println();
    }
    
    /**
     * Find returns a students from the server.
     * @return Student the student found on the server.
     */
    public Student find(){
        StudentList studentList = null;
        // if more then one stubs exits prompt the user for the stub to use
        if(listOfStubs.size() > 1){
            studentList = this.getStub();
        } 
        // do not prompt the user for input since only one exists
        else {
            Collection c = listOfStubs.values();
            Iterator i = c.iterator();
            studentList = (StudentList)i.next();
        }
        System.out.print("Enter the student number to find: ");
        String input = null;
        try{
            input = console.readLine();
        } catch(Exception e){
            System.err.println("Error reading student number");
            e.printStackTrace();            
        }
        Student student = studentList.find(input);
        if(student == null){
            System.out.println("\nStudent not found\n");
        } else {
            System.out.println("\nFound: " + student + "\n");
        }
        return student;
    }    
    
    /**
     * Sets the grade of a student on the server.
     */
    public void setGrade(){
        StudentList studentList = null;
        // if more then one stubs exits prompt the user for the stub to use
        if(listOfStubs.size() > 1){
            studentList = this.getStub();
        } 
        // do not prompt the user for input since only one exists
        else {
            Collection c = listOfStubs.values();
            Iterator i = c.iterator();
            studentList = (StudentList)i.next();
        }
        boolean result = false;
        System.out.print("Enter the student number: ");
        String studentNumber = null;
        try{
            studentNumber = console.readLine();
        } catch(Exception e){
            System.err.println("Error reading student number");
            e.printStackTrace();            
        }
        Grade grade = GradeFactory.create();
        studentList.setGrade(studentNumber, grade);
    }
    
    /**
     * Gets the grades of a student on the server and displays them.
     */
    public void getGrade(){
        StudentList studentList = null;
        // if more then one stubs exits prompt the user for the stub to use
        if(listOfStubs.size() > 1){
            studentList = this.getStub();
        } 
        // do not prompt the user for input since only one exists
        else {
            Collection c = listOfStubs.values();
            Iterator i = c.iterator();
            studentList = (StudentList)i.next();
        }
        System.out.print("Enter the student number: ");
        String studentNumber = null;
        try{
            studentNumber = console.readLine();
        } catch(Exception e){
            System.err.println("Error reading student number");
            e.printStackTrace();            
        }
        Grade grade = studentList.getGrade(studentNumber);
        if(grade == null){
            System.out.println("\nGrade not found\n");
        } else {
            System.out.println("\nGrade: " + grade + "\n");
        }
    }
    
    /**
     * addStudent add a student to remote list of students
     * on the server.
     */
    public void addStudent(){
        StudentList studentList = null;
        // if more then one stubs exits prompt the user for the stub to use
        if(listOfStubs.size() > 1){
            studentList = this.getStub();
        } 
        // do not prompt the user for input since only one exists
        else {
            Collection c = listOfStubs.values();
            Iterator i = c.iterator();
            studentList = (StudentList)i.next();
        } 
		Student student = StudentFactory.create();
   		System.out.println("\nCreated a new student\n");
   		studentList.addStudent(student);
    }
    
    /**
     * Runs the application.
     */
    public void runProgram(){
        while(true){
            String input = null;
            boolean validInput = false;
            int choice = 0;
            // display the menu to the user
            this.displayMenu();           
            try{
               // accept the users selection
               while(!validInput){
                   input = console.readLine();            
                   // check that the input is a number
                   Pattern p = Pattern.compile("[1-8]");
                   Matcher m = p.matcher(input);
                   boolean isNumber = m.matches();
                   if(isNumber){
                       choice = Integer.parseInt(input);
                       validInput = true;
                   }else{
                       System.out.println("Invalid input");
                       System.out.print("$ ");
                   }
               }
            }
            catch(Exception e){
               e.printStackTrace();
            }
            StudentList studentList = null;
            // carry out the users command
            switch(choice){
           		case 1:
           		    this.addStudent();
           		    break;
           		case 2:
           		    this.deleteStudent();
           		    break;
           		case 3:
           		    this.listAllStudents();
           		    break;
           		case 4:
           		    this.find();
           		    break;
           		case 5:
           		    this.setGrade();
           		    break;
           		case 6:
           		    this.getGrade();
           		    break;
           		case 7:
           		    this.lookupReference();
           		    break;           		    
           		case 8:
           		    System.out.println("Exiting the program");
           		    System.exit(0);
           		    break;
           		default:
           		    System.out.println("Invalid input");
           }
        }
    }
    private HashMap <String, StudentList> listOfStubs;

    private BufferedReader console;			// read the users input

 //   private StudentList list;		// stub used to send messages
    
    /**
     * Main entry point to the client application
     * @param args The command line arguments supplied by the user.
     */
    public static void main(String[] args){
        int port = 0;
        String ip = null;
        String serviceName = null;
        Client clientProgram = null;
        System.out.println("RMI Student List Application");
        // create the user supplied connection
        if(args.length == 3){
            serviceName = args[0];
            ip = (String)args[1];
            port = Integer.parseInt(args[2]);
            clientProgram = new Client(serviceName, ip, port);
            clientProgram.runProgram();
        }
        // display help to the user
        else if((args.length == 1) && 
                ((args[0].equals("-h"))|| (args[0].equals("--help")))){
                System.out.println("Usage: Client [Service Name] [Registry IP] [Registry Port]");
                System.exit(0);
        } else{
            // create the default connection
            clientProgram = new Client();
            clientProgram.runProgram();
        }
    }
}
