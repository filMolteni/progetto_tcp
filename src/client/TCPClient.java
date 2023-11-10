package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 12345;
        String messageToSend = "5;X";

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("Connesso al server: " + serverAddress + ":" + serverPort);

            OutputStream outputStream = socket.getOutputStream();
            byte[] sendData = messageToSend.getBytes();
            outputStream.write(sendData);
            outputStream.flush();

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
