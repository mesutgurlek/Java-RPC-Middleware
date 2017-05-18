/*
 * ObjectID
 * Created on Nov 8, 2004 at 5:50:57 PM
 *
 * Nathan Balon
 * University of Michigan Dearborn
 * CIS 578
 * Advanced Operating Systems
 * 
 */
package nathan.middleware;


/**
 * ObjectID is used to create unique IDs for the object that
 * are bound in the registry.
 *
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 *
 */

public class ObjectID {
    

    private ObjectID(){
        id = 1;
    }
    
    /**
     * Get the next id number.
     * @return int The next availiable id.
     */
    public int getNextID(){
        return id++;
    }
    
    /**
     * Create a new ObjectID instance which is used by 
     * the registry to number remote object references.
     * @return ObjectID An ObjectID object.
     */
    public static synchronized ObjectID objectIDFactory(){
        if(objectID == null){
            objectID = new ObjectID();
        }
        return objectID;
    }
    
    private static int id;
    private static ObjectID objectID;
}
