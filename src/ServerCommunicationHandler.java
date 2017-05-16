import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by aeakdogan on 16/05/2017.
 * ${CLASS}
 */
public class ServerCommunicationHandler extends Thread{
    Socket clientSocket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;

    public ServerCommunicationHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        super.run();
        try {
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
                outputStream.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendMessage(Message message){
        try {
            outputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Message receiveMessage(){
        Message message = null;
        try {
            message = (Message) inputStream.readObject();
            message =  new Dispatcher().dispatch(message);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }
}
