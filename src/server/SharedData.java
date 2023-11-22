package server;

import java.util.HashMap;
import java.util.Map;

public class SharedData {
    public CMatrice m;
    public int currentTurn;
    public static Map<String, CommunicationThread> threadMap = new HashMap<>();

    public SharedData() {
        
        this.m = new CMatrice(6, 7);
        this.currentTurn = 1;
    }

   
}

