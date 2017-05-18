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
import java.util.Vector;

/** The implementation class for the StudentList.
 *  StudentListImpl is used to store information about students
 *  in a list so it can be accessed later.
 *
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 */

 public class StudentListImpl implements StudentList, Serializable {
     
     /** Creates a new instance of StudentListImpl */
     public StudentListImpl() {
         students = new Vector<Student>();
     }
     
      /**
      *  adds a student to the student list
      *  @param aStudent the student to be added
      *  @return the status of the operation
      */   
     
     public boolean addStudent(Student aStudent){
         students.add(aStudent);   
         return true;
     }
     
     /**
      *  deletes a student from the list
      *  @param aStudentNumber the student number of the student to be deleted
      *  @return boolean the status of the operation
      */
     
     public boolean delStudent(String aStudentNumber){
         for(int i = 0; i < students.size(); i++){
             StudentImpl stud = (StudentImpl)students.get(i);
             if(stud.getStudentNumber().equals(aStudentNumber)){
                 students.remove(i);
                 return true;
             }              
         }
         return false;        
     }
     
     /**
      *  list all students in the student list
      *  @return Vector of students
      */   
     
     public Vector listAll(){
         return (Vector)students.clone();
     }
     
     /**
      *  find a specific student in the list
      *  @param aStudentNumber the student number of the student to be returned
      *  @return StudentImpl the student
      */
     
     public Student find(String aStudentNumber){
         for(int i = 0; i < students.size(); i++){
             Student stud = (Student)students.get(i);
             if(stud.getStudentNumber().equals(aStudentNumber)){
                 return stud;
             }              
         }
         return null;        
     }

     /** 
      *  setGrade set the grade for a student
      *  @param aStudentNumber the student number
      *  @param aGrade the grade to be set
      */
     
     public boolean setGrade(String aStudentNumber, Grade aGrade){
         Student aStudent = this.find(aStudentNumber);
         if(aStudent != null){
             aStudent.setGrade(aGrade);
             return true;
         } else {
             return false;
         }
     }
     
     /**
      *  get the grade of a student
      *  @param aStudentNumber the student number
      *  @return GradeImpl
      */
     
     public Grade getGrade(String aStudentNumber){
         for(int i = 0; i < students.size(); i++){
             Student student = (Student)students.get(i);
             if(student.getStudentNumber().equals(aStudentNumber)){
                 return student.getGrade();
             }              
         }
         return null;         
     }
     
     

    /* (non-Javadoc)
     * @see nathan.middleware.StudentList#getReplicatedObject()
     */
    public Vector getReplicatedObject(){
         return students;
     }
     

    /* (non-Javadoc)
     * @see nathan.middleware.StudentList#setReplicatedObject(null)
     */
    public void setReplicatedObject(Vector <Student> studentList){
         students = studentList;
     }
     
     private Vector <Student> students;

    
 }