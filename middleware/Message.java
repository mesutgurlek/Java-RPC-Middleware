/*
 * Message.java
 * Message to be pass by communication module.
 *
 * @author  Nathan Balon
 * Advance Operating Systems CIS 578
 * University of Michigan Dearborn
 * Remote Method Invocation Middleware Project
 *
 * Created on September 29, 2004, 1:53 AM
 */


package nathan.middleware;
import java.util.Vector;
import java.io.Serializable;

/**
 * The messages to be sent in between the client and the server.
 * Messages objects are sent between the cleint and server to
 * commincate the method that should be invoked on an object
 * and to return the value of the invocation to the client.
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 */

public class Message implements Serializable{
    /** Creates a new instance of Message */
    public Message(){
        
    }
    /**
     * Creates a new instance of Message.
     * @param messageType The type of message to be sent.
     */
    public Message(int messageType){
        this.messageType = messageType;
    }
    
    /** 
     *  Creates a new instance of Message to be passed
     *  @param aMessageType the type of message to be sent
     *  @param aRequestID
     *  @param aRor the remote object reference
     *  @param aMethodName the name of the method to be called
     *  @param aArguments the arguments to be passed to the method
     */
    public Message(int aMessageType, int aRequestID, RemoteObjectReference aRor,
                   String aMethodName, Vector aArguments){
                       
        messageType = aMessageType;
        messageID = aRequestID;
        ror = aRor;
        methodName = aMethodName;
        args = aArguments;
    }
    
    
    /**
     * Get the type of message.
     * @return int The message type.
     */
    public int getMessageType(){
        return messageType;
    }
    
    /**
     * Set the message type.
     * @param aMessageType The type of message to be sent.
     */
    public void setMessageType(int aMessageType){
        messageType = aMessageType;
    }
    
    /**
     * Get the message ID of the message.
     * @return int The message ID.
     */
    public int getMessageID(){
        return messageID;
    }

    /**
     * Sets the Message ID
     * @param messageID
     */
    public void setMessageID(int messageID){
        this.messageID = messageID;
    }
    
    /**
     * Get the remote object reference.
     * @return RemoteObjectReference The remote object 
     * reference held in the message.
     */
    public RemoteObjectReference getRemoteObjectReference(){
        return ror;
    }
    
    /**
     * Set the RemoteObjectReference.
     * @param aRor The RemoteObjectReference
     */
    public void setRemoteObjectReference(RemoteObjectReference aRor){
        ror = aRor;
    }    
    
    /**
     * Get the name of the method to be called.
     * @return String The method name
     */
    public String getMethodName(){
        return methodName;
    }
    
    /**
     * Set the method name to be called remotely.
     * @param aMethodName The method name to be called.
     */
    public void setMethodName(String aMethodName){
        methodName = aMethodName;
    }
    
    /**
     * Get the arguments for the remote method invocation
     * @return Vector A vector holding a the aguments of a method.
     */
    public Vector getArguments(){
    	return args;
    }
    
    /**
     * Set the arguments to be passed to the remote method.
     * @param aArgs A vector of arguments to be passed to the
     * remote method.
     */
    public void setArguments(Vector aArgs){
    	args = aArgs;
    }
    
    /** 
     * Compare if to sets of arguments are equal.
     * @param otherArgs A vector of arguments to be compared.
     * @return boolen true if the arguments are equal.
     */
    public boolean compareArgs(Vector otherArgs){
        if(args.size() != otherArgs.size()){
            return false;
        }
        for(int i = 0; i < args.size(); i++){
            if(!args.get(i).equals(otherArgs.get(i))){
                return false;
            }
        }
        return true;
    }
    /**
     * Returns a string representation of the message sent
     * @return String the message string
     * @see java.lang.Object#toString()
     */
    public String toString(){
    	String messageString = "ID: " + messageID +" ,Message type: ";
    	if(messageType == MessageType.REQUEST){
    	    messageString += "REQUEST";
    	} else if(messageType == MessageType.REPLY){
    	    messageString += "REPLY";
    	} else if(messageType == MessageType.MARKER){
    	    messageString += "MARKER";
    	} else if(messageType == MessageType.NEW_REPLICA){
    	    messageString += "NEW_REPLICA";
    	} else if(messageType == MessageType.CLOSE_CONNECTION){
    	  messageString += "CLOSE_CONNECTION";  
    	} else {
    	    messageString += "Invalid message type";
    	}
    	messageString += ", Method: " + methodName +
    					 ", Args: ";
    	if(args != null){
    	    messageString += args.toString();
    	} else {
    	    messageString += "NA";
    	}
    	messageString += ", ROR:" + ror;
    	return messageString;
    }
    

    private int messageType;				// the message type
    private int messageID;					// the message id
    private RemoteObjectReference ror;		// remote object reference
    private String methodName;				// name of method to be called
    private Vector args;					// arguments to be passed to the method
}