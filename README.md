# Forza 4 Multiplayer Game

## Descrizione
Questo progetto implementa una versione multiplayer del gioco Forza 4 utilizzando una connessione TCP tra client e server.

## Struttura del Progetto

### Client
Il modulo client gestisce l'interfaccia grafica del gioco e la comunicazione con il server utilizzando la classe `TCPClient`.

#### File
- `GUI.java`: Implementa l'interfaccia grafica del gioco.
- `TCPClient.java`: Gestisce la connessione TCP lato client.

### Server
Il modulo server gestisce la logica di gioco, la comunicazione con i client e la gestione della partita utilizzando la classe `TCPServer`.

#### File
- `CMatrice.java`: Rappresenta la matrice di gioco e contiene la logica di controllo della vittoria.
- `CommunicationThread.java`: Thread di comunicazione con un client.
- `TCPServer.java`: Gestisce la connessione TCP lato server.

## Istruzioni per l'esecuzione

1. **Esecuzione del Server:**
   - Esegui il file `main.java` per avviare il server sulla porta specificata.

2. **Esecuzione del Client:**
   - Esegui il file `TCPClient.java` per avviare un client.
   - La GUI del gioco si aprirà, e sarà possibile interagire con essa.

3. **Regole del Gioco:**
   - I giocatori si alternano per inserire i loro pezzi nella colonna desiderata.
   - Il gioco verifica la condizione di vittoria in base alle regole del Forza 4.

## Note
Assicurati di avere Java installato sulla tua macchina per eseguire correttamente il progetto.

## Autore
Nome Cognome

## Licenza
Questo progetto è distribuito con licenza MIT. Consulta il file LICENSE per ulteriori dettagli.
