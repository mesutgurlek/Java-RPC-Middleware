/*
 * Created on Oct 27, 2004 @author Nathan Balon Middleware Application CIS 578
 * Advanced Operating Systems U 0f M Dearborn
 * @version 1.0
 */

package nathan.middleware;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * ServerCommunicationHandler is used to create a new thread to handle message
 * from the client.
 */

public class ServerCommunicationHandler extends Thread {

	/**
     * Create a new ServerCommunicationHandler.
     * @param socket The socket for the connection
     */
	ServerCommunicationHandler(Socket socket) {
		clientConn = socket;
		messageID = new MessageID();
	}

	/*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
	public void run() {
		try {
		    // get the input and output streams for the socket
		    in = new ObjectInputStream(clientConn.getInputStream());
		    out = new ObjectOutputStream(clientConn.getOutputStream());
			Message m = this.getMessage();
			this.sendMessage(m);
		} 
		catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    try{
		        // close the connections
		        in.close();
		        out.close();
		        clientConn.close();
		    }
		    catch(Exception e){
		        System.err.println("Error in run method");
		    }
		}
	}

	/**
     * Returns a message back to the client.
     * @param message The message to be sent back to the client.
     */
	public void sendMessage(Message message) {
		try {
		    if(message.getMessageType() != MessageType.ALIVE){
		        // set message type to reply
		        message.setMessageType(MessageType.REPLY);
		    }
		    // increment the lamport timestamp
		    int receivedMessageID = message.getMessageID();		    
		    message.setMessageID(messageID.getMaxID(receivedMessageID));
		    // send the message
			out.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
     * Get the message that was sent from the client.
     * @return message the message sent from the client.
     */
	public Message getMessage() {
		Message message = null;
		try {
			// read the message in from the socket
			message = (Message) in.readObject();	
			// increment the lamport timestamp
			int receivedMessageID = message.getMessageID();		    
			message.setMessageID(messageID.getMaxID(receivedMessageID));
			if(message.getMessageType() == MessageType.ARE_YOU_ALIVE){
			    // System.out.println("Received alive message");
			    message.setMessageType(MessageType.ALIVE);
			} else {
			    // use dispatcher to execute the remote method for the
                // client create a new dispatcher
			    Dispatcher dispatch = new Dispatcher();
			    message =  dispatch.dispatchMessage(message);
			}
		} catch (IOException e) {
		    System.err.println("Error reading from socket");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e){
		    e.printStackTrace();
		}
		return message;
	}

	private Socket clientConn;					// socket connection from the client	
	private ObjectInputStream in;				// stream to read from
	private ObjectOutputStream out;				// stream to write to
	private MessageID messageID;				// id number of the message
	
}