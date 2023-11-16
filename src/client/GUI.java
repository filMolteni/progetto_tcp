package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame {
    private JButton[] columnButtons;
    public JLabel[][] matrixLabels;
    private TCPClient tcpClient;

    public GUI(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
        // ... altre inizializzazioni ...
    }

    public GUI() {
        setTitle("Forza 4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 7));

        columnButtons = new JButton[7];
        matrixLabels = new JLabel[6][7];

        for (int i = 0; i < 7; i++) {
            columnButtons[i] = new JButton("Colonna " + (i + 1));
            add(columnButtons[i]);

            final int colonnaSelezionata = i;
            columnButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new TCPClient(GUI.this).comunicazioneServer(colonnaSelezionata, Color.RED);
                }
            });
        }

        disegnaMatrice();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void disegnaMatrice() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                matrixLabels[row][col] = new JLabel(" ");
                matrixLabels[row][col].setHorizontalAlignment(SwingConstants.CENTER);
                matrixLabels[row][col].setFont(new Font("Arial", Font.BOLD, 24));
                add(matrixLabels[row][col]);
            }
        }

    }

    public void ridisegnaMatrice() {
        // Rimuovi tutti gli elementi attualmente presenti nel layout
        getContentPane().removeAll();
    
        // Aggiungi i nuovi pulsanti delle colonne
        for (int i = 0; i < 7; i++) {
            add(columnButtons[i]);
        }
    
        // Aggiungi i nuovi label della matrice
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                matrixLabels[row][col] = new JLabel(" ");
                matrixLabels[row][col].setHorizontalAlignment(SwingConstants.CENTER);
                matrixLabels[row][col].setFont(new Font("Arial", Font.BOLD, 24));
                add(matrixLabels[row][col]);
            }
        }
    
        // Rendi visibili le modifiche
        revalidate();
        repaint();
    }
    

    public void disegnaCerchio(JLabel label, Color colore) {
        label.setForeground(colore);
        label.setText("●"); 
    }

    public void setTCPClient(TCPClient tcpClient2) {
    }
}

