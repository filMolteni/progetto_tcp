# Forza 4

Forza 4 è una versione del popolare gioco da tavolo "Connect Four" implementata in Java con l'utilizzo di Socket per consentire la connessione tra un server e due client.

## Istruzioni per l'esecuzione

### Server

1. Apri il terminale.
2. Naviga nella directory del server (`/path/to/Forza4/server`).
3. Compila il server eseguendo il comando: `javac TCPServer.java`.
4. Avvia il server con il comando: `java TCPServer`.

### Client

1. Apri due terminali separati per ogni client.
2. Naviga nella directory del client (`/path/to/Forza4/client`).
3. Compila il client eseguendo il comando: `javac TCPClient.java`.
4. Avvia il client con il comando: `java TCPClient`.

## Come giocare

1. Avvia il server e i due client.
2. I client possono connettersi al server e interagire con il gioco.
3. I giocatori si alternano a fare mosse inserendo un pezzo (cerchio o croce) in una colonna.
4. Vince il giocatore che riesce a formare una fila di quattro pezzi consecutivi nella stessa direzione (orizzontale, verticale o diagonale).

## Struttura del progetto

- `server/`: Contiene il codice sorgente del server.
  - `TCPServer.java`: Implementazione del server che gestisce la comunicazione tra i client.
  - `CommunicationThread.java`: Thread per gestire la comunicazione con un singolo client.
  - `CMatrice.java`: Classe che rappresenta la matrice di gioco e le regole del Forza 4.
  - `SharedData.java`: Contiene dati condivisi tra i thread.
  
- `client/`: Contiene il codice sorgente del client.
  - `TCPClient.java`: Implementazione del client che comunica con il server.
  - `GUI.java`: Interfaccia grafica del gioco.

## Dipendenze

Il progetto non richiede librerie esterne. Assicurati di eseguire il codice su un ambiente Java compatibile.

## Contributi

I contributi sono benvenuti! Sentiti libero di aprire issue o pull request.

## Licenza

Questo progetto è distribuito con la licenza [MIT](LICENSE).
