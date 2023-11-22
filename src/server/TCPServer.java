package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TCPServer {
    private static SharedData sharedData = new SharedData();

    public static void main(String[] args) {
        int port1 = 12345;
        int port2 = 54321;

        startCommunicationThread("ThreadPort1", port1);
        startCommunicationThread("ThreadPort2", port2);
    }

    static void startCommunicationThread(String threadName, int port) {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(port);

                while (true) {
                    Socket clientSocket = serverSocket.accept();

                    CommunicationThread communicationThread = new CommunicationThread(clientSocket, port, sharedData);
                    communicationThread.start();
                    SharedData.threadMap.put(threadName, communicationThread);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
