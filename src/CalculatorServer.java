/**
 * Created by mesutgurlek on 5/22/17.
 */
public class CalculatorServer {

    public static void main(String args[]){
        CalculatorInterface calculator = new Calculator();
        // args[0] : calculator name
        // args[1] : registry IP
        // args[2] : registry port
        // args[3] : object connection port
        Naming.bind(calculator, args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
    }
}
