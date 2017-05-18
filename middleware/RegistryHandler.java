/*
 * RegistryHandler
 * Created on Nov 8, 2004 at 4:21:32 PM
 *
 * Nathan Balon
 * University of Michigan Dearborn
 * CIS 578
 * Advanced Operating Systems
 * 
 */
package nathan.middleware;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;
import java.net.InetAddress;

/**
 * RegistryHandler handles the message that are sent to the registry.
 * The registryHandler are seperate threads used to complete the requests
 * that are sent to the registry.
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 *
 */

public class RegistryHandler extends Thread{
	/**
	 * Create a new instance of RegistryHandler
	 * @param s The socket.
	 */
	RegistryHandler(Socket s) {
		clientConn = s;
//		System.out.println("Created a new registry connection: " + s.getInetAddress());
	}
	
    /**
     * Binds an object to the registry and creates a new remote object 
     * reference for the remote object.
     * @param message The message used to bind an object to the registry.
     * @return RemoteObjectReference The remote reference for the bound object.
     */
    public synchronized RemoteObjectReference bindObject(RegistryMessage message){
        // get the address of the remote object.
        InetAddress address = clientConn.getInetAddress();
        int port = message.getPortToBind();
        // get the name used to bind the object to the registry
        String objectName = message.getObjectName();
        Object o = message.getObjectToBind();
        Class c = o.getClass();
        String impName = c.getCanonicalName();
        if(impName.endsWith("Skeleton")){
            impName = impName.replace("Skeleton", "");
        }    
        // create a new remote object reference for the object
        RemoteObjectReference ror = new RemoteObjectReference(address, port, objectName, impName);
        RemoteObjectMapping mapping = null;
        // check if the name already exists
        if(nameRorLookup.containsKey(objectName)){
            mapping = nameRorLookup.get(objectName);
            // if the name already exists add to the list of replicated objects
            if(mapping.isReplicated()){
                mapping.addRemoteObject(ror);
                System.out.println("Adding new replicated object");
                // send the repicate object mapping back to front end
                int frontEndPort = mapping.getFrontEndPort();
                InetAddress frontEndAddress = mapping.getFrontEndAddress();
                try{
                    // create a socket to the front end of the replication mangager
                    // and send the information about the new object to the replication
                    // manager
                    Socket socketToFrontEnd = new Socket(frontEndAddress, frontEndPort);                    
        			ObjectOutputStream out = new ObjectOutputStream(socketToFrontEnd.getOutputStream());
        			Message replicationMessage = new Message();
        			replicationMessage.setMessageID(0);
        			replicationMessage.setMessageType(MessageType.NEW_REPLICA);
        			Vector <RemoteObjectReference> args = new Vector<RemoteObjectReference>();
        			args.add(ror);
        			replicationMessage.setArguments(args);
        			out.writeObject(replicationMessage);
                }
                catch (Exception e){
                    System.err.println("Error sending replication information");
                    e.printStackTrace();
                }
            } else {
                // if the object is not replicated add to the list with out sending
                // any messages to the front end
                mapping = new RemoteObjectMapping();
                mapping.addRemoteObject(ror);
            }
        } else {
            // create a new remote object reference              
            mapping = new RemoteObjectMapping();
            // add the reference to the registry
            mapping.addRemoteObject(ror);
            
        }
        nameRorLookup.put(objectName, mapping);
        System.out.println("Bound Object: " + objectName);
        return ror;
    }

