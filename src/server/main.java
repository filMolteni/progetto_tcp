package server;

import java.io.IOException;

public class main {
    
    public static void main(String[] args) throws IOException {
        TCPServer s = new TCPServer();
        // int port1 = 12345;
        // int port2 = 54321;
        s.startServer(8080);
        // s.startCommunicationThread("ThreadPort1", port1);
        // s.startCommunicationThread("ThreadPort2", port2);
    }
}
