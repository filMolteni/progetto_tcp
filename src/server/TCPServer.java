package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) {
        int port = 12345;
        int portclient=12346;
        CMatrice m = new CMatrice(6, 7);
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server in ascolto sulla porta " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                
                System.out.println("Nuova connessione da: " + clientSocket.getInetAddress());

                // Creazione dei canali di input 
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

                        int riga = m.getRigaInserimento(colonna);
                        if (riga >= 0) {
                            boolean isInserito = m.inserisciPezzo(colonna, pezzo);
                            if (isInserito) {
                                System.out.println("Inserimento effettuato nella colonna " + colonna + " alla riga " + riga);
                                // Invia la riga al client
                                // OutputStream output = clientSocket.getOutputStream();
                                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                                // byte[] sendData = Integer.toString(riga).getBytes();
                                String messaaggio= Integer.toString(riga) + ";"+colonna;
                                output.println(messaaggio );
                                output.flush();
                            } else {
                                System.out.println("Colonna piena, riprova");
                            }
                        } else {
                            System.out.println("Colonna non valida, riprova");
                        }

                        System.out.print(m.stampaMatrice());
                        if (m.controllaVittoria(pezzo)) {
                            System.out.print("vittoria");
                            break;
                        }
                    }

                  
                }

                // Chiusura della connessione
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
