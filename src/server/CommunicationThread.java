package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// Questa classe gestisce la comunicazione con un singolo client
public class CommunicationThread extends Thread {
    private Socket clientSocket;
    private SharedData sharedData;
    private PrintWriter output;
    private TCPServer server;

    // Costruttore che inizializza la connessione e i dati condivisi
    public CommunicationThread(Socket clientSocket, SharedData sharedData, TCPServer server) throws IOException {
        this.clientSocket = clientSocket;
        output = new PrintWriter(clientSocket.getOutputStream(), true);
        this.sharedData = sharedData;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String clientMessage;
            // Legge i messaggi inviati dal client
            while ((clientMessage = bufferedReader.readLine()) != null) {
                String[] parts = clientMessage.split(";");

                if (parts.length == 2) {
                    int colonna = Integer.parseInt(parts[0]);
                    char pezzo = parts[1].charAt(0);

                    int riga = sharedData.m.getRigaInserimento(colonna);
                    if (riga >= 0) {
                        boolean isInserito = sharedData.m.inserisciPezzo(colonna, pezzo);
                        if (isInserito) {
                            String messaggio = "";
                            String isVittoria = ".";
                            if (sharedData.m.controllaVittoria(pezzo)) {
                                isVittoria = "vittoria" + pezzo;
                            }
                            if (sharedData.currentTurn == 1 && pezzo == 'X') {
                                messaggio = Integer.toString(riga) + ";" + colonna + ";rosso;" + isVittoria;
                                sharedData.currentTurn = 2;
                                server.notifyAllClients(messaggio);
                            } else if (sharedData.currentTurn == 2 && pezzo == 'O') {
                                messaggio = Integer.toString(riga) + ";" + colonna + ";giallo;" + isVittoria;
                                sharedData.currentTurn = 1;
                                server.notifyAllClients(messaggio);
                            } else {
                                sharedData.m.setCella(riga, colonna, ' ');
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Metodo per inviare un messaggio al client
    public void sendMessage(String message) {
        output.println(message);
        System.out.println(
                "Message sent to " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " : " + message);
        System.out.println("-------------------------");
    }
}
