/*
 * MessageType
 * Created on Nov 23, 2004 at 12:02:11 AM
 *
 * Nathan Balon
 * University of Michigan Dearborn
 * CIS 578
 * Advanced Operating Systems
 * 
 */
package nathan.middleware;


/**
 * MessageType Interface contains the type of messages that can
 * be sent in the application.
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 *
 */

public interface MessageType {
    /**
     * <code>REQUEST</code> a request message to be sent.
     */
    public static final int REQUEST = 1;
    /**
     * <code>REPLY</code> a reply message
     */
    public static final int REPLY = 2;
    /**
     * <code>MARKER</code> a marker message used to get the global state of the system.
     */
    public static final int MARKER = 3;
    /**
     * <code>NEW_REPLICA</code> a message sent from the registry telling the 
     * active replication manager that a new remote object has been added.
     */
    public static final int NEW_REPLICA = 4;
    /**
     * 
     * <code>REMOVE_REPLICA</code> a message sent to the registry to remove
     * the replicated object from it list of remote objects.
     */
    public static final int REMOVE_REPLICA = 5;
    /**
     * <code>CLOSE_CONNECTION</code> a message sent to the client to terminate there
     * connection if no more objects are able to be accessed.
     */
    public static final int CLOSE_CONNECTION = 6;
    /**
     * <code>ARE_YOU_ALIVE</code> a message checking if the connection is still active.
     */
    public static final int ARE_YOU_ALIVE = 7;
    /**
     * <code>ALIVE</code> check if the connection is alive
     */

    public static final int ALIVE = 8;
}
