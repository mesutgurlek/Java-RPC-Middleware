/*
 * @author  Nathan Balon
 * Advance Operating Systems CIS 578
 * University of Michigan Dearborn
 * Remote Method Invocation Middleware Project
 */

package nathan.middleware;

import java.net.*;


/**
 * Registry is used to bind a remote object and 
 * allow clients to lookup objects to use the 
 * service provided by those objects.<br />
 * 
 * To run the registry: Registry [Port Number]<br /><br />
 * 
 * Port number is the port number that the Registry will
 * listen to.<br />
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 *
 */
public class Registry{

    /**
     * Creates a new instance of the Registry.
     */
    public Registry() {
    }

    /**
     * runregistry start the registry so it can receive messages.
     * The Registry creates new threads to handle communication.
     * @param portNumber The port number the Registry will run on.
     */
    public void runRegistry(int portNumber){
        try{
            // create a server socket 
            ServerSocket server = new ServerSocket(portNumber);
            System.out.println("Registry started");
            while (true) {
                // listen for connections then create a new thread to handle to connection
                Socket clientConn = server.accept();
                new RegistryHandler(clientConn).start();
            }
        }
        catch(Exception e){
            System.err.println("Error running the registry");
            e.printStackTrace();
        }
    }
    
    /**
     * The main entry point to the Registry application.
     * @param args The commnad line arguments.  The port number is required.
     */
    public static void main(String []args){
        int portNumber = 0;
        // check that the port number was supplied
        if((args.length == 1) && (args[0].equals("-h") || args[0].equals("--help"))){
            System.err.println("Usage: Registry [port #]");
            System.exit(0);
        }
        // get the port number
        if(args.length == 1){
            portNumber = Integer.parseInt(args[0]);
            // create the registry 
            Registry registry = new Registry();
            // run the registry
            registry.runRegistry(portNumber);
        } else {
            // create the registry 
            Registry registry = new Registry();
            // run the registry
            System.out.println("Starting registry on default port 3000");
            registry.runRegistry(3000);            
        }

    }    

}
