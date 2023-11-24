package client2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

public class GUI extends JFrame {
    // Dichiarazione di variabili di istanza
    private JButton[] columnButtons;
    public JLabel[][] matrixLabels;
    private TCPClient tcpClient;

    // Costruttore con parametro TCPClient
    public GUI(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    // Costruttore di default
    public GUI() {
        setTitle("Forza 4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 7));

        columnButtons = new JButton[7];
        matrixLabels = new JLabel[6][7];

        // Inizializzazione dei pulsanti delle colonne e dei relativi listener
        for (int i = 0; i < 7; i++) {
            columnButtons[i] = new JButton("Colonna " + (i + 1));
            add(columnButtons[i]);

            // Utilizzo di una variabile finale per garantire la correttezza
            final int colonnaSelezionata = i;
            columnButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Chiamata al metodo di comunicazioneServer del TCPClient associato
                    new TCPClient(GUI.this).comunicazioneServer(colonnaSelezionata);
                }
            });
        }

        // Inizializzazione della matrice di JLabel
        disegnaMatrice();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Metodo per disegnare la matrice di JLabel
    public void disegnaMatrice() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                matrixLabels[row][col] = new JLabel(" ");
                matrixLabels[row][col].setHorizontalAlignment(SwingConstants.CENTER);
                matrixLabels[row][col].setFont(new Font("Arial", Font.BOLD, 24));

                // Imposta un bordo vuoto per separare le celle
                matrixLabels[row][col].setBorder(new LineBorder(Color.BLACK));

                add(matrixLabels[row][col]);
            }
        }
    }

    // Metodo per ridisegnare la matrice (usato dopo l'inserimento di un pezzo)
    public void ridisegnaMatrice() {
        // Rimuove tutti gli elementi attualmente presenti nel layout
        getContentPane().removeAll();

        // Aggiunge i nuovi pulsanti delle colonne
        for (int i = 0; i < 7; i++) {
            add(columnButtons[i]);
        }

        // Aggiunge i nuovi label della matrice
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                matrixLabels[row][col] = new JLabel(" ");
                matrixLabels[row][col].setHorizontalAlignment(SwingConstants.CENTER);
                matrixLabels[row][col].setFont(new Font("Arial", Font.BOLD, 24));
                add(matrixLabels[row][col]);
            }
        }

        // Rende visibili le modifiche
        revalidate();
        repaint();
    }

    // Metodo per disegnare un cerchio di un dato colore in una JLabel
    public void disegnaCerchio(JLabel label, Color colore) {
        label.setForeground(colore);
        label.setText("●");
    }

    // Metodo per impostare il TCPClient associato
    public void setTCPClient(TCPClient tcpClient2) {
        this.tcpClient = tcpClient2;
    }
}
