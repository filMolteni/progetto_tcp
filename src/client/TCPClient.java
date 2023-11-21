package client;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {

    //client1
    public char pezzo = 'X';
    Color colore = Color.RED ;
    Color colore2 = Color.yellow ;
    int serverPort = 12345;

    public GUI gui;
    public TCPClient(GUI gui) {
        this.gui = gui;
       
    }

    public void comunicazioneServer(int colonna) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    String serverAddress = "localhost";

                    //Socket clientSocket = new Socket(serverAddress, serverPort);
                    Socket clientSocket = new Socket(serverAddress, 12345);
                    OutputStream outputStream = clientSocket.getOutputStream();
                    String messageToSend = colonna + ";" + pezzo;
                    PrintWriter p = new PrintWriter(outputStream, true);

                    p.println(messageToSend);

                    InputStream input = clientSocket.getInputStream();
                    InputStreamReader reader = new InputStreamReader(input);
                    BufferedReader bufferedReader = new BufferedReader(reader);

                    while (true) {
                        String serverMessage = bufferedReader.readLine();
                        if (serverMessage == null) {
                            // Il server ha chiuso la connessione
                            break;
                        }
                        System.out.println("CLIENT 1:Received message from server: " + serverMessage);

                        String[] parts = serverMessage.split(";");
                        int riga = Integer.parseInt(parts[0]);
                        int colon = Integer.parseInt(parts[1]);

                        SwingUtilities.invokeLater(() -> {
                            gui.disegnaCerchio(gui.matrixLabels[riga][colon], colore);
                        });

                        // Ciclo per leggere i messaggi successivi
                        
                        String nextMessage;
                        while ((nextMessage = bufferedReader.readLine() )!= null) {
                           //if (nextMessage == null || nextMessage.isEmpty()) {break;  }

                            // Elabora il messaggio successivo, ad esempio, aggiorna l'interfaccia utente
                            System.out.println("MESSEGGIO IMPORTANTE: " + nextMessage);
                            String[] parts2 = nextMessage.split(";");
                            int riga2 = Integer.parseInt(parts[0]);
                            int colon2 = Integer.parseInt(parts[1]);

                        SwingUtilities.invokeLater(() -> {
                            gui.disegnaCerchio(gui.matrixLabels[riga2][colon2], colore2);
                        });
                            
                        }
                    }

                    
                    // clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        worker.execute();
    }
    
    public static void main(String[] args) {
        GUI gui = new GUI();
        TCPClient tcpClient = new TCPClient(gui);
        gui.setTCPClient(tcpClient);

        SwingUtilities.invokeLater(() -> {
            //new GUI();
        });
    }



}
