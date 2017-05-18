
/*
 * ClientCommunication.java
 *
 * Communication module to run on the client computer.
 * The Communication module sends and receives messages to the server.
 *
 * Created on September 29, 2004, 4:16 AM
 */

package nathan.middleware;
import java.net.*;
import java.io.*;

/**
 * Client Communication is used to send and receive messages 
 * from the remote object.  The client communication module is
 * is passed messages to be sent from the stub.  When the results
 * are received from the remote object server they are returned to
 * the stub module.
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 */

public class ClientCommunication{


    /** Creates a new instance of ClientCommunication. */
    public ClientCommunication() {
            messageID = new MessageID();
    }
    
    /**
     * Creates a new instance of ClientCommunication.
     * @param address The address of the remote object.
     * @param portNumber The port number of the remote object.
     */
    public ClientCommunication(InetAddress address, int portNumber) {
            messageID = new MessageID();
    }  
    
    /**
     * Send a message to the server.
     * @param message The message to be sent to the server.
     */
    public void sendMessage(Message message){
        try{
            RemoteObjectReference ror = message.getRemoteObjectReference();
            // get the location of the server
            address = ror.getAddress();
            port = ror.getPort();
            // create a new socket to access the remote object
            socket = new Socket(address, port);
            // create input and output streams
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());            
            message.setMessageType(MessageType.REQUEST);           
            // increase the lamport clock
            message.setMessageID(messageID.getNextID());
            out.writeObject(message);
 //           System.out.println("Sent a message to Server:\n" + message.toString());
        }catch(java.net.ConnectException e){
            System.err.println("Unable to connect to server: the connection was closed");
            System.exit(1);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
 
    /**
     * Get a message sent from the server.
     * @return Message the message received from the server.
     */
    public Message getMessage(){
        Message message = null;
        try{
            message = (Message)in.readObject();
            // increase the lamport clock
            int receivedID = message.getMessageID();
            int id = messageID.getMaxID(receivedID);
            message.setMessageID(id);
            if(message.getMessageType() == MessageType.CLOSE_CONNECTION){
                System.out.println("No more active objects: All server down");
                System.exit(0);
            }         
        }        
        catch(java.net.SocketException e){
            System.err.println("Error unable to connect to server");
            // if unable to connect to any remote objects terminate the program
            System.exit(1);
        }
        catch(IOException e){
            System.err.println("IO Exception");
        }        
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        finally{
            // close the connections
            try{
                if(in != null){
                    in.close();
                }
                if(out != null){
                    out.close();
                }
                if(socket != null){
                    socket.close();
                }
            }
            catch(Exception e){
                System.err.println("error closing connections");
            }
        }
        return message; 
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    protected void finalize() throws Throwable{
        try{
            Message closeMessage = new Message();
            closeMessage.setMessageType(MessageType.CLOSE_CONNECTION);
            out.writeObject(closeMessage);
            if(in != null){
                in.close();
            }
            if(out != null){
                out.close();
            }
            if(socket != null){
                socket.close();
            }
        }
        catch(Exception e){
            System.err.println("Error closing socket and streams");
            e.printStackTrace();
        }
    }
    

    private Socket socket;
    private InetAddress address;
    private int port;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private MessageID messageID;
}