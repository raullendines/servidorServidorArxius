import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SocketsControllers.PORT);
        System.out.println("Esperando para la conexion en el puerto " + serverSocket.getLocalPort());
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Conectado a " + clientSocket.getInetAddress());
            SocketsControllers sC = new SocketsControllers(clientSocket);
            sC.actionServer();
            clientSocket.close();
        }
    }
}