    public void checkIfAlive(RemoteObjectMapping mapping){
        Vector replicas = mapping.getRemoteObjects();
        for(int i = 0; i < replicas.size(); i++){
            RemoteObjectReference ror = (RemoteObjectReference)replicas.get(i);
            InetAddress address = ror.getAddress();
            int port = ror.getPort();
            try{
                // test that the connection still exists
                Socket socket = new Socket(address, port);
                ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
                Message m = new Message(MessageType.ARE_YOU_ALIVE);
                outStream.writeObject(m);
                m = (Message)inStream.readObject();
            }
            catch(java.net.ConnectException e){
                // if failed to connect to remote object server 
                // remove the reference to the object from the list
                String name = ror.getName();
                RemoteObjectMapping map = nameRorLookup.get(name);
                map.removeRemoteObject(ror);
                System.out.println("Object " + ror.getName() + " at " +
                                    ror.getAddress() + " " + ror.getPort() + " no longer exits");
               // this.checkIfAlive(mapping);
            }
            catch(Exception e){    
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Checks if the FrontEnd for the application is still active.
     * @param registryObject The RemoteObjectMapping containing the frontend to check.
     * @return boolean Returns true if the FrontEnd is still able to be accessed.
     */
    public boolean checkFrontEnd(RemoteObjectMapping registryObject){
        InetAddress address = registryObject.getFrontEndAddress();
        int port = registryObject.getFrontEndPort();
        try{
            // test that the connection still exists
            Socket socket = new Socket(address, port);
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
            Message m = new Message(MessageType.ARE_YOU_ALIVE);
            outStream.writeObject(m);
            m = (Message)inStream.readObject();
        }
        catch(java.net.ConnectException e){
            System.out.println("Active replication no longer active");
            registryObject.setReplicated(false);
            return false;
        }
        catch(Exception e){    
            e.printStackTrace();
        }
        return true;
    }
    /**
     * lookup the remote object reference for an object.
     * @param message The message used to lookup the reference.
     * @return RemoteObjectReference The remote refernce for the object.
     */
    public synchronized RemoteObjectReference lookupObject(RegistryMessage message){
        RemoteObjectReference ror = null;
        // get the name of the object to lookup
        String name = message.getObjectName();        
        // if the name is in the registry get the remote reference for the object
        if(nameRorLookup.containsKey(name)){
            System.out.println("Looking up Object: " + name);
            RemoteObjectMapping registryObject = nameRorLookup.get(name);
            if(registryObject.isReplicated()){
                boolean activeReplication = this.checkFrontEnd(registryObject);
                if(activeReplication){
                    ror = registryObject.getFrontEndROR();
                } else {
                    ror = null;
                }
            } else {
                // get the remote object reference
                Vector rors = registryObject.getRemoteObjects();
                // check that the server is still active
                this.checkIfAlive(registryObject);
                // get the remote object reference to return to the client
                if(rors.size() > 0){
                    ror = (RemoteObjectReference)rors.get(0);
                }
            }           
        } else {
            System.out.println("Failed to find " + name + "in the registry");
        }
        return ror;
    }
    

    /**
     *  listAll object that are contained in the registry.
     */
    public void listAll(){
        System.out.println(nameRorLookup.toString());
    }
    /**
     *  Add a Active Replication by giving the registry the address of
     *  the front end so that clients access the front end rather than
     *  the remote object.
     *  @param message The message sent from the front end.
     *  
     */
    public synchronized RemoteObjectMapping addFrontEnd(RegistryMessage message){
        RemoteObjectMapping mapping = null;
        // get the address of the remote object.
        InetAddress address = clientConn.getInetAddress();
        int port = message.getPortToBind();
        String objectName = message.getObjectName();
        if(nameRorLookup.containsKey(objectName)){
            System.out.println("Object is all ready bound");
        } else {
            String impName = "nathan.middleware." + objectName + "Impl";
            RemoteObjectReference ror = new RemoteObjectReference(address, port, objectName, impName);
            System.out.println("Added active replication front end for: " + ror.getName());
            mapping = new RemoteObjectMapping(true, ror);
            mapping.setFrontEnd(address, port);
            nameRorLookup.put(objectName, mapping);
        //    mapping.
        }
        return mapping;
    }
    
	/**
	 * The thread of execution used to handle messages sent to the registry.
	 */
	public void run() {
		try {
		    // get the streams to read and write to
			in = new ObjectInputStream(clientConn.getInputStream());
			out = new ObjectOutputStream(clientConn.getOutputStream());
			// get the registry message
            RegistryMessage message = (RegistryMessage)in.readObject();
            // if the message is type bind attempt to bind object to registry
            if(message.getMessageType() == RegistryMessage.BIND){
                // bind the object
                RemoteObjectReference ror = this.bindObject(message);
                out.writeObject(ror);
            }
            // if message is a lookup message attempt to locate the reference 
            else if(message.getMessageType() == RegistryMessage.LOOKUP){
                RemoteObjectReference ror = this.lookupObject(message);
                out.writeObject(ror);
            }
            else if(message.getMessageType() == RegistryMessage.NEW_FRONT_END){
                RemoteObjectMapping mapping = this.addFrontEnd(message);
                out.writeObject(mapping);                
            }
            else{
                System.err.println("Invalid Message Type");
            }
		} catch(java.lang.NullPointerException e){
           // System.err.println("Failed to find Object in registry");
        } catch (Exception e) {
		    e.printStackTrace();
		}
		finally{
		    try{
		        // close the connection
		        in.close();
		        out.close();
		        clientConn.close();
		    }
		    catch(Exception e){
		        System.err.println("Error cleaning up connections");
		        e.printStackTrace();
		    }
		}
	}
	
	// initialize the static lookup table
	static{
	    nameRorLookup = new HashMap<String, RemoteObjectMapping>();
	}
	
	// hashmap to store name, remoteRefernce pairs
    private static HashMap <String, RemoteObjectMapping> nameRorLookup;

	private Socket clientConn;				// connection to the registry

	private ObjectInputStream in;			// stream to read from

	private ObjectOutputStream out;			// stream to write to
	

}

