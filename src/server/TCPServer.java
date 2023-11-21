package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static int i;
    static CMatrice m = new CMatrice(6, 7);
    static int currentTurn = 1; // turno del client 1

    public static void main(String[] args) {
        int port1 = 12345;
        int port2 = 54321;

        System.out.println("Server in ascolto sulla porta " + port1 + " e " + port2);

        new Thread(() -> comunicazioneClient(port1)).start();
        new Thread(() -> comunicazioneClient(port2)).start();
    }

    static void comunicazioneClient(int port) {

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            //System.out.println("Server in ascolto sulla porta " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
               // System.out.println("Nuova connessione da: " + clientSocket.getInetAddress());

                try {
                    InputStream input = clientSocket.getInputStream();
                    InputStreamReader reader = new InputStreamReader(input);
                    BufferedReader bufferedReader = new BufferedReader(reader);

                    //System.out.println("Attesa messaggio client");

                    String clientMessage;
                    while ((clientMessage = bufferedReader.readLine()) != null) {
                       // System.out.println("Received message from client: " + clientMessage);

                        String[] parts = clientMessage.split(";");

                        if (parts.length == 2) {
                            int colonna = Integer.parseInt(parts[0]);
                            char pezzo = parts[1].charAt(0);

                            if ((port == 12345 && currentTurn == 1) || (port == 54321 && currentTurn == 2)) {
                                int riga = m.getRigaInserimento(colonna);
                                if (riga >= 0) {
                                    boolean isInserito = m.inserisciPezzo(colonna, pezzo);
                                    if (isInserito) {
                                        i++;
                                         System.out.println(clientSocket.getPort());
                                        //System.out.println("Inserimento effettuato nella colonna " + colonna + " alla riga " + riga);
                                        PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                                     

                                       

                                        String messaggio = Integer.toString(riga) + ";" + colonna;
                                        output.println(messaggio);
                                        output.flush();

                                        if (port == 12345) {
                                            inviaAdaltroClient(54321, messaggio);
                                            currentTurn = 2; // Passa al turno del client 2
                                        } else if (port == 54321) {
                                            System.out.println(1111);
                                            inviaAdaltroClient(12345, messaggio);
                                            currentTurn = 1; // Passa al turno del client 1
                                        }
                                    } else {
                                        //System.out.println("Colonna piena, riprova");
                                    }
                                } else {
                                    //System.out.println("Colonna non valida, riprova");
                                }
                            } else {
                                //System.out.println("Non è il tuo turno, riprova");
                            }

                            //System.out.print(m.stampaMatrice());
                            if (m.controllaVittoria(pezzo)) {
                                System.out.print("Vittoria");
                                break;
                            }
                        }

                        break;
                    }
                } finally {
                    // Chiusura della connessione
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void inviaAdaltroClient(int port, String messaggio) {
        try {
            
            Socket clientSocket = new Socket("localhost", port);
            System.out.println("NUOVA CONNESSIONE DA: " + clientSocket.getInetAddress());
            System.out.println(port);
             System.out.println("MESSAGGIO DA INVIARE: " + messaggio);
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            output.println(messaggio);
            output.flush();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
