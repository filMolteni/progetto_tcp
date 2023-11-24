package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class CommunicationThread extends Thread {
    private Socket clientSocket;
    private int turno;
    private SharedData sharedData;
    PrintWriter output;
    TCPServer s;
    
    
    public CommunicationThread(Socket clientSocket, int turno, SharedData sharedData,TCPServer s) throws IOException {
            this.clientSocket = clientSocket;
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            this.turno = turno;
            this.sharedData = sharedData;
            this.s=s;

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

                    
                        int riga = sharedData.m.getRigaInserimento(colonna);
                        if (riga >= 0) {
                            boolean isInserito = sharedData.m.inserisciPezzo(colonna, pezzo);
                            if (isInserito) {
                                // Inviare messaggio di conferma al client
                            String messaggio="";
                            String isVittoria=".";
                             if (sharedData.m.controllaVittoria(pezzo)) {
                                isVittoria ="vittoria"+pezzo;
                              
                            }
                            if(sharedData.currentTurn == 1 && pezzo == 'X'){
                                messaggio = Integer.toString(riga) + ";" + colonna+ ";rosso;"+ isVittoria;
                                sharedData.currentTurn=2;
                                 s.notifyAllClients(messaggio);
                            }
                            else if(sharedData.currentTurn == 2 && pezzo == 'O'){
                                messaggio = Integer.toString(riga) + ";" + colonna+ ";giallo;"+ isVittoria;
                                sharedData.currentTurn=1;
                                 s.notifyAllClients(messaggio);
                            }else{
                                sharedData.m.setCella(riga, colonna, ' ');
                            }
                              
                                
                               
                                
                              
                                
                                System.out.print(sharedData.currentTurn);
                            }
                        }
                   

                    // Controlla la vittoria
                   
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
    
    public void sendMessage(String message) {
        // invio risposta
        output.println(message);

        System.out.println(
                "Message sent to " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " : " + message);
        // System.out.println("Message sent : " + message);
        System.out.println("-------------------------");
    }
    
    
    
    
    
}
