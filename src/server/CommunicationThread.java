package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class CommunicationThread extends Thread {
    private Socket clientSocket;
    private int port;
    private SharedData sharedData;

    
    
    public CommunicationThread(Socket clientSocket, int port, SharedData sharedData) {
            this.clientSocket = clientSocket;
            this.port = port;
            this.sharedData = sharedData;
    }
    
    

    @Override
    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String clientMessage;
            while ((clientMessage = bufferedReader.readLine()) != null) {
                String[] parts = clientMessage.split(";");

                if (parts.length == 2) {
                    int colonna = Integer.parseInt(parts[0]);
                    char pezzo = parts[1].charAt(0);

                    if ((port == 12345 && sharedData.currentTurn == 1) || (port == 54321 && sharedData.currentTurn == 2)) {
                        int riga = sharedData.m.getRigaInserimento(colonna);
                        if (riga >= 0) {
                            boolean isInserito = sharedData.m.inserisciPezzo(colonna, pezzo);
                            if (isInserito) {
                                // Inviare messaggio di conferma al client
                                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                                String messaggio = Integer.toString(riga) + ";" + colonna;
                                output.println(messaggio);
                                output.flush();

                              
                                if (port == 12345) {
                                    //inviaAdaltroClient(54321, messaggio);
                                    sharedData.currentTurn = 2; // Passa al turno del client 2
                                } else if (port == 54321) {
                                    //inviaAdaltroClient(12345, messaggio);
                                    sharedData.currentTurn = 1; // Passa al turno del client 1
                                }
                                System.out.print(sharedData.currentTurn);
                            }
                        }
                    } 

                    // Controlla la vittoria
                    if (sharedData.m.controllaVittoria(pezzo)) {
                        System.out.print("Vittoria");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Rimuovi il thread corrente dalla mappa
            sharedData.threadMap.remove(Thread.currentThread().getName());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }

    
}
