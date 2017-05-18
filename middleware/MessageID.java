/*
 * MessageID
 * Created on Nov 21, 2004 at 7:34:31 AM
 *
 * Nathan Balon
 * University of Michigan Dearborn
 * CIS 578
 * Advanced Operating Systems
 * 
 */
package nathan.middleware;


/**
 * MessageID is used to create logical timestamps for messages.
 * 
 * @author  Nathan Balon
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 *
 */

public class MessageID {
    /**
     * Creates a new instance of message ID
     */
    public MessageID(){
        id = 0;
    }
    
    /**
     * Gets the next availiable ID number to use.
     * @return int The id number to be used.
     */
    public synchronized int getNextID(){
        return ++id;
    }
    
    /**
     * Gets the current ID number.
     * @return int The current ID number. 
     */
    public int getID(){
        return id;
    }
    
    /**
     * Compares two IDs and returns the largest values after
     * incrementing the ID by on.
     * @param otherID The ID to compare with.
     * @return int The largest of the IDs incremented by 1.
     */
    public synchronized int getMaxID(int otherID){
        if(otherID >= id){
            id = otherID;
            id++;
        }
        return id;
    }
    
    private int id;
}
