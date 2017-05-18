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

import java.io.*;
import java.net.*;

/**
 * ServerCommunication creates a coomunication module to handle
 * request from the client by creating new threads of the type
 * ServerCommunicationHandler to handle the communication. 
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 */

public class ServerCommunication {


	/**
	 * Create a new ServerCommunication instance.
	 * @param port The port number the server will be located on.
	 */
	public ServerCommunication(int port){
	    // set the port number
	    this.port = port;
	    // run the server
	    this.runServer();
	}
	
	/**
	 * Runs the Server.  Listen for requests and then creates a new
	 * thread to handle the request.
	 */
	private void runServer() {
		try {
		    // create a server socket
			ServerSocket server = new ServerSocket(port);
			// display start up message
			System.out.println("Remote Object Server Started");
			while (true) {
			    // accept connections to the server and then 
			    // create a new thread to handle the connection
				Socket clientConn = server.accept();
				new ServerCommunicationHandler(clientConn).start();			
			}
		} catch (IOException e) {
			System.err.println("Error Connecting to the server");
		}
	}

	private int port;			// port number for the server
}