/*
 * RegistyMessage.java
 * 
 * @author  Nathan Balon
 * Advance Operating Systems CIS 578
 * University of Michigan Dearborn
 * Remote Method Invocation Middleware Project
 * 
 * Created on September 29, 2004, 1:03 AM
 */
package nathan.middleware;
import java.io.Serializable;

/**
 * RegistryMessage is the object that is sent to the registry
 * which contains the registry messages.  The client and server
 * use RegistyMessages to contact the registry.
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 *
 */
public class RegistryMessage implements Serializable{
    
    /**
     * Creates a new instance of RegistryMessage.
     * @param messageType The message type.
     * @param objectName The name of the object to bind to the registry.
     * @param o The object to bind.
     * @param portToBind The port to bind.
     */
    RegistryMessage(int messageType, String objectName, Object o, int portToBind){
        this.messageType = messageType;
        this.objectToBind = o;
        this.objectName = objectName;
        this.portToBind = portToBind;
    }
    
    /**
     * Creates a new instance of RegistryMessage.
     * @param messageType The message type.
     * @param objectName The name of the object to bind to the registry.
     * @param portToBind The port to bind.
     */
    RegistryMessage(int messageType, String objectName, int portToBind){
        this.messageType = messageType;
        this.objectName = objectName;
        this.portToBind = portToBind;
    }
    
    /**
     * Creates a new instance of RegistryMessage.
     * @param messageType The message type.
     * @param objectName The name of the object to bind to the registry.
     */
    RegistryMessage(int messageType, String objectName){
        this.messageType = messageType;
        this.objectName = objectName;
    }
    
    /**
     * Creates a new instance of RegistryMessage.
     * @param messageType
     * @param object
     */
    RegistryMessage(int messageType, Object object){
        this.messageType = messageType;
        this.objectToBind = object;
    }
        
    /**
     * Returns the name of the oject.
     * @return Returns the objectName.
     */
    public String getObjectName() {
        return objectName;
    }
    
    /**
     * Set the name of the object to bind.
     * @param objectName The objectName to set.
     */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }  
    
    /**
     * Gets the portToBind.
     * @return int The port number to bind
     */
    public int getPortToBind() {
        return portToBind;
    }
    
    /**
     * Set the port number to bind.
     * @param portToBind The portToBind to set.
     */
    public void setPortToBind(int portToBind) {
        this.portToBind = portToBind;
    }    

    /**
     * Get the type of registry message.
     * @return Returns the messageType.
     */
    public int getMessageType() {
        return messageType;
    }
    
    /**
     * Set the type of message.
     * @param messageType The messageType to set.
     */
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
    
    /**
     * Get the object to bind to the registry.
     * @return Returns the objectToBind.
     */
    public Object getObjectToBind() {
        return objectToBind;
    }
    
    /**
     * Set the object to bind to the registry.
     * @param objectToBind The objectToBind to set.
     */
    public void setObjectToBind(Object objectToBind) {
        this.objectToBind = objectToBind;
    }
    

    /**
     * Returns a String representation of the registry message.
     * @return String A String representation of the registry message.
     * @see java.lang.Object#toString()
     */
    public String toString(){
        String objectString;
        if(messageType == BIND){
            objectString = "BIND ";
        } else if (messageType == LOOKUP){
            objectString = "LOOKUP ";
        } else if (messageType == NEW_FRONT_END){
            objectString = "NEW_FRONT_END";
        } else if (messageType == REMOVE_BINDING){
            objectString = "REMOVE_BINDING";
        }else {
            objectString = "invalid ";
        }
        objectString += objectName;
        return objectString;
    }
    
    /**
     * <code>BIND</code> a message to bind an object to the registry.
     */
    public static final int BIND = 1;			
    /**
     * <code>LOOKUP</code> a message to lookup an object in the registry.
     */
    public static final int LOOKUP = 2;			
    /**
     * <code>NEW_FRONT_END</code> a message to add a new FrontEnd for active replication.
     */
    public static final int NEW_FRONT_END = 3;  
    /**
     * <code>REMOVE_BINDING</code> a message to remove a binding from the registry.
     */
    public static final int REMOVE_BINDING = 4; 
    
    private int messageType;					// type of message
    private Object objectToBind;				// object to bind
    private String objectName;					// the name of the object
    private int portToBind;						// the port to bind
    




}
