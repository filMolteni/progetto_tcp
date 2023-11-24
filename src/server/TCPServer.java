package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TCPServer {
    private static SharedData sharedData = new SharedData();
     ArrayList<CommunicationThread> clients = new ArrayList<CommunicationThread>();
     ServerSocket serverSocket;
    
    void startServer( int port) throws IOException{
        serverSocket = new ServerSocket(port);
        startCommunicationThread("ThreadPort1",1);
        startCommunicationThread("ThreadPort2",2);
    }
     void startCommunicationThread(String threadName, int turno) {
        new Thread(() -> {
            try {
                while (true) {
                    Socket clientSocket = serverSocket.accept();

                    CommunicationThread communicationThread = new CommunicationThread(clientSocket, turno, sharedData,this);
                    clients.add(communicationThread);
                    communicationThread.start();
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void notifyAllClients(String message) {
        for (CommunicationThread client : clients) {
            client.sendMessage(message);
        }
    }
}
