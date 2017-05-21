import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class Registry {

    private static HashMap<String, RemoteObjectReference> lookupTable = new HashMap<>();

    public Registry() {

    }

    public static HashMap<String, RemoteObjectReference> getLookupTable() {
        return lookupTable;
    }

    public static void setLookupTable(HashMap<String, RemoteObjectReference> lookupTable) {
        Registry.lookupTable = lookupTable;
    }

    public static void main(String[] args) {
        int portNo;
        if (args.length != 1) {
            System.out.print("Enter Port No: ");
            Scanner scanner = new Scanner(System.in);
            portNo = scanner.nextInt();
        } else {
            portNo = Integer.parseInt(args[0]);
        }
        // start registry at given port
        Registry myReg = new Registry();
        myReg.runRegistrySystem(portNo);
    }

    public void runRegistrySystem(int port) {
        try {

            // create socket for the incoming requests
            ServerSocket regServer = new ServerSocket(port);
            System.out.println("Registry server is listening at port " + port + ":");

            // listen all requests and create a thread to handle that
            while (true) {
                // TODO debug delete later
                System.out.println(lookupTable.toString());

                Socket incomingCon = regServer.accept();
                // this thread will handle all requests from specific client
                new RegistryRequestHandler(incomingCon).start();
            }
        } catch (Exception e) {
            System.out.println("ERROR AT REGISTRY!");
            e.printStackTrace();
        }
    }

}
