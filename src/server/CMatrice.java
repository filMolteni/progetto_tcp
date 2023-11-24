package server;
public class CMatrice {
    // Dichiarazione delle variabili di istanza
    private char[][] tavola;
    private int righe;
    private int colonne;

    // Costruttore che inizializza la matrice con spazi vuoti
    public CMatrice(int righe, int colonne) {
        this.righe = righe;
        this.colonne = colonne;
        tavola = new char[righe][colonne];
        for (int i = 0; i < righe; i++) {
            for (int j = 0; j < colonne; j++) {
                tavola[i][j] = ' ';
            }
        }
    }

    // Metodo per ottenere la matrice
    public char[][] getTavola() {
        return tavola;
    }

    // Metodo per inserire un pezzo in una colonna
    public boolean inserisciPezzo(int colonna, char pezzo) {
        // Verifica se il pezzo è valido
        if (pezzo != 'X' && pezzo != 'O') {
            return false;
        }

        int riga = righe - 1;
        // Trova la prima riga disponibile nella colonna
        while (riga >= 0 && tavola[riga][colonna] != ' ') {
            riga--;
        }
        // Inserisce il pezzo se la colonna non è piena
        if (riga >= 0) {
            tavola[riga][colonna] = pezzo;
            return true;
        }
        return false; // Colonna piena
    }

    // Metodo per controllare la vittoria in tutte le direzioni
    public boolean controllaVittoria(char pezzo) {
        // Verifica se il pezzo è valido
        if (pezzo != 'X' && pezzo != 'O') {
            return false;
        }

        for (int riga = 0; riga < righe; riga++) {
            for (int colonna = 0; colonna < colonne; colonna++) {
                // Controlla in tutte le direzioni dalla posizione corrente
                if (tavola[riga][colonna] == pezzo &&
                        (controllaDirezione(riga, colonna, pezzo, 0, 1) ||
                         controllaDirezione(riga, colonna, pezzo, 1, 0) ||
                         controllaDirezione(riga, colonna, pezzo, 1, 1) ||
                         controllaDirezione(riga, colonna, pezzo, 1, -1))) {
                    return true; // Vittoria trovata
                }
            }
        }
        return false; // Nessuna vittoria
    }

    // Metodo privato per controllare la vittoria in una direzione specifica
    private boolean controllaDirezione(int riga, int colonna, char pezzo, int dr, int dc) {
        int conteggio = 1;

        // Controlla verso destra
        int r = riga + dr;
        int c = colonna + dc;
        while (r >= 0 && r < righe && c >= 0 && c < colonne && tavola[r][c] == pezzo) {
            conteggio++;
            r += dr;
            c += dc;
        }

        // Controlla verso sinistra
        r = riga - dr;
        c = colonna - dc;
        while (r >= 0 && r < righe && c >= 0 && c < colonne && tavola[r][c] == pezzo) {
            conteggio++;
            r -= dr;
            c -= dc;
        }

        return conteggio >= 4; // Ritorna true se ci sono almeno 4 pezzi consecutivi
    }

    // Metodo per stampare la matrice a fini di debug
    public String stampaMatrice() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < righe; i++) {
            for (int j = 0; j < colonne; j++) {
                s.append("|").append(tavola[i][j]);
            }
            s.append("|\r\n");
        }
        return s.toString();
    }

    // Metodo per ottenere la prima riga disponibile in una colonna
    public int getRigaInserimento(int colonna) {
        for (int riga = righe - 1; riga >= 0; riga--) {
            if (tavola[riga][colonna] == ' ') {
                return riga;
            }
        }
        return -1; // La colonna è piena
    }

    // Metodo per impostare il valore di una cella nella matrice
    public void setCella(int riga, int colonna, char valore) {
        if (riga >= 0 && riga < righe && colonna >= 0 && colonna < colonne) {
            tavola[riga][colonna] = valore;
        }
    }
}
