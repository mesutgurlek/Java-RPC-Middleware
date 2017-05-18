/*
 * RemoteObjectReference.java
 * 
 * @author  Nathan Balon
 * Advance Operating Systems CIS 578
 * University of Michigan Dearborn
 * Remote Method Invocation Middleware Project
 * 
 * Created on September 29, 2004, 1:03 AM
 */

package nathan.middleware;
import java.net.*;
import java.util.Date;
import java.io.Serializable;

/**
 * RemoteObjectReference is used to represent a remote object in the
 * distributed system. The Remote Reference is used to access remote objects.
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 */

public class RemoteObjectReference implements Serializable{
    
    /**
     * Creates a new instance of RepresentationOfRemoteObject
     */
    public RemoteObjectReference(){
        timeCreated = new Date();
        ObjectID id = ObjectID.objectIDFactory();
        objectID = id.getNextID();
        port = 0;
        
    }
    
    /**
     * Creates a new instance of RepresentationOfRemoteObject
     * @param aAddress The ip address of the remote reference.
     * @param aPort	The port number of the remote reference.
     * @param name The name of the remote reference.
     */
    public RemoteObjectReference(InetAddress aAddress, int aPort, 
                                 String name, String implementationClassName){
         this.address = aAddress;
         this.port = aPort;
         this.timeCreated = new Date();
         ObjectID id = ObjectID.objectIDFactory();
         this.objectID = id.getNextID();
         this.name = name;           
         this.implementationClass = implementationClassName;
    }
    
    /**
     * Get the remote refernces address.
     * @return InetAddress The address where the remote reference is located
     */
    public InetAddress getAddress(){
        return address;
    }
    
    /**
     * Get the port number of the remote reference.
     * @return int The port number of the remote reference.
     */
    public int getPort(){
        return port;
    }
    
    /**
     * Get the id number of the reference.
     * @return int The id of the reference.
     */
    public int getObjectID(){
        return objectID;
    }
    
    /**
     * Get the time that the remote reference was created.
     * @return Date The time the reference was created.
     */
    public Date getTimeCreated(){
        return timeCreated;
    }
    
    /**
     * The name of the remote reference.
     * @return String the name of the reference.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Get the implemetation class.
     * @return Returns the implementationClass.
     */
    public String getImplementationClass() {
        return implementationClass;
    }
    
    /**
     * Set the implemetation class.
     * @param implementationClass The implementationClass to set.
     */
    public void setImplementationClass(String implementationClass) {
        this.implementationClass = implementationClass;
    }
    
    /** 
     * Gets the hash code for a RemoteObjectReference.
     * @return int The has code for a RemoteObjectReference.
     * @see java.lang.Object#hashCode()
     */
    public int hashCode(){
        int hashCode = 17;   
        hashCode = 37 * hashCode + name.hashCode();
        hashCode = 37 * hashCode + address.hashCode();
        hashCode = 37 * hashCode + port;
        hashCode = 37 * hashCode + timeCreated.hashCode();   
        hashCode = 37 * hashCode + objectID;
        return hashCode;
    }
    
    /**
     * Compares if two RemoteObjectReferences are equal.
     * @param o the object to compare for equality.
     * @return boolean Returns true if two RemoteObjectReferences are equal.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o){
        if((o != null) && (this.getClass() == o.getClass())){
            RemoteObjectReference ror = (RemoteObjectReference)o;
            if((port == ror.getPort()) && (objectID == ror.getObjectID()) &&
               (timeCreated.equals(ror.getTimeCreated())) &&
               (address.equals(ror.getAddress()) && 
               (name.equals(ror.getName())))){
                return true;
            }
            else{
                return false;
            }
        } else {
            return false;
        }
    }
  
    /**
     * Returns a String representation of a RemoteObjectReference.
     * @return String The String representation of a RemoteObjectReference.
     * @see java.lang.Object#toString()
     */
    public String toString(){
        String out = "Name = " + name + ", ID = " + objectID + 
        			 "Implentation class = " + implementationClass +
                     ", Time Created = " + timeCreated + 
                     ", IP = " + address.getHostAddress() +
                     ", Port = " + port;
        return out;
    }
    
    private InetAddress address;            // address of the object
    private int port;                       // port number
    private int objectID;                   // id number of the object
    private Date timeCreated;               // timestamp when created
    private String name;           			// name of interface
    private String implementationClass;

}