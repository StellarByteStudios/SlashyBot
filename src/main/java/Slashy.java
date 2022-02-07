import commandManaging.CommandManager;
import commandManaging.listener.CommandListener;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static net.dv8tion.jda.api.OnlineStatus.OFFLINE;
import static net.dv8tion.jda.api.entities.Activity.listening;
import static secret.Tokenholder.BOT_TOKEN;

public class Slashy {

    // Selbstreferenz, damit die anderen Programmteile auch auf die Botinstanz zugreifen können
    public static Slashy INSTANCE;

    // Connection zum Bot
    public ShardManager shardManager;
    // CommandManager command -> magic -> supatolle Antwort
    private CommandManager commandManager;

    public static void main(String[] args) {
        // Wir müssen am Anfang den Bot instanziieren
        try {
            new Slashy();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    // Konstruktor der eine Instanz des Bots erzeugt
    public Slashy() throws LoginException {
        // Erzeuge die Selbstreferenz
        INSTANCE = this;

        this.commandManager = new CommandManager();
        System.out.println("CommandManager gestartet");
        this.shardManager = configureShardManager();
        System.out.println("Bot geht online");
        startup();
        System.out.println("Alle Konsolen Threads gestartet");
    }


    // Erstellt den Bot und übergibt ihm das wichtigste was er brauch
    public ShardManager configureShardManager() throws LoginException {
        // * * * Startup (Konfiguriert den Bot ganz nackig) * * * //
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault("");
        builder.setToken(BOT_TOKEN);

        // * * * Einstellungen * * * //
        // Was macht er gerate
        builder.setActivity(listening("deinem Bullshit"));
        // Onlinestatus
        builder.setStatus(OnlineStatus.ONLINE);

        // Eventlistener hinzufügen
        builder.addEventListeners(new CommandListener());

        // Build (Starten des Bots)
        return builder.build();
    }


    // Startet Alle Threads, die die Konsole auslesen
    public void startup(){
        startShutDownListener();
    }



    // Startet Thread, welcher guckt ob er den Bot runter fahren soll
    public void startShutDownListener(){

        // Startet Thread zum Auslesen der Konsole
        new Thread(() -> {
            // Speichert die Eingabe der Konsole
            String line = "";
            // Reader, der die Konsole einliest
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                // Reder guckt, ob es eine neue Eingabe gab, wenn ja, gehts in die While
                while ((line = reader.readLine()) != null){
                    // Befehl auslesen
                    if (line.equalsIgnoreCase("shutdown")){
                        // Ist überhaut der Bot noch da
                        if (shardManager != null) {
                            // Status setzen
                            shardManager.setStatus(OFFLINE);
                            // Herunterfahren
                            shardManager.shutdown();
                            System.out.println("Bot wird heruntergefahren");
                        }
                        // Der Reader muss zum schluss noch geschlossen werden
                        reader.close();
                        // Thread wird dadurch beendet
                        return;
                    } else {
                        System.out.println("Shutdown wird so richtig geschrieben");
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }).start();
    }

    // gibt den Zugriff auf den CommandManager frei
    public CommandManager getCommandManager() {
        return commandManager;
    }
}
