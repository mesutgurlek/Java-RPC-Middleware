
/**
 * Created by aeakdogan on 16/05/2017.
 * ${CLASS}
 */
public class Server {
    public static void main(String[] args){
        CalculatorInterface calc = new Calculator();
        Object skeleton = Naming.bind(calc, "object", "localhost", 6000, 5000);
        ServerCommunicationModule module = new ServerCommunicationModule(5000);

        module.runServer();


    }
}
