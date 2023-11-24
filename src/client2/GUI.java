package client2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

public class GUI extends JFrame {
    private JButton[] columnButtons;
    public JLabel[][] matrixLabels;
    private TCPClient tcpClient;

    public GUI(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
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
                    new TCPClient(GUI.this).comunicazioneServer(colonnaSelezionata);
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
                
                // Imposta un bordo vuoto per separare le celle
                matrixLabels[row][col].setBorder(new LineBorder(Color.BLACK));
                
                add(matrixLabels[row][col]);
            }
        }
    }

    public void ridisegnaMatrice() {
        // Rimuovo tutti gli elementi attualmente presenti nel layout
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
    
        // Rendo visibili le modifiche
        revalidate();
        repaint();
    }
    

    public void disegnaCerchio(JLabel label, Color colore) {
        label.setForeground(colore);
        label.setText("●"); 
    }

    public void setTCPClient(TCPClient tcpClient2) {
        this.tcpClient = tcpClient2;
    }
}

