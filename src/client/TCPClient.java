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
    
        public  GUI gui;
    
        public TCPClient(GUI gui) {
            this.gui = gui;
            // ... altre inizializzazioni ...
        }
        public void comunicazioneServer(int colonna, Color colore) {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    try {
                        String serverAddress = "localhost";
                        int serverPort = 12345;
    
                        Socket clientSocket = new Socket(serverAddress, serverPort);
                        OutputStream outputStream = clientSocket.getOutputStream();
                        String messageToSend = colonna + ";X";
                        PrintWriter p = new PrintWriter(outputStream, true);
    
                        p.println(messageToSend);
    
                        InputStream input = clientSocket.getInputStream();
                        InputStreamReader reader = new InputStreamReader(input);
                        BufferedReader bufferedReader = new BufferedReader(reader);
    
                        String serverMessage;
                        System.out.println("attesa messaggio");
                        while ((serverMessage = bufferedReader.readLine()) != null) {
                            System.out.println("Received message from server: " + serverMessage);
                            String[] parts = serverMessage.split(";");
                            int riga = Integer.parseInt(parts[0]);
                            int colon = Integer.parseInt(parts[1]);
                            System.out.println("riga: " + riga);
                            System.out.println("colonna: " + colon);
    
                            // Aggiorna l'interfaccia utente nell'Event Dispatch Thread
                            SwingUtilities.invokeLater(() -> {
                                gui.disegnaCerchio(gui.matrixLabels[riga][colon], Color.RED);
                            });
                        }
    
                        clientSocket.close();
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
