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
        while(true) {
            int i1 = scan.nextInt();
            int i2 = scan.nextInt();
            System.out.println("result: " + stub.add(i1, i2));
        }

    }
}
