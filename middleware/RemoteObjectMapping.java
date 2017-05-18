/*
 * RemoteObjectMapping
 * Created on Nov 27, 2004 at 3:52:39 AM
 *
 * Nathan Balon
 * University of Michigan Dearborn
 * CIS 578
 * Advanced Operating Systems
 * 
 */

package nathan.middleware;

import java.net.*;
import java.util.Vector;
import java.io.Serializable;
import java.util.Date;

/**
 * RemoteObjectMapping is used to store information about
 * the replicated objects in the system, along with information 
 * about the front end to the active replication manager.
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 *
 */

public class RemoteObjectMapping implements Serializable{

    
    /**
     * Creates a new instance of RemoteObjectMapping
     */
    public RemoteObjectMapping(){
        replicatedObjects = new Vector<RemoteObjectReference>();
        isReplicated = false;
    }
    
    /**
     * Creates a new instance of RemoteObjectMapping
     * @param isReplicated Is the replication being used.
     * @param ror  The RemoteObjectReference of the front end.
     */
    public RemoteObjectMapping(boolean isReplicated, RemoteObjectReference ror){
        replicatedObjects = new Vector<RemoteObjectReference>();
        this.isReplicated = isReplicated;
        this.frontEndTimeStamp = ror.getTimeCreated();
        frontEndID = ror.getObjectID();
        this.frontEndROR = ror;
        
    }
    
    /**
     * Get the remote object reference for the front end so
     * that it can be located
     * @return RemoteObjectReference of the front end.
     */
    public RemoteObjectReference getFrontEndROR(){
        return frontEndROR;
    }
    
    /**
     * Get the address of where the front of the replication 
     * manager is located.
     * @return InetAddress the frontEnd address.
     */
    public InetAddress getFrontEndAddress() {
        return frontEndAddress;
    }
    
    /**
     * Sets the address of the Front end.
     * @param frontEndAddress The frontEnd address to set.
     */
    public void setFrontEndAddress(InetAddress frontEndAddress) {
        this.frontEndAddress = frontEndAddress;
    }
    
    /**
     * Gets the port number of the front end.
     * @return int Returns the frontEndPort.
     */
    public int getFrontEndPort() {
        return frontEndPort;
    }
    
    /**
     * Sets the port number for the front end.
     * @param frontEndPort The frontEndPort to set.
     */
    public void setFrontEndPort(int frontEndPort) {
        this.frontEndPort = frontEndPort;
    }
    
    /**
     * Sets the front end address
     * @param address The IP address of the front end.
     * @param port The port number of the front end.
     */
    public void setFrontEnd(InetAddress address, int port){
        this.frontEndPort = port;
        this.frontEndAddress = address;
    }
    /**
     * Gets all of the replicated objects of the front end.
     * @return Returns the replicateObjects.
     */
    public Vector getRemoteObjects() {
        return replicatedObjects;
    }
    
    /**
     * Set the replicated objects.
     * @param replicateObjects The replicateObjects to set.
     */
    public void setReplicatedObjects(Vector <RemoteObjectReference> replicateObjects) {
        this.replicatedObjects = replicateObjects;
    }
    
    /**
     * Adds a new replicated object.
     * @param ror The remote object reference for a remote object.
     */
    public void addRemoteObject(RemoteObjectReference ror){
        replicatedObjects.add(ror);
    }
    
    /**
     * Removes a specific remote object from the replication manager.
     * @param ror The remote object reference to be removed.
     */
    public void removeRemoteObject(RemoteObjectReference ror){
        for(int i = 0; i < replicatedObjects.size(); i++){
            RemoteObjectReference otherROR = replicatedObjects.get(i);
            if(otherROR.equals(ror)){
                replicatedObjects.remove(i);
            }
        }
    }

    /**
     * Returns true if replication is being used.
     * @return boolean is replication used.
     */
    public boolean isReplicated() {
        return isReplicated;
    }
    

    /**
     * Sets if repilication is used.
     * @param isReplicated
     */
    public void setReplicated(boolean isReplicated) {
        this.isReplicated = isReplicated;
    }

    /**
     * Returns the number of replicated objects managed by the frontend.
     * @return the number of replicated objects.
     */
    public int size(){
        return replicatedObjects.size();
    }
    
    private RemoteObjectReference frontEndROR;
    private InetAddress frontEndAddress;
    private int frontEndPort;
    private int frontEndID;
    private Date frontEndTimeStamp;
    private Vector <RemoteObjectReference> replicatedObjects;
    private boolean isReplicated;

}
