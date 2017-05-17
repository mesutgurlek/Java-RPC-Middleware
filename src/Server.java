
/**
 * Created by aeakdogan on 16/05/2017.
 * ${CLASS}
 */
public class Server {
    public static void main(String[] args){
        Object object = Naming.bind(new Object(), "object", "localhost", 6000, 3000);
    }
}
