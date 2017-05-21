import java.util.Scanner;

public class ServerDriver {
    public static void main(String args[]) {
        int registryPort;
        int objPort;
        if (args.length != 2) {
            System.out.print("Enter Registry Port: ");
            Scanner scanner = new Scanner(System.in);
            registryPort = scanner.nextInt();

            System.out.print("Enter Object Port: ");
            objPort = scanner.nextInt();
        } else {
            registryPort = Integer.parseInt(args[0]);
            objPort = Integer.parseInt(args[1]);
        }
        try {
            ServerInterface obj = new Server();
            // Bind this object
            Naming.bind(obj, "RMIServer", "localhost", registryPort, objPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
