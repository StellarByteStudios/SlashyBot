package SlashyBot.threading;

import SlashyBot.Slashy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static net.dv8tion.jda.api.OnlineStatus.OFFLINE;

public class ShutdownlistenerThread extends ThreadModel{

    public Slashy slashy;

    public ShutdownlistenerThread(Slashy slashy){
        super("Shutdown-Listener",
                "Looks at the Console for the Shutdowncommand and shutdowns the Bot");
        this.slashy = slashy;
    }

    @Override
    protected void loop() {
        // Speichert die Eingabe der Konsole
        String line = "";
        // Reader, der die Konsole einliest
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            // Reder guckt, ob es eine neue Eingabe gab
            line = reader.readLine();
                // Befehl auslesen
            if (line.equalsIgnoreCase("shutdown")){
                // Ist überhaut der Bot noch da
                if (slashy.shardManager != null) {
                    // Daten Speichern
                    slashy.saveData();
                    // Status setzen
                    slashy.shardManager.setStatus(OFFLINE);
                    // Herunterfahren
                    slashy.shardManager.shutdown();
                    System.out.println("Bot wird heruntergefahren");
                }
                // Der Reader muss zum Schluss noch geschlossen werden
                reader.close();
                // Threads in Bot werden dadurch beendet
                slashy.endAllThreads();
            } else {
                System.out.println("Shutdown wird so richtig geschrieben");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
