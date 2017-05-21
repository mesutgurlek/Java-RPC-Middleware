public class ClientDriver {
    public static void main(String arg[]) {
        Client client = new Client(arg[0], Integer.parseInt(arg[1]), Integer.parseInt(arg[2]));
        try {

            ServerInterface server = (ServerInterface) Naming.lookup("RMIServer", "localhost", 6000);  //objectname in registry
            Naming.bind(client, client.getName(), "localhost", 6000, client.getPort());

            System.out.println(client.getName() + " asks the server for a match");
            String matchName = server.match(client.getName(), client.getTimeout());

            if (matchName != null) {
                System.out.println(client.getName() + "'s call returns with " + matchName + "'s name");
                ClientInterface matchClient = (ClientInterface) Naming.lookup(matchName, "localhost", 6000);

                if (client.getName().compareTo(matchName) > 0) {
                    System.out.println(client.getName() + " waits for " + matchName + "'s handshake");
                    while (client.getHandshake() == false) ;
                    matchClient.handshake();
                    System.out.println(client.getName() + " sends a handshake to " + matchName);
                } else {
                    matchClient.handshake();
                    System.out.println(client.getName() + " sends a handshake to " + matchName);
                    System.out.println(client.getName() + " waits for " + matchName + "'s handshake");
                    while (client.getHandshake() == false) ;

                }
            } else {
                System.out.println(client.getName() + "'s call returns empty");
            }


        } catch (Exception e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("OVER");
        Naming.remove(client.getName(), "localhost", 6000);
        System.exit(0);
    }
}
