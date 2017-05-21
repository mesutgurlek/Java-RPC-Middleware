import java.io.IOException;
import java.util.Scanner;

/**
 * Created by cagri on 16.05.2017.
 */
public class ClientTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);

        // args[0] : object name
        // args[1] : registry IP
        // args[2] : registry port
        CalculatorInterface stub = (CalculatorInterface)Naming.lookup(args[0], args[1],  Integer.parseInt(args[2]));
        while(true) {
            // Enter 2 numbers to add them up
            int i1 = scan.nextInt();
            int i2 = scan.nextInt();
            System.out.println("result: " + stub.add(i1, i2));
        }

    }
}
