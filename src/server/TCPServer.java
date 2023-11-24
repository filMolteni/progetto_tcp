package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

// Questa classe gestisce il server TCP
public class TCPServer {
    private static SharedData sharedData = new SharedData();
    private ArrayList<CommunicationThread> clients = new ArrayList<>();
    private ServerSocket serverSocket;

    // Metodo per avviare il server sulla porta specificata
    void startServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        startCommunicationThread("Thread1");
        startCommunicationThread("Thread2");
    }

    // Metodo per avviare un nuovo thread per la comunicazione con un client
    void startCommunicationThread(String threadName) {
        new Thread(() -> {
            try {
                while (true) {
                    Socket clientSocket = serverSocket.accept();

                    CommunicationThread communicationThread = new CommunicationThread(clientSocket, sharedData, this);
                    clients.add(communicationThread);
                    communicationThread.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Metodo per notificare tutti i client con un messaggio
    public void notifyAllClients(String message) {
        for (CommunicationThread client : clients) {
            client.sendMessage(message);
        }
    }
}

