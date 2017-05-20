


public class ServerDriver {
	public static void main(String args[]) 
    { 
        try 
        {
        	ServerInterface obj = new Server();
            // Bind this object
        	Naming.bind(obj , "RMIServer", "localhost", 6000, 2500);
        } 
        catch (Exception e) 
        { 
            e.printStackTrace();
        } 
    } 
}
