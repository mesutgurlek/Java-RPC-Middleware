import java.util.Scanner;

/**
 * Created by aeakdogan on 16/05/2017.
 * ${CLASS}
 */
public class Server {
    public static void main(String[] args){
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
        CalculatorInterface calc = new Calculator();
        Object skeleton = Naming.bind(calc, "object", "localhost", registryPort, objPort);
    }
}
