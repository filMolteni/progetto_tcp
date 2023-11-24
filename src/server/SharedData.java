package server;

//cartella condivisa tra i thread
public class SharedData {
    public CMatrice m;
    public int currentTurn;

    public SharedData() {
        //inizializzazzione matrice e currentTurn
        this.m = new CMatrice(6, 7);
        this.currentTurn = 1;
    }

   
}

