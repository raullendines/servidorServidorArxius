import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class SocketsControllers{

    private static Socket socket;
    public static final int PORT = 5432;
    public static final String IP = "localhost";
    static DataInputStream dataInputStream;
    static FileInputStream fileInputStream;
    static DataOutputStream dataOutputStream;
    static final String dirBase = "src/archivos/";
    static InputStream inputStream;
    public SocketsControllers(Socket socket) throws IOException {
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        inputStream = socket.getInputStream();
        dataInputStream = new DataInputStream(inputStream);
        dataInputStream = new DataInputStream(inputStream);
    }

    public static void actionServer() {
        while (true) {
            try {
                String messageAction = dataInputStream.readUTF();
                String[] msg = messageAction.split(":");

                if (msg[0].equals("Escuchar")) {
                    saveFileServer();
                } else if (msg[0].equals("Enviar")) {
                    String filename = msg[1];
                    sendFileServer(filename);
                } else if (msg[0].equals("Cerrar")){
                    System.out.println("El cliente ha salido");
                    break;
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
    
    private static void sendFileServer(String filename) {
        try{
            fileInputStream = new FileInputStream(dirBase + filename);
            byte[] buffer = new byte[4096];
            int read;
            while ((read = fileInputStream.read(buffer, 0, buffer.length)) > 0) {
                System.out.println("Enviando datos...");
                dataOutputStream.write(buffer, 0, read);
            }
            System.out.println("Completado.");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void saveFileServer(){
        try {
            String filename = dataInputStream.readUTF();
            FileOutputStream fileOutputStream = new FileOutputStream(dirBase + filename);
            byte[] buffer = new byte[4096];
            int read = dataInputStream.read(buffer);
            fileOutputStream.write(buffer, 0, read);
            System.out.println("Transferencia completada.");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
