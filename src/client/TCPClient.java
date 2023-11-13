package client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient extends JFrame {
    private JButton[] columnButtons;
    private JLabel[][] matrixLabels; // Matrice di etichette per rappresentare la griglia
    private Socket socket;

    public TCPClient(Socket socket) {
        this.socket = socket;
        setTitle("Forza 4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 7)); // 7 righe x 7 colonne

        columnButtons = new JButton[7]; // Array di bottoni per le colonne
        matrixLabels = new JLabel[6][7]; // Matrice di etichette 6x7 per rappresentare la griglia

        for (int i = 0; i < 7; i++) {
            columnButtons[i] = new JButton("Colonna " + (i + 1));
            add(columnButtons[i]);

            final int colonnaSelezionata = i;
            columnButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    inviaMossaAlServer(colonnaSelezionata);
                }
            });
        }

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                matrixLabels[row][col] = new JLabel(" ");
                matrixLabels[row][col].setHorizontalAlignment(SwingConstants.CENTER);
                matrixLabels[row][col].setFont(new Font("Arial", Font.BOLD, 24));
                add(matrixLabels[row][col]);
            }
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void inviaMossaAlServer(int colonna) {
        try {
            String serverAddress = "localhost";
            int serverPort = 12345;

            Socket clientSocket = new Socket(serverAddress, serverPort);
            OutputStream outputStream = clientSocket.getOutputStream();
            String messageToSend = colonna + ";X"; // Esempio di messaggio con colonna selezionata e simbolo "X"
            byte[] sendData = messageToSend.getBytes();
            outputStream.write(sendData);
            outputStream.flush();

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Socket socket = null; // Inizializza il socket del client

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TCPClient(socket);
            }
        });
    }
}
