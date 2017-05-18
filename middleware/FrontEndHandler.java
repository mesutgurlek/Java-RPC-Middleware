/*
 * 
 * FrontEndHandler 
 * Created on Nov 25, 2004 at 4:10:24 AM 
 * Nathan Balon 
 * University of Michigan Dearborn 
 * CIS 578 Advanced Operating Systems
 * 
 */
package nathan.middleware;

import java.io.*;
import java.net.*;
import java.util.Vector;

/**
 * FrontEndHandler creates new threads to handle the messages used for active
 * replication.  The FrontEndHandler handles all communication between the
 * replicated objects and the server when active replication is used.
 *
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 */

public class FrontEndHandler extends FrontEnd implements Runnable {

    /**
     * Creates a new instance of the FrontEndHandler.
     * @param socket The socket received from the FrontEnd server.
     */
    public FrontEndHandler(Socket socket) {
        connection = socket;
    }

    /**
     * Starts a new thread of execution.
     */
    public void start() {
        this.run();
    }

    /**
     * @see java.lang.Runnable#run()
     */
    public void run() {
        try {
            // get the input and output streams
            in = new ObjectInputStream(connection.getInputStream());
            out = new ObjectOutputStream(connection.getOutputStream());
            // get the message that was sent
            Message m = this.getMessage();
            if (m.getMessageType() != MessageType.NEW_REPLICA) {
                this.sendMessage(m);
            }
            // close the connections
            out.close();
            in.close();
            connection.close();
        }
        catch (Exception e) {
            System.err.println("Error in run");
            e.printStackTrace();
        }
    }

    /**
     * Get the message that was sent to the server.
     * @return Message the message that was sent.
     */
    public synchronized Message getMessage() {
        Message message = null;
        try {
            // reading the message in from the socket
            message = (Message) in.readObject();
        }
        catch (Exception e) {
            System.err.println("Error reading message");
        }
        // if the message type is replica add the replication information
        if (message.getMessageType() == MessageType.NEW_REPLICA) {
            // get the arguments from the message
            Vector args = message.getArguments();
            // get the ror for the new replica
            RemoteObjectReference newRor = (RemoteObjectReference) args.get(0);
            // if no other replicas exist add the ror, no checking is needed
            if(replicatedObjects.size() == 0){
                replicatedObjects.addRemoteObject(newRor);
            }
            // if other object are already active get the state of one of
            // the servers so the objects will be consistent.
            else if (replicatedObjects.size() >= 1) {
                // make sure other servers are active
                this.checkIfAlive();
                // get the ror for an active object
                Message m = new Message();
                // get a vector of the remote object references to other servers.
                Vector activeServers = replicatedObjects.getRemoteObjects();
                for (int i = 0; i < activeServers.size(); i++) {
                    RemoteObjectReference ror = (RemoteObjectReference)activeServers.get(i);
                    if((ror.getPort() == newRor.getPort()) && (ror.getAddress().equals(newRor.getAddress()))){
                        // if the address and port are the same don't process
                        replicatedObjects.removeRemoteObject(ror);
                        activeServers.remove(ror);
//                        System.out.println("Removed duplicate object");
                        continue;                        
                    }
                    
                    // create a new message to be sent to get a replica
                    InetAddress address = ror.getAddress();
                    int port = ror.getPort();
                    try {
                        // create a new connection
                        Socket s = new Socket(address, port);
                        ObjectOutputStream writeStream = new ObjectOutputStream(
                                s.getOutputStream());
                        ObjectInputStream readStream = new ObjectInputStream(s
                                .getInputStream());
                        // set the message information.
                        m.setMethodName("getReplicatedObject");
                        m.setMessageID(messageID.getNextID());
                        m.setMessageType(MessageType.REQUEST);
                        m.setRemoteObjectReference(ror);
                        // send the request to get a replicated object from a server
                        writeStream.writeObject(m);
                        // get the message containing the replicated object
                        m = (Message) readStream.readObject();
                    }
                    catch (java.net.ConnectException e) {
                        System.err.println("Unable to connect");
                    }
                    catch (Exception e) {
                        System.err.println("Error getting to replica");
                    }
                }
                boolean notSuccessful = true;
                // keeping tring to send the replication request until the
                // new remote object becomes active
                while(notSuccessful){
                    try {
                        // send the replicated object to the new server
                        m.setMessageType(MessageType.REQUEST);
                        m.setMethodName("setReplicatedObject");
                        m.setMessageID(messageID.getNextID());
                        m.setRemoteObjectReference(newRor);
                        Socket sock = new Socket(newRor.getAddress(), newRor.getPort());
                        ObjectOutputStream write = new ObjectOutputStream(sock.getOutputStream());
                        ObjectInputStream read = new ObjectInputStream(sock.getInputStream());
                        write.writeObject(m);
                        read.readObject();
                        // set to new replica so the message won't be further processed
                        m.setMessageType(MessageType.NEW_REPLICA);
                        // add the new remote object reference to the list
                        replicatedObjects.addRemoteObject(newRor);
                        notSuccessful = false;
                    }
                    catch (Exception e) {
                        notSuccessful = true;
                        try{
                            Thread.sleep(100);
                        }
                        catch(java.lang.InterruptedException ex){
                            System.err.println("Interrupted Exception while " +
                                               "trying to set replica");
                        }

                    }
                }
            }
            System.out.println("Added Replicated Object " + newRor.getName()
                    + ", IP: " + newRor.getAddress() + ", Port: "
                    + newRor.getPort());
        }
        else if (message.getMessageType() == MessageType.ARE_YOU_ALIVE) {
            message.setMessageType(MessageType.ALIVE);
        }
        else if(replicatedObjects.size() == 0){
            // no remote object are active send message to the client
            // to close the connection.
            message.setMessageType(MessageType.CLOSE_CONNECTION);
            message.setMessageID(messageID.getNextID()); 
            message.setMethodName("close"); 
        }
        else{
            // process the message by sending it to all the replicas
            message = this.processMessage(message);
        }
        return message;
    }

