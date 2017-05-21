import java.io.IOException;
import java.util.Scanner;

/**
 * Created by cagri on 16.05.2017.
 */
public class ClientTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        int portNo;

        if (args.length != 1) {
            System.out.print("Enter Port No: ");
            Scanner scanner = new Scanner(System.in);
            portNo = scanner.nextInt();
        } else {
            portNo = Integer.parseInt(args[0]);
        }


        CalculatorInterfaceStub stub = (CalculatorInterfaceStub) Naming.lookup("object", "localhost", portNo);
        while (true) {
            int i1 = scan.nextInt();
            int i2 = scan.nextInt();
            System.out.println("result: " + stub.add(i1, i2));
        }

    }
}
