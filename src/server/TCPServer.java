package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serial;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPServer {
    public static void main(String[] args) {
        int port = 12345;
        CMatrice m = new CMatrice(6, 7);
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server in ascolto sulla porta " + port);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuova connessione da: " + clientSocket.getInetAddress());
                
                // Creazione dei canali di input e output
               // Scanner input = new Scanner(clientSocket.getInputStream());
                // Create input stream to read data from the client
                InputStream input = clientSocket.getInputStream();
                InputStreamReader reader = new InputStreamReader(input);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String clientMessage;
                while ((clientMessage = bufferedReader.readLine()) != null) {
                    System.out.println("Received message from client: " + clientMessage);

                    // You can process the received message here as needed
                    String[] parts = clientMessage.split(";");
                    
                     
                    if (parts.length == 2) {
                         
                        int colonna = Integer.parseInt(parts[0]);
                        char pezzo = parts[1].charAt(0);
                        m.inserisciPezzo(colonna, pezzo);
                        System.out.print(m.stampaMatrice());
                        if(m.controllaVittoria(pezzo)){
                            System.out.print("vittoria");
                            break;
                        }
                        
                    }

                    // Respond to the client
                    String response = "Server received: " + clientMessage;
                    PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                    output.println(response);
                    
                }

                
                
                // Chiusura della connessione
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
