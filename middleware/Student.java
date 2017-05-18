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
 * Interface for a student.
 *
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 *
 */
 public interface Student {
     
     /** Get the name of the student
      *  @return student name
      */
     
     String getName();
     
     /** Get the age of the student
     *  @return age
     */
        
     int getAge();
     
     /** Get the program of the student
     *  @return the program the srudent is enrolled in 
     */    
     
     String getProgram();
     
     /** Get the student number
     *  @return student number
     */
     
     String getStudentNumber();
     
     /** Get the grades of the student
     *  @return grade
     */
     
     Grade getGrade();

     /** set the grades of the student
     *  @param grade
     */
     
     void setGrade(Grade grade);
     
     /** set the name of the student
      *  @param name
      */      
     void setName(String name);
      
      /** set the age of the student
       *  @param age
       */      
     void setAge(int age);

     /** set the program of the student
      *  @param programName The name of the program
      */      
     void setProgram(String programName);
        
     /** set the student number for the student
       *  @param studentNumber The student number for the student
       */      
     void setStudentNumber(String studentNumber);        

     /** set the address for the student
      *  @param address The student number for the student
      */           
     void setAddress(String address);           

     /** toString returns a string representing the student
     *   @return String representing the student
     */    
     String toString();
 }