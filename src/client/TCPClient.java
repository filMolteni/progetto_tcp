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

    // Dichiarazione di variabili di istanza
    public char pezzo = 'X';
    Color rosso = Color.RED;
    Color giallo = Color.yellow;
    boolean victory = false;

    // Riferimento alla GUI associata a questo client
    public GUI gui;

    // Costruttore con parametro GUI
    public TCPClient(GUI gui) {
        this.gui = gui;
    }

    // Metodo per la comunicazione con il server
    public void comunicazioneServer(int colonna) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    // Verifica che il client non abbia ancora vinto
                    if (victory == false) {
                        String serverAddress = "localhost";

                        // Crea una connessione TCP con il server
                        Socket clientSocket = new Socket(serverAddress, 8080);
                        OutputStream outputStream = clientSocket.getOutputStream();
                        String messageToSend = colonna + ";" + pezzo;
                        PrintWriter p = new PrintWriter(outputStream, true);

                        // Invia il messaggio al server
                        p.println(messageToSend);

                        InputStream input = clientSocket.getInputStream();
                        InputStreamReader reader = new InputStreamReader(input);
                        BufferedReader bufferedReader = new BufferedReader(reader);

                        // Attende i messaggi dal server
                        while (victory==false) {
                            String serverMessage = bufferedReader.readLine();
                            if (serverMessage == null) {
                                // Il server ha chiuso la connessione
                                break;
                            }
                            System.out.println("CLIENT: Received message from server: " + serverMessage);

                            String[] parts = serverMessage.split(";");
                            int riga = Integer.parseInt(parts[0]);
                            int colon = Integer.parseInt(parts[1]);
                            String color = parts[2];
                            String vittoria = parts[3];

                            // Aggiorna la GUI in base al messaggio ricevuto
                            if (color.equals("rosso"))
                                gui.disegnaCerchio(gui.matrixLabels[riga][colon], rosso);
                            else
                                gui.disegnaCerchio(gui.matrixLabels[riga][colon], giallo);

                            // Verifica della condizione di vittoria
                            if (vittoria.equals("vittoriaX")&& victory==false) {
                                JOptionPane.showMessageDialog(gui, "Ha vinto il cerchio rosso!");
                                victory = true;
                               
                               
                            } else if (vittoria.equals("vittoriaO")&& victory==false) {
                                JOptionPane.showMessageDialog(gui, "Ha vinto il cerchio giallo!");
                                victory = true;
                               
                               
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        worker.execute();
    }
    

    // Metodo principale
    public static void main(String[] args) {
        GUI gui = new GUI();
        TCPClient tcpClient = new TCPClient(gui);
        gui.setTCPClient(tcpClient);

        // Esecuzione dell'applicazione GUI
        SwingUtilities.invokeLater(() -> {
            // new GUI();
        });
    }
}

