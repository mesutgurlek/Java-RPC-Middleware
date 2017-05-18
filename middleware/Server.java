/*
 * Created on Oct 27, 2004
 * 
 * @author Nathan Balon
 * Middleware Application
 * CIS 578 - Advanced Operating Systems 
 * U 0f M Dearborn 
 * @version 1.0
 */ 
package nathan.middleware;

import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * ServerApplication starts the remote object so the client can call the
 * remote methods on the object.<br /><br />
 * 
 * To start the server: Server requires the arguments [Object Name] 
 * [Registry IP] [Registry Port] [Server Port].<br /<br />
 *
 * Object name is the name to be used for the remote object.<br />
 * Registry Ip is the ip address of the registry the server will use to bind the object.<br />
 * Registry port is the port number that registry is listening on.<br />
 * Server port is the port number which the server will listen on.<br />
 *
 *  @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 */

public class Server {
    /**  
     *  main entry point to the server application
     *  @param args the command line arguments.
     * 	Server requires the arguments [Object Name] [Registry IP] [Registry Port] [Server Port]. 
     *  Object name is the name to be used for the remote object.
     *  Registry Ip is the ip address of the registry the server will use to bind the object.
     *  Registry port is the port number that registry is listening on.
     *  Server port is the port number which the server will listen on.
     */
    public static void main(String[] args) {
        boolean exit = false;
        String objectName = null;			// name of the for object
        int serverPort = 0;					// port the server will use
        int registryPort = 0;				// registry port number
        String registryIP = null;			// ip address of the registry
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        // display the rmi server has started
        System.out.println("RMI Student List Server");
        // create a new server list
        StudentList list = new StudentListImpl();
        // if the user supplied all the args
        if(args.length == 4){
            objectName = args[0];
            registryIP = args[1];
            registryPort = Integer.parseInt(args[2]);
            serverPort = Integer.parseInt(args[3]);
            // create the new remote object
            StudentList serverList = (StudentList)Naming.rebind(objectName, list, 
                    								registryIP , registryPort, serverPort);
        }
        else if((args.length == 1) && 
                ((args[0].equals("-h"))|| (args[0].equals("--help")))){
            	// display help to the user
                System.out.println("Usage: Server [Object Name] [Registry IP] [Registry Port] [Server Port]");
        } else{
            // create the defualt implentation
            StudentList serverList = (StudentList)Naming.rebind("StudentList", 
                    									list, "localhost" , 3000, 5000);
        } 

    }
}