    /**
     * sendMessage returns the results of a remote invocation to the client.
     * @param message The results of the remote invocation.
     */
    public synchronized void sendMessage(Message message) {
        try {
            out.writeObject(message);
        }
        catch (Exception e) {
            System.err.println("Error sending reply message");
            e.printStackTrace();
        }
    }

    /**
     * ProcessMessage send the message to each of the acitve replicas.
     * @param message The message sent from the client to be processed.
     * @return Message The message returned from the server.
     */
    
     public synchronized Message processMessage(Message message) { 
         Message returnMessage = null; 
         if (this.checkIfAlive()) { 
             Vector replicas = replicatedObjects.getRemoteObjects(); 
             System.out.println("Executing Method: " + message.getMethodName());
             try { 
                 // send the reguest message to all of the active replicas
                 for (int i = 0; i < replicas.size(); i++) { 
                     // the remote object reference for a server
                     RemoteObjectReference rorToProcess = (RemoteObjectReference)replicas.get(i); 
                     // get the address and port number of the server
                     InetAddress address = rorToProcess.getAddress(); 
                     int port = rorToProcess.getPort(); 
                     // create a new socket to the replica
                     Socket socketToReplica = new Socket(address, port); 
                     ObjectOutputStream outToReplica = new ObjectOutputStream(socketToReplica.getOutputStream()); 
                     ObjectInputStream inFromReplica = new ObjectInputStream( socketToReplica.getInputStream());                     
                     message.setRemoteObjectReference(rorToProcess);
                     // send and receive the message from the replica
                     outToReplica.writeObject(message); 
                     returnMessage = (Message)inFromReplica.readObject(); 
                     // System.out.println("Received Message : " + message);
                     Vector retArg = returnMessage.getArguments();
 /*                    if(message.getMessageType() != MessageType.ARE_YOU_ALIVE){
                        System.out.print("Returned value : "); 
                     } 
                     for(int j = 0; j < retArg.size(); j++){ 
                         System.out.println(retArg.get(j)); 
                     } 
*/                     
                  } 
              } catch (java.net.ConnectException e) { 
               //   replicatedObjects.removeReplicatedObject(rorToProcess); 
              } catch (Exception e) { 
                  System.err.println("Exception processing the message");
                  // e.printStackTrace();
              } 
          } else {
              // if no servers are active send a message to the client to
              // close the connection, since the request will not be able to 
              // be completed.
              returnMessage = new Message();
              returnMessage.setMessageType(MessageType.CLOSE_CONNECTION);
              returnMessage.setMessageID(messageID.getNextID()); 
              returnMessage.setMethodName("close"); 
        }     	 
         //   return a reply message
         return returnMessage;
     }
     
    /**
     * Check that all servers are still alive by sending them are you alive
     * messages. If a server is down remove it from the list of replicas.
     * @return boolean true if there are active replicas.
     */

    public synchronized boolean checkIfAlive() {
        // get the list of all the replicated objects
        Vector replicas = replicatedObjects.getRemoteObjects();
        RemoteObjectReference rorToProcess = null;
        try {
            // send each server a message to test if it is still up
            for (int i = 0; i < replicas.size(); i++) {
                // get the information about the server
                rorToProcess = (RemoteObjectReference) replicas.get(i); 
                InetAddress address = rorToProcess.getAddress();
                int port = rorToProcess.getPort();
                // create a new socket
                Socket socketToReplica = new Socket(address, port);
                ObjectOutputStream outToReplica = new ObjectOutputStream(
                        socketToReplica.getOutputStream());
                ObjectInputStream inFromReplica = new ObjectInputStream(
                        socketToReplica.getInputStream());
                // create the message to send
                Message message = new Message(MessageType.ARE_YOU_ALIVE);
                message.setMessageID(messageID.getNextID());
                // method name has not effect on operation
                message.setMethodName("Check Connection");
                message.setRemoteObjectReference(rorToProcess);
                // send the message to the server
                outToReplica.writeObject(message);
                // get the message from the server
                Message returnMessage = (Message) inFromReplica.readObject();
                inFromReplica.close();
                outToReplica.close();
                socketToReplica.close();
            }
        }
        catch (java.net.ConnectException e) {
            //if the server can't be reached remove it from the list
            replicatedObjects.removeRemoteObject(rorToProcess);
            System.out.println("Replicated object failed, removing :"
                    + rorToProcess.getName() + " "
                    + rorToProcess.getAddress() + " " 
                    + rorToProcess.getPort());
            // check if an other servers are down this.checkIfAlive();
        }
        catch (ClassNotFoundException e) {
            System.err.println("Class not found exception");
        }
        catch (Exception e) {
            System.err.println("Error checking if processes are alive");
        }
        // check to see if there are any servers active 
        Vector activeObjects = replicatedObjects.getRemoteObjects();
        if (activeObjects.size() == 0) {
            // return false no servers are active
            return false;
        }
        else {
            // return true the request can be handled
            return true;
        }
    }


    private ObjectInputStream in;

    private ObjectOutputStream out;

    private Socket connection;

}