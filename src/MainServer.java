import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    static ServerSocket serverSocket;
    static Socket clientSocket;

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(SocketsControllers.PORT);
        System.out.println("Esperando para la conexion en el puerto " + serverSocket.getLocalPort());
        while (true) {
            clientSocket = serverSocket.accept();
            showFiles();
            System.out.println("Conectado a " + clientSocket.getInetAddress());
            SocketsControllers sC = new SocketsControllers(clientSocket);
            sC.actionServer();
            clientSocket.close();
        }
    }
    public static void showFiles() throws IOException {
        File folder = new File(SocketsControllers.dirBase);
        File[] files = folder.listFiles();

        ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());

        oos.writeObject(files);
    }
}