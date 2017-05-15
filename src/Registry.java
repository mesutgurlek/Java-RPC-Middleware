import java.net.ServerSocket;
import java.net.Socket;

public class Registry {

	public Registry() {

	}

	public void runRegistrySystem(int port) {
		try {
			// create socket for the incoming requests
			ServerSocket regServer = new ServerSocket(port);
			System.out.println("Registry server is listening at port " + port + ":");

			// listen all requests and create a thread to handle that
			while(true) {
				Socket incomingCon = regServer.accept();
				// this thread will handle all requests from specific client
				new RegistryRequestHandler(incomingCon).start(); 
			}
		}
		catch(Exception e) {
			System.out.println("ERROR AT REGISTRY!");
			e.printStackTrace();
		}	
	}

	public static void main(String []args) {
		int portNo = Integer.parseInt(args[0]);
		// start registry at given port
		Registry myReg = new Registry();
		myReg.runRegistrySystem(portNo);
	}

}
