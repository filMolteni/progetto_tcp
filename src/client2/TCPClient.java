package client2;
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

    public char pezzo = 'O';
    Color rosso = Color.RED ;
    Color giallo = Color.yellow ;
    int serverPort = 12345;
    boolean victory=false;

    public GUI gui;
    public TCPClient(GUI gui) {
        this.gui = gui;
       
    }

    public void comunicazioneServer(int colonna) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    if(victory == false){
                        String serverAddress = "localhost";

                        //Socket clientSocket = new Socket(serverAddress, serverPort);
                        Socket clientSocket = new Socket(serverAddress, 8080);
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
                            String color = parts[2];
                            String vittoria = parts[3];
                            // SwingUtilities.invokeLater(() -> {
                            // });
                            if(color.equals("rosso"))
                            gui.disegnaCerchio(gui.matrixLabels[riga][colon], rosso);
                            else 
                            gui.disegnaCerchio(gui.matrixLabels[riga][colon], giallo);
                            // Ciclo per leggere i messaggi successivi
                        
                            if(vittoria.equals("vittoriaX")){
                                JOptionPane.showMessageDialog(gui, "Ha vinto il cerchio rosso!");
                                victory = true;
                            }else if(vittoria.equals("vittoriaO")){
                                JOptionPane.showMessageDialog(gui, "Ha vinto il cerchio rosso!");
                                victory = true;
                            }
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
    public void simulaPressioneBottone() {
        // Simula la pressione del bottone una sola volta all'avvio
        int colonna = 0; // Sostituisci con la colonna desiderata
        comunicazioneServer(colonna);
    }
    public static void main(String[] args) {
        GUI gui = new GUI();
        TCPClient tcpClient = new TCPClient(gui);
        gui.setTCPClient(tcpClient);
    
        SwingUtilities.invokeLater(() -> {
            tcpClient.simulaPressioneBottone();
        });
    }




}
