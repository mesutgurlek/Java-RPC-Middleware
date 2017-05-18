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

import java.io.Serializable;

 /**
  * The implementation class for Student Interface.
  * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
  */

 public class StudentImpl implements Student, Serializable{
     
     /**
      * Creates a new intance of StudentImpl
      */
     public StudentImpl(){
         name = "NA";
         studentNumber = "NA";
         address = "NA";
         program = "NA";
         age = 0;
         grade = new GradeImpl();
     }
     
     /** Creates a new instance of StudentImpl 
      *  @param aName the name of the student
      *  @param aAge the age of the student
      *  @param aAddress the address of the student
      *  @param aProgram the program the student is enrolled in
      */
     public StudentImpl(String aName, String aStudentNumber,
                    int aAge, String aAddress, String aProgram) {
      
         name = aName;
         studentNumber = aStudentNumber;
         age = aAge;
         address = aAddress;
         program = aProgram;
     }
     
     /** Get the name of the student
      *  @return student name
      */
     
     public String getName(){
         return name;
     }
     
     /** Get the age of the student
     *  @return age
     */
        
     public int getAge(){
         return age;
     }
     
     /** Get the program of the student
     *  @return the program the srudent is enrolled in 
     */    
     
     public String getProgram(){
         return program;
     }
     
     /** Get the student number
     *  @return student number
     */
     
     public String getStudentNumber(){
         return studentNumber;
     }
     
     /** Get the grades of the student
     *  @return grade
     */
     
     public Grade getGrade(){
         return grade;
     }

     /** set the grades of the student
     *  @param grade
     */
     
     public void setGrade(Grade grade){
         this.grade = grade;
     }

     /** toString returns a string representing the student
     *   @return String representing the student
     */
     
     public String toString(){
         String studentInfo;
         studentInfo =  "Name: " + name + ", SN# " + studentNumber
                        + ", Age:" + age + ", Address: " + address  
                        + ", Program: " + program;
         return studentInfo;
     }
     /**
      * @return Returns the address.
      */
     public String getAddress() {
         return address;
     }
     /**
      * @param address The address to set.
      */
     public void setAddress(String address) {
         this.address = address;
     }
     
     /**
      * @param age The age to set.
      */
     public void setAge(int age) {
         this.age = age;
     }
     
     /**
      * @param name The name to set.
      */
     public void setName(String name) {
         this.name = name;
     }
     
     /**
      * @param program The program to set.
      */
     public void setProgram(String program) {
         this.program = program;
     }
     
     /**
      * @param studentNumber The studentNumber to set.
      */
     public void setStudentNumber(String studentNumber) {
         this.studentNumber = studentNumber;
     }
     
     private String name;            // student name
     private String studentNumber;   // student number
     private int age;                // age of the student
     private String address;         // address of the student
     private String program;         // program the student is enrolled in
     private Grade grade;            // grade of the student

 }