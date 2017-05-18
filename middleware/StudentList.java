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
import java.util.Vector;

/** Student List Interface */

 public interface StudentList{
     /**
      *  adds a student to the student list
      *  @param student the student to be added
      *  @return the status of the operation
      */   
     boolean addStudent(Student student);
     
     /**
      *  deletes a student from the list
      *  @param studentNumber the student number of the student to be deleted
      *  @return boolean the status of the operation
      */
     boolean delStudent(String studentNumber);
     
     /**
      *  find a specific student in the list
      *  @param studentNumber the student number of the student to be returned
      *  @return Student the student
      */
     Student find(String studentNumber);
     
     /**
      *  get the grade of a student
      *  @param studentNumber the student number
      *  @return Grade
      */     
     Grade getGrade(String studentNumber);
     
     /**
      *  list all students in the student list
      *  @return Vector of students
      */   
     Vector listAll();
     /** 
      * Get a copy of the student list
      * @return Vector containing all of the students.
      */
     public Vector getReplicatedObject();
     
     /**
      * Set the student list
      * @param list A Vector containing all students.
      */
     public void setReplicatedObject(Vector <Student> list);
     /** 
      *  setGrade set the grade for a student
      *  @param studentNumber the student number
      *  @param grade the grade to be set
      */
     
     boolean setGrade(String studentNumber, Grade grade);
 }