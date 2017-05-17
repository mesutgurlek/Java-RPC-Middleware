import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by cagri on 16.05.2017.
 */
public class ClientTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        int port = Integer.parseInt(args[0]);

        CalculatorInterfaceStub stub = (CalculatorInterfaceStub)Naming.lookup("object", "localhost",  6000);
        System.out.println(stub.ror.getAddress() + "-" + stub.ror.getPort());
        System.out.println(stub.add(50, 60));
        System.out.println(stub.add(50, 80));
    }
}
