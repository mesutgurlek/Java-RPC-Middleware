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
 * The implementation of the Grade interface used to store a students grade. 
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 *
 */

public class GradeImpl implements Serializable, Grade{
     
     /** Creates a new instance of GradeImpl */
    public GradeImpl() {
         // initialize the course grades to be empty
         courseI = ' ';
         courseII = ' ';
         courseIII = ' ';
         gpa = 0;
     }
     
     /* (non-Javadoc)
     * @see nathan.middleware.Grade#setCourseIGrade(char)
     */
    public boolean setCourseIGrade(char aGrade){
         // check that aGrade is a valid grade 
         if(((aGrade >= 'a') && (aGrade <= 'e')) || 
            ((aGrade >= 'A') && (aGrade <= 'E'))){
             // determine the grade points that the grade is worth
             if((aGrade == 'a') || (aGrade == 'A')){
                 totalGradePoints += 4;
             }
             else if((aGrade == 'b') || (aGrade == 'B')){
                 totalGradePoints += 3;
             }
             else if((aGrade == 'c') || (aGrade == 'C')){
                 totalGradePoints += 2;
             }
             else if((aGrade == 'd') || (aGrade == 'D')){           
                 totalGradePoints += 1;
             }
             else{

                 totalGradePoints += 0;
             }
             courseI = aGrade;           // set the grade
             classesTaken++;             // increment the number of classes taken
             return true;
         }
         else{
             // could not set the grade because a valid grade was not passed
             return false;
         } 
     }
     
     /* (non-Javadoc)
     * @see nathan.middleware.Grade#getCourseIGrade()
     */
    public char getCourseIGrade(){
         return courseI;
     }
     
      /* (non-Javadoc)
     * @see nathan.middleware.Grade#setCourseIIGrade(char)
     */
    public boolean setCourseIIGrade(char aGrade){
         // check that aGrade is a valid grade 
         if(((aGrade >= 'a') && (aGrade <= 'e')) || 
            ((aGrade >= 'A') && (aGrade <= 'E'))){
             // determine the grade points that the grade is worth
             if((aGrade == 'a') || (aGrade == 'A')){
                 totalGradePoints += 4;
             }
             else if((aGrade == 'b') || (aGrade == 'B')){
                 totalGradePoints += 3;
             }
             else if((aGrade == 'c') || (aGrade == 'C')){
                 totalGradePoints += 2;
             }
             else if((aGrade == 'd') || (aGrade == 'D')){           
                 totalGradePoints += 1;
             }
             else{
                 totalGradePoints += 0;
             }
             courseII = aGrade;          // set the grade
             classesTaken++;             // increment the number of classes taken
             return true;
         }
         else{
             // could not set the grade because a valid grade was not passed
             return false;
         }     
     }
     
     /* (non-Javadoc)
     * @see nathan.middleware.Grade#getCourseIIGrade()
     */
    public char getCourseIIGrade(){
         return courseII;
     }
     
     /* (non-Javadoc)
     * @see nathan.middleware.Grade#setCourseIIIGrade(char)
     */
    public boolean setCourseIIIGrade(char aGrade){
         // check that aGrade is a valid grade 
         if(((aGrade >= 'a') && (aGrade <= 'e')) || 
            ((aGrade >= 'A') && (aGrade <= 'E'))){
             // determine the grade points that the grade is worth
             if((aGrade == 'a') || (aGrade == 'A')){
                 totalGradePoints += 4;
             }
             else if((aGrade == 'b') || (aGrade == 'B')){
                 totalGradePoints += 3;
             }
             else if((aGrade == 'c') || (aGrade == 'C')){
                 totalGradePoints += 2;
             }
             else if((aGrade == 'd') || (aGrade == 'D')){           
                 totalGradePoints += 1;
             }
             else{
                 totalGradePoints += 0;
             }
             courseIII = aGrade;         // set the grade
             classesTaken++;             // increment the number of classes taken
             return true;
         }
         else{
             // could not set the grade because a valid grade was not passed
             return false;
         } 

     } 
     
     /* (non-Javadoc)
     * @see nathan.middleware.Grade#getCourseIIIGrade()
     */
    public char getCourseIIIGrade(){
         return courseIII;
     }
     
     /* (non-Javadoc)
     * @see nathan.middleware.Grade#getGPA()
     */
    public double getGPA(){
         gpa = (double)totalGradePoints/classesTaken;
         return gpa;
     }
  
     /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString(){
         String gradeString = "GRADES: Course I: " + courseI + ", Course II: "
                              + courseII + ", Course III: " + courseIII +
                              ", GPA: " + this.getGPA();
         return gradeString;       
     }
     
     private char courseI;					// grade for courseI
     private char courseII;					// grade for courseII
     private char courseIII;				// grade for courseIII
     private int totalGradePoints;			// total grade points
     private int classesTaken;    			// number of classes taken
     private double gpa;					// gpa for all classes taken
 }
