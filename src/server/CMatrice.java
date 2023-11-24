package server;
public class CMatrice {
    private char[][] tavola;
    private int righe;
    private int colonne;

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

    public char[][] getTavola() {
        return tavola;
    }

    public boolean inserisciPezzo(int colonna, char pezzo) {
        if(pezzo != 'X' && pezzo != 'O'){
            return false;
        }
        int riga = righe - 1;
        while (riga >= 0 && tavola[riga][colonna] != ' ') {
            riga--;
        }
        if (riga >= 0) {
            tavola[riga][colonna] = pezzo;
            return true;
        }
        return false;
    }

    public boolean controllaVittoria(char pezzo) {
        if (pezzo != 'X' && pezzo != 'O') {
           return false;
        }
    
        for (int riga = 0; riga < righe; riga++) {
            for (int colonna = 0; colonna < colonne; colonna++) {
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
    
        return conteggio >= 4;
    }
    
    public String stampaMatrice() {
        String s="";
        for (int i = 0; i < righe; i++) {
            for (int j = 0; j < colonne; j++) {
                s+="|" + tavola[i][j];
            }
            s+="|\r\n";
        }
        
        
        return s;
    }

    public int getRigaInserimento(int colonna) {
        for (int riga = righe - 1; riga >= 0; riga--) {
            if (tavola[riga][colonna] == ' ') {
                return riga;
            }
        }
        return -1; // La colonna è piena
    }
    public void setCella(int riga, int colonna, char valore) {
        if (riga >= 0 && riga < righe && colonna >= 0 && colonna < colonne) {
            tavola[riga][colonna] = valore;
        }
    }
}
