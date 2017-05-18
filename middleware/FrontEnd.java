/*
 * FrontEnd 
 * Created on Nov 24, 2004 at 11:52:56 PM 
 * 
 * Nathan Balon 
 * University of Michigan Dearborn 
 * CIS 578 Advanced Operating Systems
 * 
 */

package nathan.middleware;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * FrontEnd is the front end used for fault tolerance by implementing active
 * replication of the objects in the system. The front is responisible for for
 * sending messages on behalf of the client to all of the replicated objects.<br /><br />
 * 
 * To run the front end: FrontEnd [Name] [Registry IP] [RegistryPort] [FrontEnd Port].<br /><br />
 * Name is the name of the used to bind the front end in the registry.<br />
 * Registry IP is the ip address of the registry the front end wish to bind
 * the replicated object to.<br />
 * Registry Port is the port number that the registry is listening on.<br />
 * FrontEnd Port is the port number that the port will listen on.<br />
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 */

public class FrontEnd {

    /**
     * Creates a new instance of FrontEnd
     * @param port the port number the server will run on.
     */
    public FrontEnd(int port) {
        this.port = port;
        messageID = new MessageID();
    }

    /**
     * Creates a new instance of FrontEnd
     */
    public FrontEnd() {
        messageID = new MessageID();
    }

    /**
     * Runs the server by creating new threads to handle messages as they come
     * in.
     */
    public void runServer() {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Active Replication FrontEnd Started");
            while (true) {
                // accept a connection to the front end server
                Socket socket = server.accept();
                // start a new thread to handle communication
                new FrontEndHandler(socket).start();
            }
        }
        catch (IOException e) {
            System.err.println("Error Connecting to the server");
        }
    }

    /**
     * Set the information about the registry that the replication manager will
     * use.
     * @param address The address of the registry.
     * @param port The port number of the registry.
     */
    public void setRegistryInfo(String address, int port) {
        registryAddress = address;
        registryPort = port;
    }

    /**
     * The main entry point into the active replication manager.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        // check that the port number was supplied
        if ((args.length < 4) || (args[0].equals("-h"))
                || (args[0].equals("--help"))) {
            System.err.println("Usage: FrontEnd [Name] [Registry IP] [RegistryPort] [FrontEnd Port]");
            System.exit(0);
        }
        String name = args[0];
        String address = args[1];
        // get the port number
        int registryPortNumber = Integer.parseInt(args[2]);
        int portNumber = Integer.parseInt(args[3]);
        // use the naming servie to register the front end with the registry
        replicatedObjects = Naming.createFrontEnd(name, address,
                registryPortNumber, portNumber);
        // create the registry
        FrontEnd frontEnd = new FrontEnd(portNumber);
        frontEnd.setRegistryInfo(address, registryPortNumber);
        if (replicatedObjects == null) {
            System.err.println("Object is already bound");
            System.exit(1);
        }
        else {
            // run the registry
            frontEnd.runServer();
        }
    }

    protected MessageID messageID; 								// messsage ID to use in messages

    protected static RemoteObjectMapping replicatedObjects;		// list of replicated objects
                                                                                                                              // objects
    private int port; 											// server port

    protected int registryPort; 								// registry port

    protected String registryAddress; 							// registry address.
}

