/*
 * Created on Oct 27, 2004
 * 
 * @author  Nathan Balon
 * Middleware Application
 * CIS 578 - Advanced Operating Systems
 * U 0f M Dearborn
 * @version 1.0
 */

package nathan.middleware;

/**
 * Grade Interface is the interface used to access grade objects
 * in the application. 
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 * 
 */

 public interface Grade{
         
     /**
     * Get the grade for course I.
     * @return char The grade of the student for course I.
     */     
    char getCourseIGrade();
    
     /**
     * Set the grade for course I.
     * @param grade The grade for course I.
     * @return boolean Returns true if the grade was set.
     */
    boolean setCourseIGrade(char grade);
     
     /**
     * Get the grade for course II.
     * @return char The grade of the student for course II.
     */
    char getCourseIIGrade();
    
     /**
     * Set the grade for course II.     
     * @param grade The grade for course II.
     * @return boolean Returns true if the grade was set.
     */
    boolean setCourseIIGrade(char grade);
     
     /**
     * Get the grade for course III.
     * @return char The grade of the student for course III.
     */
    char getCourseIIIGrade();
    
     /**
     * Set the grade for course III.  
     * @param grade The grade for course III.
     * @return boolean Returns true if the grade was set.
     */
    boolean setCourseIIIGrade(char grade);
     
     /**
     * Get the grade point average.
     * @return double the grade point average of the student.
     */
    double getGPA();
    
     /**
     * Get a String representation of all the grades.
     * @return String A String listing all of the grades.
     */
    String toString();
 }
