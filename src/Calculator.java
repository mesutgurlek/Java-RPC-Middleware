import java.io.Serializable;

/**
 * Created by cagri on 17.05.2017.
 */
public class Calculator implements CalculatorInterface, Serializable{
    public int add(int x, int y) {
        return x+y;
    }
}
